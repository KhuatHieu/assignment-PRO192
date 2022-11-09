package classes.com;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.io.IOException;

public class CarList extends ArrayList<Car> {

    public BrandList brandlist;

    public CarList(BrandList brandlist) {
        this.brandlist = brandlist;
    }

    public Menu menu = new Menu();

    public boolean loadFromFile(String fileCars) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileCars));
            String line;
            while ((line = br.readLine()) != null) {
                String data[] = line.split(", ");
                String carID = data[0];
                String brandID = data[1];
                String color = data[2];
                String frameID = data[3];
                String engineID = data[4];
                int pos = brandlist.searchID(brandID);
                Brand b = brandlist.get(pos);
                this.add(new Car(carID, b, color, frameID, engineID));
            }
            br.close();
        } catch (IOException e) {
            return false;
        }
        
        return true;
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

    public int searchId(String carID) {
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getCarID().equals(carID)) {
                index = i;
            }
        }
        return index;
    }

    public int searchFrame(String frameId) {
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getFrameID().equals(frameId)) {
                index = i;
            }
        }
        return index;
    }

    public int searchEngine(String engineId) {
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getEngineID().equals(engineId)) {
                index = i;
            }
        }
        return index;
    }

    public boolean removeCar() {
        String idRemove = menu.getString("Enter ID you want to remove: ");
        int pos = searchId(idRemove);
        if (pos < 0) {
            return false;
        } else {
            this.remove(pos);
            return true;
        }
    }

    public boolean isExisted(String input, String mode) {
        String temp = "";
        for (Car car : this) {
            switch (mode.toLowerCase()) {
                case "carid":
                    temp = car.getCarID();
                    break;
                case "frameid":
                    temp = car.getFrameID();
                    break;
                case "engineid":
                    temp = car.getEngineID();
                    break;
                default:
                    break;
            }

            if (input.equals(temp)) {
                System.out.println(temp + " already existed in database! Please retype");
                return true;
            }
        }
        return false;
    }

    public boolean isInPattern(String input, String ch) {
        if (!input.startsWith(ch)) {
            System.out.println("Must be started with " + ch + "! Please retype");
            return false;
        }

        char tmp[] = input.toCharArray();
        for (int i = 1; i < input.length(); i++) {
            if (!Character.isDigit(tmp[i]) || input.length() != 6) {
                System.out.println("Must be in " + ch + "00000 format! Please retype");
                return false;
            }
        }

        return true;
    }

    public boolean updateCar() {
        String updateId = menu.getStringWoSpace("Enter car ID ");
        int pos = searchId(updateId);

        if (pos < 0) {
            return false;
        }
        Car c = this.get(pos);

        System.out.println("Enter new brand index: ");
        Brand b = (Brand) menu.ref_getChoice(brandlist);
        c.setBrand(b);
        
        String frameId, engineId = "";
        
        c.setColor(menu.getString("Enter new color: "));

        do {
            frameId = menu.getString("Enter new frame ID: ");
        } while (isExisted(frameId, "frameId") || !isInPattern(frameId, "F"));
        c.setFrameID(frameId);

        do {
            engineId = menu.getStringWoSpace("Enter new engine ID: ");
        } while (isExisted(engineId, "engineId") || !isInPattern(engineId, "E"));
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

        System.out.println("Enter brand index: ");
        Brand b = (Brand) menu.ref_getChoice(brandlist);
        
        color = menu.getStringWoSpace("Enter color: ");

        do {
            frameId = menu.getString("Enter frame ID: ");
        } while (isExisted(frameId, "frameId") || !isInPattern(frameId, "F"));

        do {
            engineId = menu.getStringWoSpace("Enter engine ID: ");
        } while (isExisted(engineId, "engineId") || !isInPattern(engineId, "E"));
        
        this.add(new Car(carId, b, color, frameId, engineId));
    }

    public void printBasedBrandName() {
        int count = 0;
        String name = menu.getString("Enter brand name you want to find: ");
        for (Car c : this) {
            if (c.getBrand().getBrandName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(c + " [" + c.getBrand().getBrandName() + "]");
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Not found any car with this brand name!");
        }
    }
}
