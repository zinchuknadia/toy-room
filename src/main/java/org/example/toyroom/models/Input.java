package org.example.toyroom.models;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
     private static Scanner scanner = new Scanner(System.in);

    public static int getInteger(){
        int input = 0;
        while(true) {

            try {
                input = (scanner.nextInt());
            } catch (InputMismatchException e) {
                System.out.println("Try again");
                continue;
            } finally {
                scanner.nextLine();
            }
            return input;
        }
    }

    public static String getString(){
        return scanner.nextLine();
    }
}
