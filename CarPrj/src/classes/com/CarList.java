/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.com;

import classes.com.Brand;
import classes.com.BrandList;
import classes.com.Car;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class CarList extends ArrayList<Car> {

    BrandList brandlist;

    public CarList(BrandList brandlist) {
        this.brandlist = brandlist;
    }
    public Menu menu = new Menu();

    public boolean loadFromFile(String fileCars) {
        boolean result = false;
        try {
            File f = new File(fileCars);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                StringTokenizer stk = new StringTokenizer(line, ",");
                String carID = stk.nextElement().toString();
                String brandID = stk.nextElement().toString();
                String color = stk.nextElement().toString();
                String frameID = stk.nextElement().toString();
                String engineID = stk.nextElement().toString();
                int pos = brandlist.searchID(brandID);
                Brand b = brandlist.get(pos);
                Car tmp = new Car(carID, b, color, frameID, engineID);
                this.add(tmp);
            }
            System.out.println("End of file. ");
            br.close();
            fr.close();
            result = true;
            System.out.println("Read data from file " + fileCars + " is completerd. ");
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean saveToFile(String fileCars) {
        boolean result = false;
        try {
            File f = new File(fileCars);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);

            for (Car c : this) {
                pw.println(c);
            }
            pw.close();
            fw.close();

        } catch (Exception e) {

            return result;

        }
        return result;
    }

    public int searchID(String CarID) {
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getCarID() == CarID) {
                index = i;
            } else {
                index = index;
            }
        }
        return index;
    }

    public int searchFrame(String fID) {
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getFrameID() == fID) {
                index = i;
            } else {
                index = -1;
            }
        }
        return index;
    }

    public int searchEngine(String eID) {
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getEngineID() == eID) {
                index = i;
            } else {
                index = -1;
            }
        }
        return index;
    }

    public boolean removeCar() {
        boolean yup = true;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ID of car wants to remove: ");
        String removedID = sc.nextLine();
        int pos = searchID(removedID);
        if (pos < 0) {
            System.out.print("Not found!");
            yup = false;
        } else {
            this.remove(pos);
        }
        return yup;
    }

    public boolean updateCar() {  
        boolean yup = true;
        String updateID = menu.getStringWoSpace("Enter CarID: ");
        int pos = searchID(updateID);
        if (pos < 0) {
            System.out.print("Not found!");
            yup = false;
        } else {            
            Brand b = (Brand) menu.ref_getChoice(brandlist);            
            System.out.println("Found and start changing: ");
            this.get(pos).setColor(menu.getString("Enter Color: "));
            this.get(pos).setFrameID(menu.getString("Enter frameID: "));
            for (int i = 0; i < this.size(); i++) {
                String tmp = this.get(i).getFrameID();
                while(tmp.equals(this.get(pos).getFrameID()) && !tmp.startsWith("F")) {
                    System.out.println("frameID was been duplicated or need to start with F.");
                    this.get(pos).setFrameID("Enter frameID: ");
                }
            }this.get(pos).setEngineID(menu.getString("Enter EngineID: "));
            for (int i = 0; i < this.size(); i++) {
                String tmp = this.get(i).getEngineID();
                while(tmp.equals(this.get(pos).getEngineID()) && !tmp.startsWith("E")) {
                    System.out.println("eengineID was been duplicated or need to start with E.");
                    this.get(pos).setEngineID(menu.getString("Enter engineID: "));
                }
            }
        }
        return yup;
    }

    public void listCars() {
        Collections.sort(this, new Comparator<Car>() {
            @Override
            public int compare(Car o1, Car o2) {
                if (!o1.brand.getBrandName().equals(o2.brand.getBrandName())) {
                    return o1.brand.getBrandName().compareTo(o2.brand.getBrandName());
                } else {
                    return o1.brand.getBrandID().compareTo(o2.brand.getBrandID());
                }
            }
        });
        for (int i = 0; i < this.size(); i++) {
            Car c = this.get(i);
            System.out.println(c.screnString());
        }
    }

    public void addCar() {

        String id = menu.getString("Enter new id: ");
        for (int i = 0; i < this.size(); i++) {
            while (this.get(i).getCarID().equals(id)) {
                System.out.println("ID was been duplicated. Enter again: ");
                id = menu.getString("Enter ID: ");
            }
        }
        String color = menu.getStringWoSpace("Enter color: ");
        Brand b = (Brand) menu.ref_getChoice(brandlist);
        String frameID = menu.getStringWoSpace("Enter frameID: ");
        for (int i = 0; i < this.size(); i++) {
            String tmp = this.get(i).getFrameID();
            while (tmp.equals(frameID) && !tmp.startsWith("F")) {
                System.out.println("frameID was been duplicated or need to start with F. Enter again: ");
                frameID = menu.getStringWoSpace("Enter frameID: ");
            }
        }
        String engineID = menu.getStringWoSpace("Please enter engineID: ");
        for (int i = 0; i < this.size(); i++) {
            String tmp = this.get(i).getEngineID();
            while (tmp.equals(engineID) && !tmp.startsWith("E")) {
                System.out.println("engineID was been duplicated or need to start with E. Enter again:  ");
                engineID = menu.getStringWoSpace("Please enter engineID: ");
            }
        }
        Car c = new Car(id, b, color, frameID, engineID);
        this.add(c);
    }

    public void printtBasedBrandName() {
        System.out.println("Brand name of list: ");
    }
}
