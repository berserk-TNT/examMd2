package glocery.views;

import glocery.models.Product;
import glocery.services.IProductService;
import glocery.services.ProductService;
import glocery.models.InputOption;
import glocery.tools.Retry;
import glocery.tools.Validation;

import java.util.List;
import java.util.Scanner;

public class ProductView {
    private final IProductService productService;
    private final Scanner scan = new Scanner(System.in);

    public ProductView() {
        productService = ProductService.getInstance();
    }

    public void add() {
        do {
            Long proId = System.currentTimeMillis() / 1000;
            String proName = inputProName(InputOption.ADD);
            Double proPrice = inputProPrice(InputOption.ADD);
            Integer proQuantity = inputProQuantity(InputOption.ADD);
            Product product = new Product(proId, proName, proPrice, proQuantity);
            productService.add(product);
            System.out.println("Added product successfully!\n");

        } while (Retry.isRetry(InputOption.ADD));
    }

    public void update() {
        boolean isRetry = false;
        do {
            try {
                showProducts(InputOption.UPDATE);
                long proId = inputProId(InputOption.UPDATE);
                System.out.println("┌ - - - - - - -   EDIT  - - - - - - - ┐");
                System.out.println("|   1. Edit product's name            |");
                System.out.println("|   2. Edit product's price           |");
                System.out.println("|   3. Edit product's quantity        |");
                System.out.println("|   4. Turn back                      |");
                System.out.println("└ - - - - - - - - - - - - - - - - - - ┘");
                System.out.println("Select option:");
                int option = Retry.retryChoose(1, 4);
                Product newProduct = new Product();
                newProduct.setProId(proId);
                switch (option) {
                    case 1:
                        String proName = inputProName(InputOption.UPDATE);
                        newProduct.setProName(proName);
                        productService.update(newProduct);
                        System.out.println("Edited product's name successfully!");
                        break;
                    case 2:
                        double proPrice = inputProPrice(InputOption.UPDATE);
                        newProduct.setProPrice(proPrice);
                        productService.update(newProduct);
                        System.out.println("Edited product's price successfully!");
                        break;
                    case 3:
                        int proQuantity = inputProQuantity(InputOption.UPDATE);
                        newProduct.setProQuantity(proQuantity);
                        productService.update(newProduct);
                        System.out.println("Updated product's quantity successfully!");
                        break;
                }
                isRetry = option != 4 && Retry.isRetry(InputOption.UPDATE);
            } catch (Exception e) {
                System.out.println("Wrong option! Please select one of above options!");
            }
        } while (isRetry);
    }

    public void showProducts(InputOption inputOption) {
        System.out.println("-----------------------------------------PRODUCT LIST-------------------------------------------");
        System.out.printf("%-15s %-30s %-25s %-10s %-20s %-20s\n", "ID", "PRODUCT", "PRICE", "QUANTITY", "CREATED TIME", "UPDATED TIME");
        for (Product product : productService.findAll()) {
            System.out.printf("%-15d %-30s %-25s %-10d %-20s %-20s\n",
                    product.getProId(),
                    product.getProName(),
                    Validation.doubleToVND(product.getProPrice()),
                    product.getProQuantity(),
                    Validation.instantToString(product.getProCreatedTime()),
                    product.getProUpdatedTime() == null ? "" : Validation.instantToString(product.getProUpdatedTime())
            );
        }
        System.out.println("--------------------------------------------------------------------------------------------------\n");
        if (inputOption == InputOption.SHOW)
            Retry.isRetry(InputOption.SHOW);
    }

    public void remove() {
        showProducts(InputOption.DELETE);
        Long id;
        while (!productService.exist(id = inputProId(InputOption.DELETE))) {
            System.out.println("This product doesn't exist!");
            System.out.println("Press 'T' for continue removing");
            System.out.println("Press 'Q' to turn back");
            System.out.println("Press 'E' to exit");
            System.out.print("==> ");
            String option = scan.nextLine();
            switch (option) {
                case "t":
                    break;
                case "q":
                    return;
                case "e":
                    Retry.exit();
                    break;
                default:
                    System.out.println("Wrong option! Please select one of above options!");
                    break;
            }
        }
        System.out.println("========== REMOVE COMFIRM ==========");
        System.out.println("|    1. Confirm to remove          |");
        System.out.println("|    2. Back                       |");
        System.out.println("====================================");
        int option = Retry.retryChoose(1, 2);
        if (option == 1) {
            productService.deleteById(id);
            System.out.println("Remove product successfully!");
            Retry.isRetry(InputOption.DELETE);
        }
    }

    private String inputProName(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Input product's name want to add: ");
                break;
            case UPDATE:
                System.out.println("Input product's name want to edit: ");
                break;
        }
        System.out.print("==> ");
        return scan.nextLine();
    }

    private Long inputProId(InputOption option) {
        Long proId;
        switch (option) {
            case ADD:
                System.out.println("Enter product's ID want to add:");
                break;
            case UPDATE:
                System.out.println("Enter product's ID want to update:");
                break;
            case DELETE:
                System.out.println("Enter product's ID want to remove:");
                break;
        }
        boolean isRetry = false;
        do {
            proId = Retry.retryParseDouble().longValue();
            boolean exist = productService.existsById(proId);
            switch (option) {
                case ADD:
                    if (exist) {
                        System.out.println("This ID existed! Try another ID!");
                    }
                    isRetry = exist;
                    break;
                case UPDATE:
                    if (!exist) {
                        System.out.println("This ID doesn't exist! Please try again!");
                    }
                    isRetry = !exist;
                    break;
            }
        } while (isRetry);
        return proId;
    }

    private int inputProQuantity(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Input product's quantity:");
                break;
            case UPDATE:
                System.out.println("Input quantity after edited:");
                break;
        }
        int proQuantity;
        do {
            proQuantity = Retry.retryParseInt();
            if (proQuantity <= 0)
                System.out.println("The quantity must be greater than 0!");
        } while (proQuantity <= 0);
        return proQuantity;
    }

    private double inputProPrice(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Enter product's price:");
                break;
            case UPDATE:
                System.out.println("Enter price after edited:");
                break;
        }
        double proPrice;
        do {
            proPrice = Retry.retryParseDouble();
            if (proPrice <= 0)
                System.out.println("The price must be greater than 0!");
        } while (proPrice <= 0);
        return proPrice;
    }

    public void showProductsSort(InputOption inputOption, List<Product> products) {
        System.out.println("---------------------------------------------------PRODUCT LIST-----------------------------------------------------");
        System.out.printf("%-15s %-30s %-25s %-10s %-20s %-20s\n", "ID", "PRODUCT NAME", "PRICE", "QUANTITY", "TIME CREATED", "TIME UPDATED");
        for (Product product : products) {
            System.out.printf("%-15d %-30s %-25s %-10d %-20s %-20s\n",
                    product.getProId(),
                    product.getProName(),
                    Validation.doubleToVND(product.getProPrice()),
                    product.getProQuantity(),
                    Validation.instantToString(product.getProCreatedTime()),
                    product.getProUpdatedTime() == null ? "" : Validation.instantToString(product.getProUpdatedTime())
            );
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------\n");
        if (inputOption == InputOption.SHOW)
            Retry.isRetry(InputOption.SHOW);
    }

    public void sortByPriceOrderByASC() {
        showProductsSort(InputOption.SHOW, productService.sortProductByPriceASC());
    }
    public void sortByPriceOrderByDES() {
        showProductsSort(InputOption.SHOW, productService.sortProductByPriceDES());
    }
}
