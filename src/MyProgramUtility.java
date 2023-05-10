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

                if (choice >= 1 && choice <= 5) {
                    isValid = true;
                } else {
                    System.out.print("The number must be from 1 to 5. Please enter again: ");
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
                4. Exit""");



    }

    public boolean sortOrder(){
        System.out.print("" +
                "Type A for Ascending order\n" +
                "Type D for Descending order\n" +
                "\nType Here: ");
        String order = scanner.nextLine();
    return (order.equalsIgnoreCase("a"));
    }

    public void toSort(List<Citizen> citizens){
        boolean sort;
        SortingMethods chosenSort = new SortingMethods();
        int choice = 0;
        while (choice!=7){
            printSortingMenu();
            sort = sortOrder();
            choice = readSortingChoice();
            switch (choice) {
                case 1 -> System.out.println();
                case 2 -> printData(chosenSort.SortAge(citizens,sort));
                case 3 -> toSort(citizens);
                case 4 -> System.out.println("Exiting program...");
                case 7 -> System.out.println("going back");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

    }
    public void run(){
        MyProgramUtility start = new MyProgramUtility();
        int choice = 0;
        try {
            ArrayList<Citizen> citizens = (ArrayList<Citizen>)  readDataFromCSV();
            while (choice!=5){
                printMainMenu();
                choice = readChoice();
                switch (choice) {
                    case 1 ->  start.printData(returnDistrict(citizens));
                    case 2 -> start.genderDistribution(citizens);
                    case 3 -> toSort(citizens);
                    case 4 -> System.out.println("Exiting program...");
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
        BufferedReader reader= null;
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

    public void genderDistribution(List<Citizen> citizens){
        int Male = 0;
        int Female = 0;


        Predicate<Citizen> isMale = citizen -> citizen.gender == 'M';

        for (Citizen citizen: citizens){
            if (isMale.test(citizen)){
                Male++;
            }else Female++;
        };

        System.out.println("Male: " + Male);
        System.out.println("Female: " + Female);


    }

}
