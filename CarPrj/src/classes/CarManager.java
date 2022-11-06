package classes;

import classes.com.*;

import java.util.ArrayList;
import java.nio.file.Paths;
import java.util.Scanner;

public class CarManager {

    public static String projectPath = Paths.get("").toAbsolutePath().toString();
    public static String srcPath = projectPath + "\\src";

    public static ArrayList<String> options = new ArrayList<>();

    public static Scanner scanner = new Scanner(System.in);

    public static void addOptions() {
        options.add("Exit");
        options.add("List all brands");
        options.add("Add a new brand");
        options.add("Search a brand by ID");
        options.add("Update a brand");
        options.add("Save brands to the file");
        options.add("List all cars in ascending order of brand names");
        options.add("List cars based on a part of an input brand name");
        options.add("Add a car");
        options.add("Remove a car by ID");
        options.add("Update a car by ID");
        options.add("Save cars to file");
    }

    public static void main(String[] args) {
        addOptions();
        BrandList brandList = new BrandList();
        CarList carList = new CarList(brandList);

        String brandsPath = srcPath + "\\Brands.txt";
        String carsPath = srcPath + "\\Cars.txt";
        
        if (brandList.loadFromFile(brandsPath))
            System.out.println("Loaded brands database successfully");
        else {
            System.out.println("Cannot load brands database! Exiting");
            System.exit(0);
        }
        
        if (carList.loadFromFile(carsPath))
            System.out.println("Loaded cars database successfully");
        else {
            System.out.println("Cannot load cars database! Exiting");
            System.exit(0);
        }
        
        int choice;
        Menu menu = new Menu();
        do {
            System.out.println("==================================");
            choice = menu.int_getChoice(options);
            switch (choice) {
                case 0:
                    return;
                case 1:
                    brandList.listBrands();
                    break;
                case 2:
                    brandList.addBrand();
                    break;
                case 3:
                    int pos = brandList.searchID(menu.getStringWoSpace("Enter brand ID to search: "));
                    System.out.println((pos == -1)
                                    ? "Not found!"
                                    : brandList.get(pos));
                    break;
                case 4:
                    brandList.updateBrand();
                    break;
                case 5:
                    if (brandList.saveToFile(brandsPath)) {
                        System.out.println("Saved successfully!");
                    } else {
                        System.out.println("Save failed!");
                    }
                    break;
                case 6:
                    carList.listCars();
                    break;
                case 7:
                    carList.printBasedBrandName();
                    break;
                case 8:
                    carList.addCar();
                    break;
                case 9:
                    if (carList.removeCar()) {
                        System.out.println("Removed successfully!");
                    } else {
                        System.out.println("Remove failed!");
                    }
                    break;
                case 10:
                    if (carList.updateCar()) {
                        System.out.println("Updated successfully!");
                    } else {
                        System.out.println("Update failed!");
                    }
                    break;
                case 11:
                    if (carList.saveToFile(carsPath)) {
                        System.out.println("Saved successfully!");
                    } else {
                        System.out.println("Save failed!");
                    }
                    break;
                default:
                    System.out.println("Enter option again");
                    break;
            }
        } while (choice >= 0 && choice < options.size());
    }
}
