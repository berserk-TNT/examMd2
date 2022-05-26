package glocery.views;

import glocery.services.AccountService;
import glocery.services.IAccountService;
import glocery.tools.Retry;

import java.util.Scanner;

public class AdminView {
    private final IAccountService userService;
    private final Scanner scan = new Scanner(System.in);

    public AdminView() {
        userService = AccountService.getInstance();
    }

    public void adminLogin() {
        boolean isRetry;
        System.out.println("==================================================");
        System.out.println("||||         WELCOME TO MY GLOCERY STORE      ||||");
        System.out.println("==================================================");
        do {
            System.out.println("Username");
            String username = Retry.retryString("Username");
            System.out.println("Password");
            String password = Retry.retryString("Password");
            if (userService.adminLogin(username, password) == null) {
                System.out.println("Account is not valid!");
                isRetry = isRetry();
            } else {
                System.out.println("Sign in successfully!");
                isRetry = false;
            }
        } while (isRetry);
    }

    private boolean isRetry() {
        do {
            try {
                System.out.println("========== TRY AGAIN? ==========");
                System.out.println("||                            ||");
                System.out.println("||   1. Try to login again    ||");
                System.out.println("||   2. Exit                  ||");
                System.out.println("||                            ||");
                System.out.println("================================");
                System.out.print("==> ");
                int option = Integer.parseInt(scan.nextLine());
                switch (option) {
                    case 1:
                        return true;
                    case 2:
                        Retry.exit();
                        break;
                    default:
                        System.out.println("This option doesn't exist! Please choose one of above choices!");
                        break;
                }

            } catch (Exception ex) {
                System.out.println("This option doesn't exist! Please choose one of above choices!");
                ex.printStackTrace();
            }
        } while (true);
    }
}
