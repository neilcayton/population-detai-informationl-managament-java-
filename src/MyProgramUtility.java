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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MyProgramUtility {
    private List<Citizen> listOfCitizens;
    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        MyProgramUtility newDat = new MyProgramUtility();
        newDat.run();
    }




    public int readChoice(){
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
                7. Sort by registration date
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
        System.out.println("""
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
    public boolean OptionFullname(){
        System.out.print("Type F to see top First names\nType L to see top Last names\n\nType Here: ");
        String order = scanner.nextLine();
        return (order.equalsIgnoreCase("f"));
    }
    public int TopListFullname(){
        System.out.print("\nAssign number of top name list: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void toSort(List<Citizen> citizens){
        boolean sort;
        SortingMethods chosenSort = new SortingMethods();
        int choice;

            printSortingMenu();
            choice = readSortingChoice();
            sort = sortOrder();
            switch (choice) {
                case 1 -> printData(chosenSort.SortNames(citizens,sort));
                case 2 -> printData(chosenSort.SortAge(citizens,sort));
                case 3 -> System.out.println("Exiting program...");
                case 4-> System.out.println("going back");
                default -> System.out.println("Invalid choice. Please try again.");

        }

    }
    public void run(){
        SortingMethods sortingMethods = new SortingMethods();
        int choice = 0;
        try {
            ArrayList<Citizen> citizens = (ArrayList<Citizen>)  readDataFromCSV();
            listOfCitizens = citizens;
            while (choice!=7){
                printMainMenu();
                choice = readChoice();
                switch (choice) {
                    case 1 -> printData(returnDistrict(listOfCitizens));
                    case 2 -> genderDistribution(citizens);
                    case 3 -> toSort(citizens);
                    case 4 -> printData(sortingMethods.getTopRecurringNames(citizens,OptionFullname(),TopListFullname()));
                    case 5 -> printData(returnAge(listOfCitizens));
                    case 6 -> specificFinder(citizens,sortingMethods);
                    case 7 -> System.out.println("Exiting program...");
                    default -> System.out.println("Invalid choice. Please try again.");
                }

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void specificFinder(List<Citizen> citizens, SortingMethods sortingMethods){
        int choice = 0;
        try {
            listOfCitizens = citizens;
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
                    case 5 -> printData(sortingMethods.getTopRecurringNames(citizens,OptionFullname(),TopListFullname()));
                    case 6 -> System.out.println("Exiting program...");
                    default -> System.out.println("Invalid choice. Please try again.");
                }

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void printData(List<Citizen> citizens){
        for(Citizen citizen: citizens){
            System.out.println(citizen.toString());
        }

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

    public List<Citizen> returnAge(List<Citizen> citizens){
        List<Citizen> newCitizen = new ArrayList<>();
        System.out.print("Age that you want to see: ");
        int age = scanner.nextInt();
        Predicate<Citizen> predicate = citizen -> citizen.age == age;
        Function<Citizen, Citizen> function= o -> {
            if(predicate.test(o)) newCitizen.add(o);
            return o;
        };
        citizens.stream().map(function).collect(Collectors.toList());
        return newCitizen;
    }

    public List<Citizen> returnDistrict(List<Citizen> citizens){
        List<Citizen> newCitizen = new ArrayList<>();
        System.out.print("District that you want to see: ");
        int dist = scanner.nextInt();
        Predicate<Citizen> predicate = citizen -> citizen.district == dist;
        Function<Citizen, Citizen> function= o -> {
            if(predicate.test(o)) newCitizen.add(o);
            return o;
        };
        citizens.stream().map(function).collect(Collectors.toList());
        return newCitizen;
    }

    public List<Citizen> returnNameSpecifiedSearch(List<Citizen> citizens){
        System.out.print("Enter Name: ");

        String input = scanner.nextLine();

        return citizens.stream()
                .filter(citizen -> hasDuplicateString(input, citizen.getFullname()))
                .collect(Collectors.toList());
    }
    public boolean hasDuplicateString(String input, String str) {
        String[] nameParts = str.split("\\s+"); // split the name into parts using whitespace
        for (String part : nameParts) {
            if (input.equalsIgnoreCase(part)) { // check if the input matches any of the parts
                return true;
            }
        }
        return false;
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
