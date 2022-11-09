package classes.com;

import classes.App;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.io.IOException;

public class BrandList extends ArrayList<Brand> {

    public BrandList() {
    }

    public Menu menu = new Menu();

    public boolean loadFromFile(String fileBrands) {
        String line = null;
        try {
            FileReader fr = new FileReader(fileBrands);
            try (BufferedReader br = new BufferedReader(fr)) {
                while ((line = br.readLine()) != null) {
                    String data[] = line.split(", |: ");
                    String brandId = data[0];
                    String brandName = data[1];
                    String soundBrand = data[2];
                    double price = Double.parseDouble(data[3]);
                    this.add(new Brand(brandId, brandName, soundBrand, price));
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("File Brands.txt corrupted!"
                        + "\nat: -> " + line
                        + "\nForced exit");
                System.exit(-1);
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean saveToFile(String fileBrands) {
        try {
            FileWriter fw = new FileWriter(fileBrands);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Brand brand : this) {
                bw.write(brand.toString() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public int searchID(String brandId) {
        int pos = App.NOT_FOUND;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getBrandID().equals(brandId)) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    public Brand getUserChoice() {
        Menu mnu = new Menu();
        return (Brand) mnu.ref_getChoice(this);
    }

    public void addBrand() {
        String id = menu.getStringWoSpace("Enter brand ID: ");
        if (searchID(id) == -1) {
            String name = menu.getString("Enter brand name: ");
            String sound = menu.getString("Enter sound brand: ");
            double price = menu.getDouble("Enter price: ");
            this.add(new Brand(id, name, sound, price));
        } else {
            System.out.println(id + " already existed in database! Please enter another brand ID");
        }
    }

    public void listBrands() {
        if (this.isEmpty()) {
            System.out.println("Nothing");
        } else {
            for (Brand b : this) {
                System.out.println(b);
            }
        }
    }

    public void updateBrand() {
        String brandId = menu.getStringWoSpace("Enter brand ID you want to update: ");
        int pos = searchID(brandId);
        if (pos == App.NOT_FOUND) {
            System.out.println("Not found");
        } else {
            this.get(pos).setBrandName(menu.getString("Enter new brand name: "));
            this.get(pos).setSoundBrand(menu.getString("Enter new sound brand: "));
            this.get(pos).setPrice(menu.getDouble("Enter new price: "));
            System.out.println("Update completed!");
        }
    }
}
