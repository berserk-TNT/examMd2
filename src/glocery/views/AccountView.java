package glocery.views;

import glocery.models.Account;
import glocery.models.Role;
import glocery.services.AccountService;
import glocery.services.IAccountService;
import glocery.models.InputOption;
import glocery.tools.Retry;
import glocery.tools.Validation;

import java.util.List;
import java.util.Scanner;

public class AccountView {
    private final IAccountService accountService;
    private final Scanner scan = new Scanner(System.in);

    public AccountView() {
        accountService = AccountService.getInstance();
    }

    public void addUser() {
        do {
            try {
                long id = System.currentTimeMillis() / 1000;
                String username = inputUsername();
                String password = inputPassword();
                String fullName = inputFullName(InputOption.ADD);
                String phone = inputPhone(InputOption.ADD);
                String address = inputAddress(InputOption.ADD);
                String email = inputEmail();
                Account account = new Account(id, username, password, fullName, phone, email, address, Role.USER);
                setRole(account);
                accountService.add(account);
                System.out.println("Account was added successfully!");
            } catch (Exception e) {
                System.out.println("Wrong input! Try again!");
            }
        } while (Retry.isRetry(InputOption.ADD));
    }


    public void setRole(Account user) {
        System.out.println("= = SET ROLE = =");
        System.out.println("∥   1. USER    ∥");
        System.out.println("∥   2. ADMIN   ∥");
        System.out.println("= = = =  = = = = ");
        System.out.println("Select Role: ");
        System.out.print("==> ");
        int option = scan.nextInt();
        scan.nextLine();
        switch (option) {
            case 1:
                user.setAccRole(Role.USER);
                break;
            case 2:
                user.setAccRole(Role.ADMIN);
                break;
            default:
                System.out.println("Wrong option! Please select one of above options!");
                setRole(user);
        }
    }

    public void showUsers(InputOption inputOption) {
        System.out.println("----------------------------------------------------------- ACCOUNT LIST ------------------------------------------------------------------- ");
        System.out.printf("%-15s %-22s %-15s %-22s %-20s %-10s %-20s %-20s\n", "ID", "NAME", "PHONE NUMBERS", "EMAIL", "ADDRESS", "ROLE", "TIME CREATED", "TIME UPDATED");
        List<Account> accountList = accountService.findAll();
        for (Account account : accountList) {
            System.out.printf("%-15d %-22s %-15s %-22s %-20s %-10s %-20s %-20s\n",
                    account.getAccId(),
                    account.getAccName(),
                    account.getAccPhoneNumber(),
                    account.getAccEmail(),
                    account.getAccAddress(),
                    account.getAccRole(),
                    Validation.instantToString(account.getAccCreatedTime()),
                    account.getAccUpdatedTime() == null ? "" : Validation.instantToString(account.getAccUpdatedTime())
            );
        }
        System.out.println("-----------------------------------------------------------------------------------------------------  ");
        if (inputOption == InputOption.SHOW) Retry.isRetry(InputOption.SHOW);
    }

    public void updateUser() {
        boolean isRetry = false;
        do {
            try {
                showUsers(InputOption.UPDATE);
                long id = inputId(InputOption.UPDATE);
                System.out.println("┌ - - - - - - - EDIT - - - - - -  ┐");
                System.out.println("︲  1. Change your name           ︲");
                System.out.println("︲  2. Change your phone numbers  ︲");
                System.out.println("︲  3. Change your address        ︲");
                System.out.println("︲  4. Back                       ︲");
                System.out.println("└ - - - - - - - - - - - - - - - - ┘");

                int option = Retry.retryChoose(1, 4);
                Account newAccount = new Account();
                newAccount.setAccId(id);
                switch (option) {
                    case 1:
                        String accName = inputFullName(InputOption.UPDATE);
                        newAccount.setAccName(accName);
                        accountService.update(newAccount);
                        System.out.println("Your name was changed successfully!");
                        break;
                    case 2:
                        String phone = inputPhone(InputOption.UPDATE);
                        newAccount.setAccPhoneNumber(phone);
                        accountService.update(newAccount);
                        System.out.println("Your phone numbers was changed successfully!");
                        break;
                    case 3:
                        String address = inputAddress(InputOption.UPDATE);
                        newAccount.setAccAddress(address);
                        accountService.update(newAccount);
                        System.out.println("Your address was changed successfully!");
                        break;
                }
                isRetry = option != 4 && Retry.isRetry(InputOption.UPDATE);
            } catch (Exception e) {
                System.out.println("Wrong input! Please try again!");
            }
        } while (isRetry);
    }


    private Long inputId(InputOption option) {
        Long id;
        switch (option) {
            case ADD:
                System.out.println("Enter ID:");
                break;
            case UPDATE:
                System.out.println("Enter account's ID you want to edit:");
                break;
        }
        boolean isRetry = false;
        do {
            id = Retry.retryParseDouble().longValue();
            boolean exist = accountService.existById(id);
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
        return id;
    }

    public String inputUsername() {
        System.out.println("Input Username:");
        String username;

        do {
            if (!Validation.isUsernameValid(username = Retry.retryString("Username"))) {
                System.out.println(username + "is not valid! Please try again!");
                System.out.print("==> ");
                continue;
            }
            if (accountService.existsByUsername(username)) {
                System.out.println("This username exist! Try another username!");
                System.out.print("==> ");
                continue;
            }
            break;
        } while (true);

        return username;
    }

    private String inputFullName(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Enter your name:");
                break;
            case UPDATE:
                System.out.println("Enter your name after edited:");
                break;
        }

        System.out.print("==> ");
        String name;
        while (!Validation.isNameValid(name = scan.nextLine())) {
            System.out.println(name + "is not valid! Please try again!");
            System.out.println("Enter your name:");
            System.out.print("==> ");
        }
        return name;
    }

    public String inputPhone(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Enter your phone numbers:");
                break;
            case UPDATE:
                System.out.println("Enter your phone numbers after edited:");
                break;
        }
        System.out.print("==> ");
        String phoneNumber;
        do {
            phoneNumber = scan.nextLine();
            if (!Validation.isPhoneValid(phoneNumber)) {
                System.out.println(phoneNumber + " is not valid! Please try again!");
                System.out.println("Enter your phone numbers:");
                System.out.print("==> ");
                continue;
            }
            if (accountService.existsByPhoneNumber(phoneNumber)) {
                System.out.println("This phone numbers existed! Please try another phone numbers!");
                System.out.print("==> ");
                continue;
            }
            break;
        } while (true);

        return phoneNumber;
    }

    private String inputEmail() {
        System.out.println("Enter your email:");
        System.out.print("==> ");
        String email;
        do {
            if (!Validation.isEmailValid(email = scan.nextLine())) {
                System.out.println(email + " is not valid! Please try again!");
                System.out.println("Enter your email:");
                System.out.print("==> ");
                continue;
            }
            if (accountService.existsByEmail(email)) {
                System.out.println(email + " is not valid! Please try again!");
                System.out.println("Enter your email:");
                System.out.print("==> ");
                continue;
            }
            break;
        } while (true);
        return email;
    }

    private String inputPassword() {
        System.out.println("Enter password:");
        System.out.print("==> ");
        String password;
        while (!Validation.isPasswordValid(password = scan.nextLine())) {
            System.out.println("Password is not valid! Please try again!");
            System.out.print("==> ");
        }
        return password;
    }

    private String inputAddress(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Input your address:");
                break;
            case UPDATE:
                System.out.println("Input your address after edited:");
                break;
        }
        System.out.print("==> ");
        return scan.nextLine();
    }
}
