import models.GenuineMobile;
import models.ImportedMobile;
import models.Mobile;
import service.MobileManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MobileManager manager = new MobileManager();
        manager.loadData();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("------ MENU ------");
            System.out.println("1. Add New");
            System.out.println("2. Delete");
            System.out.println("3. View List");
            System.out.println("4. Search");
            System.out.println("5. Sort by Name");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1:
                    addNewMobile(manager, sc);
                    break;
                case 2:
                    deleteMobile(manager, sc);
                    break;
                case 3:
                    manager.displayList();
                    break;
                case 4:
                    searchMobile(manager, sc);
                    break;
                case 5:
                    manager.sortMobilesByName();
                    manager.displayList();
                    break;
                case 6:
                    System.out.println("Exiting.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    private static void addNewMobile(MobileManager manager, Scanner sc) {
        int id = manager.getNextId();

        System.out.print("Enter mobile name: ");
        String name = sc.nextLine();

        System.out.print("Enter price: ");
        double price = Double.parseDouble(sc.nextLine());

        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(sc.nextLine());

        System.out.print("Enter manufacturer: ");
        String manufacturer = sc.nextLine();

        System.out.println("Select mobile type: 1. Genuine, 2. Imported");
        String type = sc.nextLine();

        Mobile mobile;
        if (type.equals("1")) {
            System.out.print("Enter warranty period (days, <=730): ");
            int warrantyPeriod = Integer.parseInt(sc.nextLine());

            System.out.print("Enter warranty scope (Toan Quoc/Quoc Te): ");
            String warrantyScope = sc.nextLine();

            mobile = new GenuineMobile(id, name, price, quantity, manufacturer, warrantyPeriod, warrantyScope);
        } else {
            System.out.print("Enter import country (cannot be Viet Nam): ");
            String importCountry = sc.nextLine();

            System.out.print("Enter status (Da sua chua/Chua sua chua): ");
            String status = sc.nextLine();

            mobile = new ImportedMobile(id, name, price, quantity, manufacturer, importCountry, status);
        }

        manager.addMobile(mobile);
    }

    private static void deleteMobile(MobileManager manager, Scanner sc) {
        System.out.print("Enter mobile ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Are you sure you want to delete? (Yes/No): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("Yes")) {
            manager.deleteMobile(id);
        } else {
            System.out.println("Delete cancelled. Returning to main menu.");
        }
    }

    private static void searchMobile(MobileManager manager, Scanner sc) {
        System.out.print("Enter keyword to search (ID or Mobile Name): ");
        String keyword = sc.nextLine();

        Mobile result = manager.searchMobile(keyword);
        if (result != null) {
            System.out.println(result.toString());
        } else {
            System.out.println("No mobile found.");
        }
    }
}