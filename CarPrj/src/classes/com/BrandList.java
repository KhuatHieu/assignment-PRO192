/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.com;

import classes.com.Menu;
import classes.com.Brand;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author user
 */
public class BrandList extends ArrayList<Brand> {
    

    public BrandList() {
    }

    public boolean loadFromFile(String fileBrands) {
        boolean result = false;
        try {
            File f = new File(fileBrands);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                StringTokenizer stk = new StringTokenizer(line, ",");
                String brandid = stk.nextElement().toString();
                String brandName = stk.nextElement().toString();
                String soundBrand = stk.nextElement().toString();
                double price = Double.parseDouble(stk.nextToken());
                Brand tmp = new Brand(brandid, brandName, soundBrand, price);
                this.add(tmp);
            }
            System.out.println("End of file. ");
            br.close();
            fr.close();
            result = true;
            System.out.println("Read data from file " + fileBrands + " is completerd. ");
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean saveToFile(String fileBrands) {
        boolean result = false;
        try {
            File f = new File(fileBrands);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (Brand c : this) {
                pw.println(c);
            }
            System.out.println("Save to file " + fileBrands + " is success. ");
            pw.close();
            fw.close();

        } catch (Exception e) {

            return result;

        }
        return result;
    }

    public int searchID(String bID) {
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getBrandID().equals(bID)) {
                index = i;
            } else {
                index = index;
            }
        }
        return index;
    }

    public Brand getUserChoice() {
        Menu mnu = new Menu();
        return (Brand) mnu.ref_getChoice(this);
    }

    public void addBrand() {  
        
        Brand b = new Brand();
        Scanner sc = new Scanner(System.in);
        String id = sc.nextLine();
        String name = sc.nextLine();
        String sound =sc.nextLine();
        double price = Double.parseDouble(sc.nextLine());
        while (b.getPrice() < 0) {
            System.out.println("Please enter again price: ");
            price = Double.parseDouble(sc.nextLine());
        }
        b = new Brand(id, name, sound, price);
        this.add(b);
        
    }

    public void listBrands() {
        for (Brand b : this) {
            System.out.println(b);
        }
    }

    public void updateBrand() {
        String brandID;
        Scanner sc = new Scanner(System.in);
        System.out.print("BrandID want to find:");
        brandID = sc.nextLine();
        int Pos = searchID(brandID);
        if (Pos < 0) {
            System.out.println("Not found!");
        } else {
            Scanner pc = new Scanner(System.in);
            System.out.println("Found and start changing: ");
            for (int i = 0; i < this.size(); i++) {

                System.out.print("\nEnter new BrandName: ");
                this.get(Pos).setBrandName(sc.nextLine());
                while (this.get(Pos).getBrandName().contains(" ")) {
                    System.out.println("Brand name must not be has the space.");
                    this.get(Pos).setBrandName(pc.nextLine());
                }
                System.out.print("\nEnter new soundBrand: ");
                this.get(Pos).setSoundBrand(sc.nextLine());
                while (this.get(Pos).getSoundBrand().contains(" ")) {
                    System.out.println("SoundBrand must");
                }

                System.out.print("\nEnter new price: ");
                this.get(Pos).setPrice(pc.nextDouble());
                while (this.get(Pos).getPrice() < 0) {
                    System.out.print("Error and please enter again price must be >= 0: ");
                    this.get(Pos).setPrice(pc.nextDouble());
                }
            }

        }
    }

}
