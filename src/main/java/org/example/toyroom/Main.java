package org.example.toyroom;

import org.example.toyroom.commands.*;
import org.example.toyroom.models.Input;
import org.example.toyroom.repository.ToyRepository;

public class Main {

    private final ToyRepository toyRepository = new ToyRepository();

    public static void main(String[] args) {
        ToyRoom toyRoom = new ToyRoom();

        while(true) {
            System.out.println(menu());
            int input = Input.getInteger();

            switch (input) {
                case 1 -> new AddItem(toyRoom).execute();
                case 2 -> new ShowItem(toyRoom).execute();
                case 3 -> new FindItem(toyRoom).execute();
                case 4 -> new SortItem(toyRoom).execute();
                case 5 -> new DeleteItem(toyRoom).execute();
                case 6 -> {
                    new ExitItem(toyRoom).execute();
                    return;
                }

                default -> System.out.println("Try again");
            }
        }
    }

    public static String menu() {
        return """
                MainMenu\
                
                 1. Add toy\
                
                 2. Show room\
                
                 3. Find toys\
                
                 4. Sort toys\
                
                 5. Delete toy\
                
                 6. Exit""";
    }
}