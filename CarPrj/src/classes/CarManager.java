package classes;

import classes.com.*;

import classes.com.BrandList;
import classes.com.CarList;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import java.io.IOException;

public class CarManager {

    public static Path projectPath = Paths.get("");
    public static String workingPath = projectPath.toAbsolutePath().toString() + "\\src";

    public static ArrayList<String> options = new ArrayList<>();

    public static Scanner scanner = new Scanner(System.in);

    public static void addOptions() {
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

    public static void main(String[] args) throws IOException {
        addOptions();
        BrandList brandList = new BrandList();
        CarList carList = new CarList(brandList);

        String brandsPath = workingPath + "\\Brands.txt";
        String carsPath = workingPath + "\\Cars.txt";

        Menu menu = new Menu();
        do {
            int choice = menu.int_getChoice(options);
            switch (choice) {
                case 1:
                    brandList.listBrands();
                    break;
                case 2:
                    brandList.addBrand();
                    break;
                case 3:
                    System.out.println("Enter brand ID to search: ");
                    String bID = scanner.nextLine();
                    brandList.searchID(bID);
                    break;
                case 4:
                    brandList.updateBrand();
                    break;
                case 5:
                    brandList.saveToFile(brandsPath);
                    break;
                case 6:
                    carList.listCars();
                    break;
                case 7:
                    
                    break;
                case 8:
                    carList.add(e);
                    break;
                case 9:
                    
            }
        }
    
    
    }
}
