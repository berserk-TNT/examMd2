package glocery.views;

import glocery.models.InputOption;
import glocery.tools.Retry;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductViewLauncher {
    public static void run() {
        int option;
        do {
            Scanner scanner = new Scanner(System.in);
            ProductView productView = new ProductView();
            menuProduct();
            try {
                System.out.println("Select option:");
                System.out.print("==> ");
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        productView.add();
                        break;
                    case 2:
                        productView.update();
                        break;
                    case 3:
                        productView.remove();
                        break;
                    case 4:
                        productView.showProducts(InputOption.SHOW);
                        break;
                    case 5:
                        productView.sortByPriceOrderByASC();
                        break;
                    case 6:
                        productView.sortByPriceOrderByDES();
                    case 7:
                        Retry.exit();
                        break;
                    default:
                        System.err.println("Wrong option! Please select one of above options!");
                        run();
                }
            } catch (InputMismatchException io) {
                System.out.println("Wrong option! Please select one of above options!");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public static void menuProduct() {
        System.out.println();
        System.out.println("================= PRODUCT MENU ===================");
        System.out.println("||                                              ||");
        System.out.println("||    1. Add product                            ||");
        System.out.println("||    2. Edit product's detail                  ||");
        System.out.println("||    3. Remove product                         ||");
        System.out.println("||    4. Show products list                     ||");
        System.out.println("||    5. Show products by ascending price       ||");
        System.out.println("||    6. Show products by descending price      ||");
        System.out.println("||    7. Exit                                   ||");
        System.out.println("||                                              ||");
        System.out.println("==================================================");
    }
}
