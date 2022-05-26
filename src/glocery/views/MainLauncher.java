package glocery.views;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainLauncher {
    public static void launch() {
        AdminView adminView = new AdminView();
        adminView.adminLogin();
        menuOption();
    }

    public static void menuOption() {
        do {
            mainMenu();
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println("Choose option");
                System.out.print("==> ");
                int option = scan.nextInt();
                switch (option) {
                    case 1:
                        AccountViewLauncher.launch();
                        break;
                    case 2:
                        ProductViewLauncher.run();
                        break;
                    case 3:
                        OrderViewLauncher.run();
                        break;
                    default:
                        System.out.println("Wrong option! Please select one of above options!");
                        menuOption();
                }
            } catch (InputMismatchException io) {
                System.out.println("Wrong option! Please select one of above options!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public static void mainMenu() {
        System.out.println("\t ==================== MAIN MENU ====================");
        System.out.println("\t ||                                               ||");
        System.out.println("\t ||             1. ACCOUNT MANAGER                ||");
        System.out.println("\t ||             2. PRODUCT MANAGER                ||");
        System.out.println("\t ||             3. ORDER MANAGER                  ||");
        System.out.println("\t ||                                               ||");
        System.out.println("\t ===================================================");
    }
}
