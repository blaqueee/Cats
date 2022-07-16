package com;

import com.Exceptions.EmptyInputException;
import com.FileService.FileReaderWriter;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Cat> cats = new FileReaderWriter("cats.json").readFile();
        cats.forEach(Cat::updateAverageState);

        cats = sortCat(cats);
        int days = 1;
        while (true) {
            System.out.printf("\n                            <=== DAY %d ===>%n", days);
            cats = doAction(cats);
            days++;
            nextDay(cats);
            if (cats.size() == 0) {
                System.out.println("     <====== NO CATS LEFT - GAME OVER ======>");
                return;
            }
        }
    }

    private static List<Cat> doAction(List<Cat> cats) {
        while (true) {
            new FileReaderWriter("cats.json").writeFile(cats);

            removeDeadCats(cats);
            if (cats.size() == 0) {
                return cats;
            }

            printCats(cats);

            switch (askAction()) {
                case 1:
                    int catNumber = chooseCat(cats);
                    cats.get(catNumber - 1).feed();
                    break;
                case 2:
                    catNumber = chooseCat(cats);
                    cats.get(catNumber - 1).play();
                    break;
                case 3:
                    catNumber = chooseCat(cats);
                    cats.get(catNumber - 1).goToVet();
                    break;
                case 4:
                    cats.add(addNewCat());
                    break;
                case 5:
                    return cats;
                case 6:
                    cats = sortCat(cats);
                    break;
            }
        }
    }

    private static void removeDeadCats(List<Cat> cats) {
        var deadCats = cats.stream()
                .filter(cat -> cat.getHealth() == 0)
                .collect(toList());
        if (deadCats.size() != 0) {
            deadCats.forEach(cat -> removeCat(cats, cat));
            System.out.print("\nCATS DEAD: ");
            deadCats.forEach(cat -> System.out.printf("%s | ", cat.getName()));
            System.out.println("\n");
        }
    }

    private static void removeCat(List<Cat> cats, Cat deadCat) {
        cats.remove(deadCat);
    }

    private static void nextDay(List<Cat> cats) {
        cats.forEach(cat -> {
            cat.setNextDayParameters();
            cat.setInitialState();
        });
    }

    private static int chooseCat(List<Cat> cats) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.printf("Choose cat (1 - %s): ", cats.size());
            String catNumber = sc.nextLine().replaceAll("\\s+", "");
            try {
                return checkCatNumber(catNumber, cats.size());
            } catch (EmptyInputException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static int checkCatNumber(String number, int listSize) throws EmptyInputException {
        if (number.equals(""))
            throw new EmptyInputException("\nYou did not enter the number!\n");
        int choice = Integer.parseInt(number);
        if (choice < 1 || choice > listSize)
            throw new IllegalArgumentException("\nCan't find the cat with this number!\n");
        return choice;
    }

    private static int askAction() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            printActions();
            String action = sc.nextLine().replaceAll("\\s+", "");
            try {
                return checkAction(action);
            } catch (EmptyInputException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static int checkAction(String str) throws EmptyInputException {
        if (str.equals(""))
            throw new EmptyInputException("\nChoice can't be empty!\n");
        int choice = Integer.parseInt(str);
        if (choice < 1 || choice > 6)
            throw new IllegalArgumentException("\nCan't find this choice!\n");
        return choice;
    }

    private static void printActions() {
        System.out.println("""
                1 -> Feed cat
                2 -> Play with cat
                3 -> Go to vet
                4 -> Add a new cat
                5 -> Next day
                6 -> Choose type of sorting
                """);
        System.out.print("Choose action: ");
    }

    private static Cat addNewCat() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter name of new cat: ");
            String name = sc.nextLine().replaceAll("\\s+", "");
            System.out.print("Enter the age of new cat: ");
            String strAge = sc.nextLine().replaceAll("\\s+", "");
            try {
                int age = checkNameAndAge(name, strAge);
                return new Cat(name, age);
            } catch (EmptyInputException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static int checkNameAndAge(String name, String strAge) throws EmptyInputException {
        if (strAge.equals(""))
            throw new EmptyInputException("\nAge can't be empty!\n");
        int age = Integer.parseInt(strAge);
        if (age < 1 || age > 18)
            throw new IllegalArgumentException("\nAge should be between 1 and 18!\n");

        if (name.equals(""))
            throw new EmptyInputException("\nName can't be empty!\n");
        String[] numbers = new String[10];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = String.valueOf(i);
            if (name.contains(numbers[i]))
                throw new IllegalArgumentException("\nName can't be a number!\n");
        }
        return age;
    }

    private static void printCats(List<Cat> cats) {
        System.out.println("+-----+----------+----------+----------+----------+----------+----------+\n" +
                "|  #  |   NAME   |   AGE    |  HEALTH  |   MOOD   | SATIETY  | AVERAGE  |\n" +
                "+-----+----------+----------+----------+----------+----------+----------+");
        for (int i = 0; i < cats.size(); i++) {
            System.out.printf("|  %-2d | %-8s |    %-5d |    %-5d |    %-5d |    %-5d |    %-5d |%n",
                    i + 1,
                    cats.get(i).getName(),
                    cats.get(i).getAge(),
                    cats.get(i).getHealth(),
                    cats.get(i).getMood(),
                    cats.get(i).getSatiety(),
                    cats.get(i).getAverageState());
        }
        System.out.println("+-----+----------+----------+----------+----------+----------+----------+");
    }

    private static List<Cat> sortCat(List<Cat> cats) {
        switch (askSortType()) {
            case 1:
                return cats.stream()
                        .sorted(comparing(Cat::getName))
                        .collect(toList());
            case 2:
                return cats.stream()
                        .sorted(Collections.reverseOrder(comparingInt(Cat::getAge)))
                        .collect(toList());
            case 3:
                return cats.stream()
                        .sorted(Collections.reverseOrder(comparingInt(Cat::getHealth)))
                        .collect(toList());
            case 4:
                return cats.stream()
                        .sorted(Collections.reverseOrder(comparingInt(Cat::getMood)))
                        .collect(toList());
            case 5:
                return cats.stream()
                        .sorted(Collections.reverseOrder(comparingInt(Cat::getSatiety)))
                        .collect(toList());
            default:
                return cats.stream()
                        .sorted(Collections.reverseOrder(comparingInt(Cat::getAverageState)))
                        .collect(toList());
        }
    }

    private static void printSortTypes() {
        System.out.println("""
                1 -> Sort by NAME
                2 -> Sort by AGE
                3 -> Sort by HEALTH
                4 -> Sort by MOOD
                5 -> Sort by SATIETY
                6 -> Sort by AVERAGE
                """);
        System.out.print("Choose type sort: ");
    }

    private static int askSortType() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            printSortTypes();
            String type = sc.nextLine().replaceAll("\\s+", "");
            try {
                return checkSortNumber(type);
            } catch (EmptyInputException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static int checkSortNumber(String str) throws EmptyInputException {
        if (str.equals(""))
            throw new EmptyInputException("\nChoice can't be empty!\n");
        int choice = Integer.parseInt(str);
        if (choice < 1 || choice > 6)
            throw new IllegalArgumentException("\nCan't find this type of sort!\n");
        return choice;
    }
}