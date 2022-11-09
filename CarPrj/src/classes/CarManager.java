package classes;

import classes.com.*;

import java.util.ArrayList;
import java.io.File;
import java.nio.file.Paths;

import java.io.IOException;

public class CarManager {

    public static String projectPath = Paths.get("").toAbsolutePath().toString();
    public static String srcPath = projectPath + "\\src";

    public static BrandList brandList = new BrandList();
    public static CarList carList = new CarList(brandList);

    public static String brandsPath = srcPath + "\\Brands.txt";
    public static String carsPath = srcPath + "\\Cars.txt";

    public static ArrayList<String> options = new ArrayList<>();

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

    public static void checkValidFiles() throws IOException {
        if (brandList.loadFromFile(brandsPath)) {
            System.out.println("Loaded brands successfully");
        } else {
            File brandsFile = new File(brandsPath);
            brandsFile.createNewFile();
            System.out.println("Brands file not found. Automatically created Brands.txt at " + srcPath);
        }

        if (carList.loadFromFile(carsPath)) {
            System.out.println("Loaded cars successfully");
        } else {
            File carsFile = new File(carsPath);
            carsFile.createNewFile();
            System.out.println("Cars file not found. Automatically created Cars.txt at " + srcPath);
        }
    }

    public static void main(String[] args) throws IOException {
        addOptions();

        checkValidFiles();
        
        boolean choosing = true;
        Menu menu = new Menu();
        do {
            System.out.println("==================================");
            int choice = menu.int_getChoice(options);
            switch (choice) {
                case 0: // exit
                    choosing = false;
                    break;
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
                    System.out.println((brandList.saveToFile(brandsPath))
                            ? "Saved successfully"
                            : "Save failed!");
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
                    System.out.println(carList.removeCar()
                            ? "Removed successfully!"
                            : "Remove failed!");
                    break;
                case 10:
                    System.out.println(carList.updateCar()
                            ? "Updated successfully!"
                            : "Not found!");
                    break;
                case 11:
                    System.out.println(carList.saveToFile(carsPath)
                            ? "Saved successfully!"
                            : "Save failed!");
                    break;
                default:
                    System.out.println("Choice must be from 0 to " + (options.size() - 1) + ". Please retype");
                    break;
            }
        } while (choosing == true);
    }
}
