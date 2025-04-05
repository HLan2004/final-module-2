package service;

import exception.NotFoundProductException;
import models.GenuineMobile;
import models.ImportedMobile;
import models.Mobile;

import java.io.*;
import java.util.*;

public class MobileManager {
    private List<Mobile> mobileList = new ArrayList<>();
    private static final String FILE_PATH = "data/mobiles.csv";

    public void addMobile(Mobile mobile) {
        if (isValidMobile(mobile)) {
            mobileList.add(mobile);
            System.out.println("Mobile added successfully!");
            saveData();
        } else {
            System.out.println("Failed to add mobile. Invalid data.");
        }
    }

    private boolean isValidMobile(Mobile mobile) {
        if (mobile.getName().isEmpty() || mobile.getManufacturer().isEmpty()) {
            return false;
        }
        if (mobile.getPrice() <= 0 || mobile.getQuantity() <= 0) {
            return false;
        }

        if (mobile instanceof GenuineMobile) {
            GenuineMobile genuineMobile = (GenuineMobile) mobile;
            if (genuineMobile.getWarrantyPeriod() <= 0 || genuineMobile.getWarrantyPeriod() > 730) {
                return false;
            }
            String scope = genuineMobile.getWarrantyScope();
            if (!(scope.equalsIgnoreCase("Toan Quoc") || scope.equalsIgnoreCase("Quoc Te"))) {
                return false;
            }
        } else if (mobile instanceof ImportedMobile) {
            ImportedMobile importedMobile = (ImportedMobile) mobile;
            if (importedMobile.getImportCountry().equalsIgnoreCase("Viet Nam")) {
                return false;
            }
            String status = importedMobile.getStatus();
            if (!(status.equalsIgnoreCase("Da sua chua") || status.equalsIgnoreCase("Chua sua chua"))) {
                return false;
            }
        }
        return true;
    }

    public void displayList() {
        if (mobileList.isEmpty()) {
            System.out.println("Mobile list is empty.");
            return;
        }
        for (Mobile mobile : mobileList) {
            System.out.println(mobile.toString());
        }
    }

    public Mobile searchMobile(String keyword) {
        keyword = keyword.toLowerCase();
        for (Mobile mobile : mobileList) {
            if (String.valueOf(mobile.getId()).equals(keyword) ||
                    mobile.getName().toLowerCase().contains(keyword)) {
                return mobile;
            }
        }
        return null;
    }

    public void deleteMobile(int id) {
        for (Mobile mobile : mobileList) {
            if (mobile.getId() == id) {
                mobileList.remove(mobile);
                saveData();
                System.out.println("Mobile deleted successfully!");
                return;
            }
        }
        try {
            throw new NotFoundProductException("Mobile ID not found.");
        } catch (NotFoundProductException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getNextId() {
        int maxId = 0;
        for (Mobile mobile : mobileList) {
            if (mobile.getId() > maxId) {
                maxId = mobile.getId();
            }
        }
        return maxId + 1;
    }

    public void saveData() {
        File file = new File(FILE_PATH);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Mobile mobile : mobileList) {
                bw.write(mobile.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }


    public void loadData() {
        mobileList.clear();
        File file = new File(FILE_PATH);

        if (!file.exists() || file.length() == 0) {
            System.out.println("File does not exist or is empty.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;


            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    try {
                        int warranty = Integer.parseInt(parts[5].trim());
                        String scope = parts[6].trim();
                        Mobile mobile = new GenuineMobile(
                                Integer.parseInt(parts[0].trim()),
                                parts[1].trim(),
                                Double.parseDouble(parts[2].trim()),
                                Integer.parseInt(parts[3].trim()),
                                parts[4].trim(),
                                warranty,
                                scope
                        );
                        mobileList.add(mobile);
                    } catch (NumberFormatException e) {
                        Mobile mobile = new ImportedMobile(
                                Integer.parseInt(parts[0].trim()),
                                parts[1].trim(),
                                Double.parseDouble(parts[2].trim()),
                                Integer.parseInt(parts[3].trim()),
                                parts[4].trim(),
                                parts[5].trim(),
                                parts[6].trim()
                        );
                        mobileList.add(mobile);
                    }
                }
            }
            System.out.println("Data loaded successfully from CSV file!");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void sortMobilesByName() {
        Collections.sort(mobileList, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        System.out.println("Mobile list has been sorted by name!");
    }
}