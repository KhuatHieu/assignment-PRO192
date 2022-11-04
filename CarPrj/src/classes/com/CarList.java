package classes.com;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

import java.io.IOException;

public class CarList extends ArrayList<Car> {

    public BrandList brandlist;

    public CarList(BrandList brandlist) {
        this.brandlist = brandlist;
    }

    public Menu menu = new Menu();

    public boolean loadFromFile(String fileCars) {
        boolean result;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileCars));
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer stk = new StringTokenizer(line, ",");
                String carID = stk.nextElement().toString();
                String brandID = stk.nextElement().toString();
                String color = stk.nextElement().toString();
                String frameID = stk.nextElement().toString();
                String engineID = stk.nextElement().toString();
                int pos = brandlist.searchID(brandID);
                Brand b = brandlist.get(pos);
                this.add(new Car(carID, b, color, frameID, engineID));
            }
            br.close();
            result = true;
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    public boolean saveToFile(String fileCars) {
        boolean result;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileCars));
            for (Car c : this) {
                bw.write(c.toString() + "\n");
            }
            bw.close();
            result = true;
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    public int searchID(String carID) {
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getCarID().equals(carID)) {
                index = i;
            }
        }
        return index;
    }

    public int searchFrame(String fId) {
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getFrameID().equals(fId)) {
                index = i;
            }
        }
        return index;
    }

    public int searchEngine(String eId) {
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getEngineID().equals(eId)) {
                index = i;
            }
        }
        return index;
    }

    public boolean removeCar() {
        boolean found = false;
        String idRemove = menu.getString("Enter ID you want to remove: ");
        int pos = searchID(idRemove);
        if (pos < 0) {
            System.out.print("Not found!");
        } else {
            this.remove(pos);
            found = true;
        }
        return found;
    }

    public boolean updateCar() {
        boolean updated = false;
        String updateID = menu.getStringWoSpace("Enter car ID ");
        int pos = searchID(updateID);
        if (pos < 0) {
            System.out.print("Not found!");
            updated = false;
        } else {
            Brand b = (Brand) menu.ref_getChoice(brandlist);
            System.out.println("Found and start updating");
            Car c = this.get(pos);
            c.setColor(menu.getString("Enter color: "));
            c.setFrameID(menu.getString("Enter frame ID: "));
            for (Car car : this) {
                String tmp = car.getFrameID();
                while (tmp.equals(c.getFrameID()) || !tmp.startsWith("F")) {
                    c.setFrameID("This frame ID has been duplicated or need to start with F\nEnter new frame ID: ");
                }
            }
            c.setEngineID(menu.getString("Enter new engine ID: "));
            for (Car car : this) {
                String tmp = car.getEngineID();
                while (tmp.equals(c.getEngineID()) || !tmp.startsWith("E")) {
                    System.out.println("This engine ID has been duplicated or need to start with E");
                    c.setEngineID(menu.getString("Enter new engine ID: "));
                }
            }
        }
        return updated;
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
        for (Car c : this) {
            System.out.println(c.screnString());
        }
    }

    public void addCar() {
        String id = menu.getString("Enter ID: ");
        for (Car car : this) {
            while (car.getCarID().equals(id)) {
                System.out.println(id + " already existed. Enter again: ");
                id = menu.getString("Enter ID: ");
            }
        }
        String color = menu.getStringWoSpace("Enter color: ");
        Brand b = (Brand) menu.ref_getChoice(brandlist);
        String frameID = menu.getStringWoSpace("Enter frame ID: ");
        for (int i = 0; i < this.size(); i++) {
            String tmp = this.get(i).getFrameID();
            while (tmp.equals(frameID) && !tmp.startsWith("F")) {
                System.out.println("This frame ID has been duplicated or need to start with F. Enter again: ");
                frameID = menu.getStringWoSpace("Enter frame ID: ");
            }
        }
        String engineID = menu.getStringWoSpace("Please enter engine ID: ");
        for (Car car : this) {
            String tmp = car.getEngineID();
            while (tmp.equals(engineID) && !tmp.startsWith("E")) {
                System.out.println("This engine ID has been duplicated or need to start with E. Enter again:  ");
                engineID = menu.getStringWoSpace("Please enter engine ID: ");
            }
        }
        this.add(new Car(id, b, color, frameID, engineID));
    }

    public void printBasedBrandName() {
        int count = 0;
        String name = menu.getString("Enter brand name you want to find: ");
        for (Car c : this) {
            if (c.brand.getBrandName().matches(name)) {
                System.out.println(c);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No car is detected!");
        }
    }
}
