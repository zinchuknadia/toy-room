package org.example.toyroom;


import org.example.toyroom.models.*;
import org.example.toyroom.repository.ToyRepository;
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

//    private List<Toy> toyList;
    private String filePath = "D:\\java_projects\\ToyRoom\\playRoomData.txt";
    private final ToyRepository toyRepository = new ToyRepository();


//    public ToyRoom(){
//        this.toyList = new ArrayList<>();
//    }

    public ToyRepository getToyRepository() {
        return toyRepository;
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
                    toyRepository.add(toy);
                    System.out.println("\nToy was added");
                }
                case 2 -> {
                    if(readFile())
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
        String hex = Input.getString().toLowerCase();
        Color color = new Color(hex);
        System.out.println("Material:");
        String material = Input.getString().toLowerCase();
        return new Toy(type, size, color, material);
    }

    public boolean readFile(){
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] details = line.split(",");
                String type = details[0];
                Size size = parseSize(details[1]);
                String hex = details[2].toLowerCase();
                Color color = new Color(hex);
                String material = details[3];
                toyRepository.add(new Toy(type, size, color, material));
            }
            return true;
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    private static Size parseSize(String sizeStr){
        switch(sizeStr.toLowerCase()){
            case "large": return Size.LARGE;
            case "medium": return Size.MEDIUM;
            case "small": return Size.SMALL;
            default: throw new IllegalArgumentException("Invalid Size: " + sizeStr);
        }
    }

    @Override
    public void show() {
        List<Toy> toys = toyRepository.findAll();
        System.out.println("\nToy room\n" + toys);
    }


    @Override
    public void find() {
         while (true){
             System.out.println("""
                    
                    1. Find by size\
                    
                    2. Find by color\
                    
                    3. Back""");

             int input = Input.getInteger();
             switch(input){
                 case 1 -> {
                     System.out.println("size:");
                     Size size = parseSize(Input.getString());
                     List<Toy> foundToys = toyRepository.findBySize(size);
                     System.out.println(foundToys);
                 }
                 case 2 -> {
                     System.out.println("color:");
                     String hex = Input.getString();
                     Color color = new Color(hex);
                     List<Toy> foundToys = toyRepository.findByColor(color);
                     System.out.println(foundToys);
                 }
                 case 3 -> {
                     return;
                 }
                 default -> System.out.println("Try again");
             }
             System.out.println("Toys were found");
         }
    }

    @Override
    public void sort() {

        List<Toy> toys = toyRepository.findAll();
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
        System.out.println("Choose toy:\n");

        List<Toy> toys = toyRepository.findAll();
        for (int i = 0; i < toys.size(); i++) {
            System.out.println((i + 1) + ". " + toys.get(i));
        }

        int index;
        do {
            index = Input.getInteger();
        } while (0 >= index || index > toys.size());

        int toyId = toys.get(index - 1).getId();
        toyRepository.deleteById(toyId);
        System.out.println("Toy was deleted");
    }

    @Override
    public void exit() {
        System.out.println("exiting...");
    }

}
