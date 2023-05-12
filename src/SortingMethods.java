package src;

import java.util.*;

public class SortingMethods {


    public List<Citizen> sortNames(List<Citizen> citizen, boolean sortOrder){
        QuickSortStrings proceed = new QuickSortStrings();
        proceed.quickSortStrings(citizen,0,citizen.size() -2,"fullname",sortOrder);
        return citizen;
    }
    public List<Citizen> sortEmail(List<Citizen> citizen, boolean sortOrder){
        QuickSortStrings proceed = new QuickSortStrings();
        proceed.quickSortStrings(citizen,0,citizen.size() -2,"email",sortOrder);
        return citizen;
    }

    public List<Citizen> sortAddress(List<Citizen> citizen, boolean sortOrder){
        QuickSortStrings proceed = new QuickSortStrings();
        proceed.quickSortStrings(citizen,0,citizen.size() -2,"address",sortOrder);
        return citizen;
    }


    public List<Citizen> SortAge(List<Citizen> citizen, boolean sortOrder){
        QuickSortNums proceed =  new QuickSortNums();
        proceed.quickSortNums(citizen, 0, citizen.size() - 1,"age",sortOrder);
        return citizen;
    }

    public String[] getTopRecurringNames(List<Citizen> citizens, boolean first_last, int numTopNames) {
        int main_focus_index = first_last ? 0 : 1;

        Set<String> topRecurringNames = new HashSet<>();
        Map<String, Integer> nameCountMap = new HashMap<>();

        // Count the occurrences of each name and store them in a HashMap
        for (Citizen citizen : citizens) {
            String[] name = citizen.getFullname().split("\\s+");
            if (name.length <= main_focus_index) {
                continue; // skip this Citizen object
            }
            int count = nameCountMap.getOrDefault(name[main_focus_index], 0);
            nameCountMap.put(name[main_focus_index], count + 1);
        }

        // Find the top recurring names
        TreeMap<Integer, List<String>> sortedNames = new TreeMap<>(Collections.reverseOrder());
        for (String name : nameCountMap.keySet()) {
            int count = nameCountMap.get(name);
            List<String> names = sortedNames.getOrDefault(count, new ArrayList<>());
            names.add(name);
            sortedNames.put(count, names);
        }

        // Add the unique top recurring names to the output set
        for (int count : sortedNames.keySet()) {
            List<String> names = sortedNames.get(count);
            for (String name : names) {
                if (topRecurringNames.size() >= numTopNames) {
                    return topRecurringNames.toArray(new String[0]);
                }
                topRecurringNames.add(name);
            }
        }

        return topRecurringNames.toArray(new String[0]);
    }

    public List<Citizen> getTopRecurringNamesOOP(List<Citizen> citizens, boolean first_last, int numTopNames) {
        int main_focus_index = first_last ? 0 : 1;

        List<Citizen> topRecurringCitizens = new ArrayList<>();
        Map<String, Integer> nameCountMap = new HashMap<>();

        // Count the occurrences of each name and store them in a HashMap
        for (Citizen citizen : citizens) {
            String[] name = citizen.getFullname().split("\\s+");
            if (name.length <= main_focus_index) {
                continue; // skip this Citizen object
            }
            int count = nameCountMap.getOrDefault(name[main_focus_index], 0);
            nameCountMap.put(name[main_focus_index], count + 1);
        }

        // Find the top recurring names
        TreeMap<Integer, List<String>> sortedNames = new TreeMap<>(Collections.reverseOrder());
        for (String name : nameCountMap.keySet()) {
            int count = nameCountMap.get(name);
            List<String> names = sortedNames.getOrDefault(count, new ArrayList<>());
            names.add(name);
            sortedNames.put(count, names);
        }

        // Find the Citizen objects that have the top recurring names
        for (int count : sortedNames.keySet()) {
            List<String> names = sortedNames.get(count);
            for (String name : names) {
                if (topRecurringCitizens.size() >= numTopNames) {
                    return topRecurringCitizens;
                }
                for (Citizen citizen : citizens) {
                    String[] fullName = citizen.getFullname().split("\\s+");
                    if (fullName.length > main_focus_index && fullName[main_focus_index].equals(name)) {
                        topRecurringCitizens.add(citizen);
                    }
                }
            }
        }

        return topRecurringCitizens;
    }
}
