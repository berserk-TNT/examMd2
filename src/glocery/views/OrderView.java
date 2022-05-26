package glocery.views;

import glocery.models.Order;
import glocery.models.OrderItem;
import glocery.models.Product;
import glocery.services.IOrderItemService;
import glocery.services.IOrderService;
import glocery.services.IProductService;
import glocery.services.OrderItemService;
import glocery.services.OrderService;
import glocery.services.ProductService;
import glocery.models.InputOption;
import glocery.tools.Retry;
import glocery.tools.Validation;

import java.util.List;
import java.util.Scanner;

public class OrderView {
    private final IProductService productService;
    private final IOrderService orderService;
    private final IOrderItemService oderItemService;
    private final Scanner scan = new Scanner(System.in);

    public OrderView() {
        productService = ProductService.getInstance();
        orderService = OrderService.getInstance();
        oderItemService = OrderItemService.getInstance();
    }

    public OrderItem addOrderItems(long orderId) {
        oderItemService.findAll();
        ProductView productView = new ProductView();
        productView.showProducts(InputOption.ADD);
        orderId = System.currentTimeMillis() / 1000;
        System.out.println("Enter product's ID:");
        System.out.print("==> ");
        long proId = scan.nextLong();
        scan.nextLine();
        while (!productService.existsById(proId)) {
            System.out.println("This product's ID doesn't exist!");
            System.out.println("Enter product's ID: ");
            System.out.print("==> ");
            proId = scan.nextLong();
        }
        Product product = productService.findById(proId);
        double proPrice = product.getProPrice();
        int oldQuantity = product.getProQuantity();
        System.out.println("Input quantity:");
        System.out.print("==> ");
        int orderQuantity = scan.nextInt();
        scan.nextLine();
        while (!checkQuantityProduct(product, orderQuantity)) {
            System.out.println("Exceed quantity! Input quantity mustn't get over of current quantity!");
            System.out.println("Input quantity:");
            System.out.print("==> ");
            orderQuantity = scan.nextInt();
        }
        String proName = product.getProName();
        int currentQuantity = oldQuantity - orderQuantity;
        double orderTotal = orderQuantity * proPrice;
        product.setProQuantity(currentQuantity);
        return new OrderItem(orderId, proName, proPrice, orderQuantity);
    }

    public boolean checkQuantityProduct(Product product, Integer orderQuantity) {
        if (orderQuantity <= product.getProQuantity()) {
            return true;
        }
        else {
            return false;
        }
    }

    public void addOrder() {
        try {
            orderService.findAll();
            long orderId = System.currentTimeMillis() / 1000;
            System.out.println("Input your name:");
            System.out.print("==> ");
            String accName = scan.nextLine();
            while (!Validation.isNameValid(accName)) {
                System.out.println(accName + " is not valid! Try again!");
                System.out.println("Input your name:");
                System.out.print("==> ");
                accName = scan.nextLine();
            }
            System.out.println("Input phone numbers:");
            System.out.print("==> ");
            String accPhoneNumber = scan.nextLine();
            while (!Validation.isPhoneValid(accPhoneNumber)) {
                System.out.println(accPhoneNumber + " is not valid! Try again! ");
                System.out.println("Enter your phone numbers:");
                System.out.print("==> ");
                accPhoneNumber = scan.nextLine();
            }
            System.out.println("Enter your address:");
            System.out.print("==> ");
            String accAddress = scan.nextLine();
            OrderItem orderItem = addOrderItems(orderId);
            Order order = new Order(orderId, accName, accPhoneNumber, accAddress);
            oderItemService.add(orderItem);
            orderService.add(order);
            System.out.println("Order was created successfully!");
            do {
                System.out.println("====================================");
                System.out.println("||                                ||");
                System.out.println("||     1. Create more orders      ||");
                System.out.println("||     2. Print your bill         ||");
                System.out.println("||     3. Turn back               ||");
                System.out.println("||     4. Exit                    ||");
                System.out.println("||                                ||");
                System.out.println("====================================");
                System.out.print("==> ");
                int option = Integer.parseInt(scan.nextLine());
                switch (option) {
                    case 1:
                        addOrder();
                        break;
                    case 2:
                        showPaymentInfo(orderItem, order);
                        break;
                    case 3:
                        OrderViewLauncher.run();
                        break;
                    case 4:
                        Retry.exit();
                        break;
                    default:
                        System.out.println("Wrong option! Please select one of above options!");
                }
            } while (true);
        } catch (Exception e) {
            System.out.println("Wrong option! Please select one of above options!");
        }
    }

    public void showPaymentInfo(OrderItem orderItem, Order order) {
        try {
            System.out.println("---------------------------------------------------------------------BILL--------------------------------------------------------------------------------------");
            System.out.printf("|%-15s %-20s %-15s %-15s %-25s %-15s %-15s\n|", "ORDER ID", "CUSTOMER NAME", "PHONE NUMBERS", "ADDRESS", "PRODUCT NAME", "PRICE", "QUANTITY\n");
            System.out.printf("%-15d %-20s %-15s %-15s %-25s %-15f %-15d\n|", order.getOrderId(), order.getAccName(), order.getAccPhoneNumber(),
                    order.getAccAddress(), orderItem.getProName(), orderItem.getProPrice(), orderItem.getOrderQuantity());
            System.out.println(" ------------------------------------------------------------------------------------------------------------------- TOTAL: " + orderItem.getOrderQuantity()*orderItem.getProPrice());
            boolean is = true;
            do {
                System.out.println("Press 'Q' to get back");
                System.out.println("Press 'E' to exit");
                System.out.print("==> ");
                String option = scan.nextLine();
                switch (option) {
                    case "q":
                        OrderViewLauncher.run();
                        break;
                    case "e":
                        Retry.exit();
                        break;
                    default:
                        System.out.println("Wrong option! Please select one of above options!");
                        is = false;
                }
            } while (!is);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void showAllOrder() {
        List<Order> orders = orderService.findAll();
        List<OrderItem> orderItems = oderItemService.findAll();
        OrderItem newOrderItem = new OrderItem();
        try {
            System.out.println("----------------------------------------------------------ORDER LIST--------------------------------------------------------------------");
            System.out.printf("|%-15s %-20s %-15s %-20s %-15s %-10s %-15s %-20s\n|", "ORDER ID", "CUSTOMER NAME", "PHONE NUMBERS", "ADDRESS", "PRODUCT", "PRICE", "QUANTITY", "TOTAL" + "|");
            for (Order order : orders) {
                for (OrderItem orderItem : orderItems) {
                    if (order.getOrderId().equals(orderItem.getOrderId())) {
                        newOrderItem = orderItem;
                        break;
                    }
                }
                System.out.printf("%-15d %-20s %-15s %-20s %-15s %-10f %-15d %-20f\n|",
                        order.getOrderId(),
                        order.getAccName(),
                        order.getAccPhoneNumber(),
                        order.getAccAddress(),
                        newOrderItem.getProName(),
                        newOrderItem.getProPrice(),
                        newOrderItem.getOrderQuantity(),
                        newOrderItem.getProPrice() * newOrderItem.getOrderQuantity(),
                        "|");
            }
            System.out.println("                                                                                                                                      |");
            boolean flag = true;
            do {
                System.out.println("Press 'Q' to turn back");
                System.out.println("Press 'E' to exit");
                System.out.print("==> ");
                String option = scan.nextLine();
                switch (option) {
                    case "q":
                        OrderViewLauncher.run();
                        break;
                    case "e":
                        Retry.exit();
                        break;
                    default:
                        System.out.println("Wrong option! Please select one of above options!");
                        flag = false;
                }
            } while (!flag);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
