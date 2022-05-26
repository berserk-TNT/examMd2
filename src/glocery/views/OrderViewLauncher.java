package glocery.views;

import java.util.Scanner;

public class OrderViewLauncher {
    public static void run(){
        orderMenu();
        Scanner scan = new Scanner(System.in);
        OrderView orderView = new OrderView();
        System.out.println("Select option:");
        System.out.print("==> ");
        Integer option = Integer.parseInt(scan.nextLine());
        switch (option){
            case 1:
                orderView.addOrder();
                break;
            case 2:
                orderView.showAllOrder();
                break;
            default:
                System.out.println("Wrong option! Please select one of above options!");
                run();
        }
    }

    public static void orderMenu() {
        System.out.println("========== ORDER MENU ==========");
        System.out.println("||                            ||");
        System.out.println("||    1. Create order         ||");
        System.out.println("||    2. Show order list      ||");
        System.out.println("||                            ||");
        System.out.println("================================");
    }
}
