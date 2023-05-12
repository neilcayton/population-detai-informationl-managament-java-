package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MyProgramUtility {
    public SortingMethods sortingMethods = new SortingMethods();
    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        MyProgramUtility newDat = new MyProgramUtility();
        newDat.run();
    }

    public int readChoice() {
        int choice = 0;
        boolean isValid = false;
        while (!isValid) {
            System.out.print("\nEnter choice: ");
            try {

                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Please enter a number.");
                    continue;
                }
                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 7) {
                    isValid = true;
                } else {
                    System.out.print("The number must be from 1 to 7. Please enter again: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("You entered an invalid integer. Please enter integer: ");
            }
        }
        return choice;
    }

    public int readSortingChoice(){
        int choice = 0;
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.print("\n Enter choice: ");
                choice = Integer.parseInt(scanner.nextLine().trim());

                if (choice >= 1 && choice <= 7) {
                    isValid = true;
                } else {
                    System.out.print("The number must be from 1 to 7. Please enter again: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("You entered an invalid integer. Please enter integer: ");
            }
        }
        return choice;
    }

    public void printSortingMenu(){
        System.out.println("""
                Sorting options:

                1. Sort by name
                2. Sort by age
                3. Sort by gender
                4. Sort by address
                5. Sort by email
                6. Sort by resident status
                """);
    }
    public void printMainMenu(){
        System.out.println("""
                Please select an option:

                1. Show district by choice
                2. Show gender distribution
                3. Sort the citizens list
                4. Top Name List
                5. Show age by choice
                6. specific finder
                7. exit""");
    }

    public void printSpecificFinderMenu(){
        System.out.println("\n\n"+"""
                Please select an option:

                1. Show district by choice
                2. Show age by choice
                3. Show gender
                4. Show name
                5. Top Name List
                6. exit""");



    }

    public boolean sortOrder(){
        System.out.print("Type A for Ascending order\nType D for Descending order\n\nType Here: ");
        String order = scanner.nextLine();
    return (order.equalsIgnoreCase("a"));
    }
    public boolean optionFullName(){
        System.out.print("Type F to see top First names\nType L to see top Last names\n\nType Here: ");
        String order = scanner.nextLine();
        return (order.equalsIgnoreCase("f"));
    }
    public int topListFullName(){
        System.out.print("\nAssign number of top name list: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void toSort(List<Citizen> citizens){
        boolean sort;
        SortingMethods chosenSort = new SortingMethods();
        int choice;

            printSortingMenu();
            choice = readSortingChoice();

            switch (choice) {
                case 1 -> {sort = sortOrder();
                    printData(chosenSort.sortNames(citizens,sort));}
                case 2 -> printData(returnGender(citizens));
                case 3 -> {sort = sortOrder();
                    printData(chosenSort.SortAge(citizens,sort));}
                case 4-> {sort = sortOrder();
                    printData(chosenSort.sortEmail(citizens,sort));}
                case 5-> {sort = sortOrder();
                    printData(chosenSort.sortAddress(citizens,sort));}
                case 6 -> printData(returnResident(citizens));

                default -> System.out.println("Invalid choice. Please try again.");

        }

    }
    public void run(){
        int choice = 0;
        try {
            ArrayList<Citizen> citizens = (ArrayList<Citizen>)  readDataFromCSV();

            while (choice!=7){
                printMainMenu();
                choice = readChoice();
                switch (choice) {
                    case 1 -> printData(returnDistrict(citizens));
                    case 2 -> genderDistribution(citizens);
                    case 3 -> toSort(citizens);
                    case 4 -> topNames(citizens);
                    case 5 -> printData(returnAge(citizens));
                    case 6 -> specificFinder(citizens);
                    case 7 -> System.out.println("Going back to main menu...");
                    default -> System.out.println("Invalid choice. Please try again.");
                }

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void specificFinder(List<Citizen> citizens){
        int choice = 0;
        try {
            List<Citizen> listOfCitizens = citizens;
            while (choice!=6){
                printSpecificFinderMenu();
                choice = readChoice();
                switch (choice) {
                    case 1 -> {
                        listOfCitizens = returnDistrict(listOfCitizens);
                        printData(listOfCitizens);
                    }
                    case 2 -> {
                        listOfCitizens = returnAge(listOfCitizens);
                        printData(listOfCitizens);
                    }
                    case 3 -> {
                        listOfCitizens = returnGender(listOfCitizens);
                        printData(listOfCitizens);
                    }
                    case 4 -> {
                        listOfCitizens = returnNameSpecifiedSearch(listOfCitizens);
                        printData(listOfCitizens);
                    }
                    case 5 -> {topNames(listOfCitizens);
                        listOfCitizens = sortingMethods.getTopRecurringNamesOOP(citizens,optionFullName(),topListFullName());
                        }
                    case 6 -> System.out.println("Going back to Main menu...\n");
                    default -> System.out.println("Invalid choice. Please try again.");
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void printData(List<Citizen> citizens){
        System.out.println("\n");
        for(Citizen citizen: citizens){
            System.out.println(citizen.toString());
        }
        System.out.println("\n");
    }
    public File readFile(String fileName){
        URL url = getClass().getResource(fileName);
        if (url == null){
            throw  new IllegalArgumentException("file cannot be found: "+ fileName);
        }
        try {
            URI uri =url.toURI();
            return new File(uri);
        }catch (URISyntaxException e){
            throw new IllegalArgumentException("Invalid: "+url,e);
        }
    }

    public List<Citizen> readDataFromCSV() {
        List<Citizen> citizensList = new ArrayList<>();

        File file = readFile("data.csv");
        BufferedReader reader;
        String line;

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null){
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue; // skip empty lines or lines starting with #
                }
                String [] row = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                try {
                    citizensList.add(new Citizen(row[0]+" "+row[1],
                            row[2],row[3],Integer.parseInt(row[4]),
                            (row[5].equalsIgnoreCase("Resident"))
                            ,Integer.parseInt(row[6]),row[7].charAt(0)));
                }catch (IndexOutOfBoundsException outOfBoundsException){
                    outOfBoundsException.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    return citizensList;
    }

    public void topNames(List<Citizen> citizens){

        String[] topNames = sortingMethods.getTopRecurringNames(citizens, optionFullName(), topListFullName());
        System.out.println("\nTOP NAMES LIST \n");
        for (String top: topNames){
            System.out.println(top);
        }
        System.out.println("\n");
    }

    public List<Citizen> returnAge(List<Citizen> citizens){
        System.out.print("Age that you want to see: ");
        int age = scanner.nextInt();
        return citizens.stream()
                .filter(citizen -> citizen.age == age)
                .collect(Collectors.toList());
    }

    public List<Citizen> returnDistrict(List<Citizen> citizens){
        System.out.print("District that you want to see: ");
        int dist = scanner.nextInt();
        scanner.nextLine(); // consume the remaining newline character
        return citizens.stream()
                .filter(citizen -> citizen.district == dist)
                .collect(Collectors.toList());
    }

    private List<Citizen> returnNameSpecifiedSearch(List<Citizen> citizens){
        System.out.print("Enter Name: ");
        String input = scanner.nextLine();
        return citizens.stream()
                .filter(citizen -> hasDuplicateString(input, citizen.getFullname()))
                .collect(Collectors.toList());
    }
    private boolean hasDuplicateString(String input, String fullname) {
        if (fullname.length() < input.length()) {
            return false; // no need to check if fullname is shorter than input
        }
        for (int i = 0; i <= fullname.length() - input.length(); i++) {
            if (input.equalsIgnoreCase(fullname.substring(i, i + input.length()))) {
                return true; // found a match
            }
        }
        return false; // no match found
    }

    public List<Citizen> returnResident(List<Citizen> citizens) {
        String input;
        while (true) {
            System.out.print("Enter [N (Non-resident) / R (Resident)]: ");
            input = scanner.nextLine().toLowerCase();
            if (input.equals("n") || input.equals("r")) {
                final boolean condition = input.equals("r"); // define a new final variable
                // use the condition to filter the citizens list
                return citizens.stream()
                        .filter(citizen -> citizen.resident == condition)
                        .collect(Collectors.toList());
            } else {
                System.out.println("Invalid input. Please enter either 'N' or 'R'.");
            }
        }
    }
    public List<Citizen> returnGender(List<Citizen> citizens){

        boolean isValid = true;
        String input;
        do {
            System.out.print("Enter Gender [M(male)/F(female)]: ");
            input = scanner.nextLine().toLowerCase();
            if (input.equals("m") || input.equals("f")) {
                isValid = false;
            } else {
                System.out.println("Invalid input. Please enter either 'M' or 'F'.");
            }
        } while (isValid);

        String finalInput = input.toUpperCase();
        return citizens.stream()
                .filter(citizen -> citizen.getGender() == finalInput.charAt(0)).collect(Collectors.toList());
    }

    public void genderDistribution(List<Citizen> citizens){
        int Male = 0;
        int Female = 0;


        Predicate<Citizen> isMale = citizen -> citizen.gender == 'M';

        for (Citizen citizen: citizens){
            if (isMale.test(citizen)){
                Male++;
            }else Female++;
        }

        System.out.println("Male: " + Male);
        System.out.println("Female: " + Female);


    }

}
