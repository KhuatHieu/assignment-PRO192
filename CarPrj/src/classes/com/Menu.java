package classes.com;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu<E> {

    public Scanner scanner = new Scanner(System.in);

    public Menu() {
    }

    public void clearBuffer() {
        scanner = new Scanner(System.in);
    }

    public boolean isEmpty(String input) {
        if (input.isEmpty()) {
            clearBuffer();
            System.out.println("This field must not be empty! Please retype");
            return true;
        }
        return false;
    }

    public boolean isContainSpace(String input) {
        if (input.contains(" ")) {
            clearBuffer();
            System.out.println("This field must not contains space! Please retype");
            return true;
        }
        return false;
    }

    public boolean isPositive(double num) {
        if (num < 0) {
            System.out.println("That number is < 0! Please enter a number bigger than 0");
            return false;
        }
        return true;
    }
    
    public boolean isInt(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(input + " is not a number! Please retype");
            return false;
        }

        return true;
    }

    public boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println(input + " is not a number! Please retype");
            return false;
        }
        
        return true;
    }

    public int getInt() {
        clearBuffer();

        String num;
        do {
            num = scanner.nextLine();
        } while (isEmpty(num) || isContainSpace(num) || !isInt(num) || !isPositive(Integer.parseInt(num)));
        return Integer.parseInt(num);
    }

    public int getInt(String message) {
        System.out.print(message);
        return getInt();
    }

    public double getDouble() {
        clearBuffer();

        String num;
        do {
            num = scanner.nextLine();
        } while (isEmpty(num) || isContainSpace(num) || !isDouble(num) || !isPositive(Double.parseDouble(num)));
        return Double.parseDouble(num);
    }

    public double getDouble(String message) {
        System.out.print(message);
        return getDouble();
    }

    public String getString() {
        clearBuffer();
        String out;

        do {
            out = scanner.nextLine();
        } while (isEmpty(out));

        return out;
    }

    public String getString(String message) {
        System.out.print(message);
        return getString();
    }

    public String getStringWoSpace() {
        clearBuffer();
        String out;
        do {
            out = scanner.nextLine();
        } while (isEmpty(out) || isContainSpace(out));
        return out;
    }

    public String getStringWoSpace(String message) {
        System.out.print(message);
        return getStringWoSpace();
    }

    public int int_getChoice(ArrayList<E> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println(i + " " + options.get(i));
        }
        int response = getInt();
        return response;
    }

    public E ref_getChoice(ArrayList<E> options) {
        int response;
        do {
            response = int_getChoice(options);
        } while (response < 0 || response >= options.size());
        return options.get(response);
    }
}
