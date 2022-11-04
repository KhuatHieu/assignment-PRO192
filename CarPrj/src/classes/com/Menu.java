package classes.com;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu<E> {

    public Scanner scanner = new Scanner(System.in);

    public Menu() {
    }

    public int getInt() {
        Scanner scanner = new Scanner(System.in);
        int num;
        do {
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println(input + " is not a number. Please retype");
            }
            num = scanner.nextInt();
            if (num < 0) {
                System.out.println(num + " is smaller than 0. Please enter a number greater than 0");
            }
        } while (num < 0);

        return num;
    }

    public int getInt(String message) {
        System.out.println(message);
        return getInt();
    }

    public double getDouble() {
        Scanner scanner = new Scanner(System.in);
        double num;
        do {
            while (!scanner.hasNextDouble()) {
                String input = scanner.next();
                System.out.println(input + " is not a number. Please retype");
            }
            num = scanner.nextInt();
            if (num < 0) {
                System.out.println(num + " is smaller than 0. Please enter a number greater than 0");
            }
        } while (num < 0);

        return num;
    }

    public double getDouble(String message) {
        System.out.println(message);
        return getDouble();
    }

    public int int_getChoice(ArrayList<E> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println(i + " " + options.get(i));
        }
        int response = scanner.nextInt();
        return response;
    }

    public E ref_getChoice(ArrayList<E> options) {
        int response;
        do {
            response = int_getChoice(options);
        } while (response < 0 || response > options.size());
        return options.get(response - 1);
    }

}
