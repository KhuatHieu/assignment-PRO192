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
import java.util.List;

public class CarList extends ArrayList<Car> {

    public BrandList brandlist;

    public CarList(BrandList brandlist) {
        this.brandlist = brandlist;
    }

    public Menu menu = new Menu();

    public boolean loadFromFile(String fileCars) {
        boolean result = true;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileCars));
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer stk = new StringTokenizer(line, ", ");
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

    public boolean isExisted(String input, String check) {
        String temp = "";
        for (Car car : this) {
            switch (check) {
                case "carId":
                    temp = car.getCarID();
                    break;
                case "frameId":
                    temp = car.getFrameID();
                    break;
                case "engineId":
                    temp = car.getEngineID();
                    break;
                default:
                    break;
            }

            if (input.equals(temp)) {
                System.out.println(temp + " already existed in database!");
                return true;
            }
        }
        return false;
    }

    public boolean isStartsWith(String input, String ch) {
        if (input.startsWith(ch)) {
            return true;
        } else {
            System.out.println("Must be started with " + ch);
            return false;
        }
    }

    public boolean updateCar() {
        String updateID = menu.getStringWoSpace("Enter car ID ");
        int pos = searchID(updateID);

        if (pos < 0) {
            return false;
        }

        System.out.println("Found and start updating");

        Brand b = (Brand) menu.ref_getChoice(brandlist);
        String frameId, engineId = "";

        Car c = this.get(pos);
        c.setColor(menu.getString("Enter color: "));

        do {
            frameId = menu.getString("Enter frame ID: ");
        } while (isExisted(frameId, "frameId") || !isStartsWith(frameId, "F"));
        c.setFrameID(frameId);

        do {
            engineId = menu.getStringWoSpace("Enter engine ID: ");
        } while (isExisted(engineId, "engineId") || !isStartsWith(engineId, "E"));
        c.setEngineID(engineId);

        return true;
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
        String carId, color, frameId, engineId = "";

        do {
            carId = menu.getString("Enter ID: ");
        } while (isExisted(carId, "carId"));

        color = menu.getStringWoSpace("Enter color: ");

        do {
            frameId = menu.getString("Enter frame ID: ");
        } while (isExisted(frameId, "frameId") || !isStartsWith(frameId, "F"));

        do {
            engineId = menu.getStringWoSpace("Enter engine ID: ");
        } while (isExisted(engineId, "engineId") || !isStartsWith(engineId, "E"));

        Brand b = (Brand) menu.ref_getChoice(brandlist);
        this.add(new Car(carId, b, color, frameId, engineId));
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
