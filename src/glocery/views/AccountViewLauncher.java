package glocery.views;

import glocery.models.InputOption;
import java.util.Scanner;

public class AccountViewLauncher {
    public static void launch() {
        Scanner scan = new Scanner(System.in);
        AccountView userView = new AccountView();
        int option = -1;
        do {
            menuUser();
            try {
                do {
                    System.out.println("Select option:");
                    System.out.print("==> ");
                    option = Integer.parseInt(scan.nextLine());
                    if (option > 4 || option < 1)
                        System.out.println("Wrong option! Please select one of above options!");
                } while (option > 4 || option < 1);

                switch (option) {
                    case 1:
                        userView.addUser();
                        break;
                    case 2:
                        userView.updateUser();
                        break;
                    case 3:
                        userView.showUsers(InputOption.SHOW);
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("Wrong option! Please select one of above options!");
                        break;
                }
            } catch (Exception ex) {
                System.out.println("Wrong option! Please select one of above options!");
            }
        } while (option != 4);

    }

    public static void menuUser() {
        System.out.println("=========== ACCOUNTS MANAGER ============");
        System.out.println("||                                     ||");
        System.out.println("||    1. Add account                   ||");
        System.out.println("||    2. Edit account profile          ||");
        System.out.println("||    3. Show accounts list            ||");
        System.out.println("||    4. Back                          ||");
        System.out.println("||                                     ||");
        System.out.println("=========================================");
    }
}
