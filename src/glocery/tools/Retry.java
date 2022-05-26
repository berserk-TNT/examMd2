package glocery.tools;

import glocery.models.InputOption;

import java.util.Scanner;

public class Retry {
    static Scanner scan = new Scanner(System.in);

    public static Integer retryChoose(Integer min, Integer max) {
        int option;
        do {
            System.out.print("==> ");
            try {
                option = Integer.parseInt(scan.nextLine());
                if (option > max || option < min) {
                    System.out.println("This choice doesn't exist! Please choose one of above choices!");
                    continue;
                }
                break;
            } catch (Exception ex) {
                System.out.println("Invalid input! Please try again!");
            }
        } while (true);
        return option;
    }

    public static Integer retryParseInt() {
        int result;
        do {
            System.out.print("==> ");
            try {
                result = Integer.parseInt(scan.nextLine());
                return result;
            } catch (Exception ex) {
                System.out.println("Invalid input! Please try again!");
            }
        } while (true);
    }

    public static String retryString(String name) {
        String result;
        System.out.print("==> ");
        while ((result = scan.nextLine()).isEmpty()) {
            System.out.printf("%s can't be empty!\n", name);
            System.out.print("==> ");
        }
        return result;
    }

    public static Double retryParseDouble() {
        double result;
        do {
            System.out.print("==> ");
            try {
                result = Double.parseDouble(scan.nextLine());
                return result;
            } catch (Exception ex) {
                System.out.println("Invalid input! Please try again!");
            }
        } while (true);
    }

    public static boolean isRetry(InputOption inputOption) {
        do {
            switch (inputOption) {
                case ADD:
                    System.out.println("Press 'T' to continue adding");
                    System.out.println("Press 'Q' to turn back");
                    System.out.println("Press 'E' to exit");
                    break;
                case UPDATE:
                    System.out.println("Press 'T' to continue editing");
                    System.out.println("Press 'Q' to turn back");
                    System.out.println("Press 'E' to exit");
                    break;
                case DELETE:
                    System.out.println("Press 'Q' to turn back");
                    System.out.println("Press 'E' to exit");
                    break;
                case SHOW:
                    System.out.println("Press 'Q' to turn back");
                    System.out.println("Press 'E' to exit");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + inputOption);
            }
            System.out.print("==> ");
            String option = scan.nextLine();
            switch (option) {
                case "t":
                    return true;
                case "q":
                    return false;
                case "e":
                    exit();
                    break;
                default:
                    System.out.println("This choice doesn't exist! Please choose one of above choices!");
                    break;
            }
        } while (true);
    }

    public static void exit() {
        System.out.println("Goodbye! See you again!");
        System.exit(0);
    }

//    private boolean isRetry() {
//        do {
//            try {
//                System.out.println("-------------------");
//                System.out.println("|                 |");
//                System.out.println("|  1. Retry       |");
//                System.out.println("|  2. Turn back   |");
//                System.out.println("|  3. Exit        |");
//                System.out.println("|                 |");
//                System.out.println("-------------------");
//                System.out.print("==> ");
//                int option = Integer.parseInt(scan.nextLine());
//                switch (option) {
//                    case 1:
//                        return true;
//                    case 2:
//                        return false;
//                    case 3:
//                        System.exit(0);
//                        break;
//                    default:
//                        System.out.println("This choice doesn't exist! Please choose one of above choices!");
//                        break;
//                }
//            } catch (Exception ex) {
//                System.out.println("This choice doesn't exist! Please choose one of above choices!");
//                ex.printStackTrace();
//            }
//        } while (true);
//    }
}
