package org.example.toyroom;


import org.example.toyroom.models.*;
import org.example.toyroom.units.ToyColorComparator;
import org.example.toyroom.units.ToyFinder;
import org.example.toyroom.units.ToySizeComparator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToyRoom implements Room {

    private List<Toy> toyList;
    private String filePath = "D:\\java_projects\\playRoomData.txt";

    public ToyRoom(){
        this.toyList = new ArrayList<>();
    }

    @Override
    public void add() {

        while(true) {
            System.out.println("""
                    
                    1. Add toy\
                    
                    2. Read file\
                    
                    3. Back""");

            int input = Input.getInteger();

            switch (input) {
                case 1 -> {
                    Toy toy = createToy();
                    toyList.add(toy);
                    System.out.println("\nToy was added");
                }
                case 2 -> {
                    toyList.addAll(readFile());
                    System.out.println("File was read");
                }
                case 3 -> {
                    return;
                }

                default -> System.out.println("Try again");
            }
        }
    }

    public Toy createToy(){
        System.out.println("type:");
        String type = Input.getString();
        System.out.println("Size:");
        Size size = parseSize(Input.getString());
        System.out.println("color:");
        String color = Input.getString().toLowerCase();
        return new Toy(type, size, color);
    }

    public List<Toy> readFile(){
        List<Toy> toys = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] details = line.split(",");
                String type = details[0];
                Size size = parseSize(details[1]);
                String color = details[2].toLowerCase();
//                String material = details[3];
//                Double cost = Double.valueOf(details[4]);
                toys.add(new Toy(type, size, color));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return toys;
    }

    private static Size parseSize(String sizeStr){
        switch(sizeStr.toLowerCase()){
            case "big": return Size.BIG;
            case "medium": return Size.MEDIUM;
            case "small": return Size.SMALL;
            default: throw new IllegalArgumentException("Invalid Size: " + sizeStr);
        }
    }

    @Override
    public void show() {
        System.out.println(this);
        System.out.println("\nRoom was shown");
    }

    @Override
    public String toString() {
        return "\nToy room " +
                "\ntoyList=" + toyList;
    }

    @Override
    public void find() {
         while (true){
             System.out.println("""
                    
                    1. Find by size\
                    
                    2. Find by color\
                    
                    3. Find by all\
                    
                    4. Back""");

             int input = Input.getInteger();
             switch(input){
                 case 1 -> {
                     System.out.println("size:");
                     Size size = parseSize(Input.getString());
                     List<Toy> foundToys = ToyFinder.findToysBySize(toyList, size);
                     System.out.println(foundToys);
                 }
                 case 2 -> {
                     System.out.println("color:");
                     String color = Input.getString();
                     List<Toy> foundToys = ToyFinder.findToysByColor(toyList, color);
                     System.out.println(foundToys);
                 }
                 case 3 -> {
                     System.out.println("size:");
                     Size size = parseSize(Input.getString());
                     System.out.println("color:");
                     String color = Input.getString();
                     List<Toy> foundToys = ToyFinder.findToysByColorAndSize(toyList, color, size, false);
                     System.out.println(foundToys);

                     System.out.println("more(y/n):");
                     if (Input.getString().equals("y")){
                         List<Toy> foundToysOr = ToyFinder.findToysByColorAndSize(toyList, color, size, true);
                         System.out.println(foundToysOr);
                     }
                 }
                 case 4 -> {
                     return;
                 }
                 default -> System.out.println("Try again");
             }
             System.out.println("Toys were found");
         }
    }

    @Override
    public void sort() {

        List<Toy> toys = new ArrayList<>(List.copyOf(toyList));
        while(true){
            System.out.println("""
                    
                    1. Sort by size\
                    
                    2. Sort by color\
                    
                    3. Sort all\
                    
                    4. Back""");

            int input = Input.getInteger();
            switch(input) {
                case 1 -> Collections.sort(toys, new ToySizeComparator());
                case 2 -> Collections.sort(toys, new ToyColorComparator());
                case 3 -> toys.sort(new ToySizeComparator().thenComparing((new ToyColorComparator())));
                case 4 -> {
                    return;
                }
                default -> System.out.println("Try again");
            }
            System.out.println(toys);
            System.out.println("Toys were sorted");
        }
    }

    @Override
    public void delete() {
        System.out.println("Choose toy:" +
                toyList);
        int index;
        do {
            index = Input.getInteger();
        } while (0 >= index || index > toyList.size());

        toyList.remove(index - 1);
        System.out.println("Toy was deleted");
    }

    @Override
    public void exit() {
        System.out.println("exiting...");
    }

}
