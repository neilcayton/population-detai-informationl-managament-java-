package src;

import java.util.*;

public class SortingMethods {


    public List<Citizen> SortNames(List<Citizen> citizen, boolean sortOrder){
        QuickSortStrings proceed = new QuickSortStrings();
        proceed.quickSortStrings(citizen,0,citizen.size() -2,"fullname",sortOrder);
        return citizen;
    }

    public List<Citizen> SortAge(List<Citizen> citizen, boolean sortOrder){
        QuickSortNums proceed =  new QuickSortNums();
        proceed.quickSortNums(citizen, 0, citizen.size() - 1,"age",sortOrder);
        return citizen;
    }

    public List<Citizen> getTopRecurringNames(List<Citizen> citizens, boolean first_last, int numTopNames) {
        int main_focus_index = first_last ? 0 : 1;

        List<Citizen> topRecurringCitizens = new ArrayList<>();
        Map<String, Integer> nameCountMap = new HashMap<>();

        // Count the occurrences of each name and store them in a HashMap
        for (Citizen citizen : citizens) {
            String[] name = citizen.getFullname().split(",");
            int count = nameCountMap.getOrDefault(name[main_focus_index], 0);
            nameCountMap.put(name[main_focus_index], count + 1);
        }

        // Find the top recurring names
        PriorityQueue<String> topNames = new PriorityQueue<>((name1, name2) -> {
            int count1 = nameCountMap.get(name1);
            int count2 = nameCountMap.get(name2);
            return count2 - count1;
        });
        for (String name : nameCountMap.keySet()) {
            if (topNames.size() < numTopNames || nameCountMap.get(name) >= nameCountMap.get(topNames.peek())) {
                topNames.offer(name);
                if (topNames.size() > numTopNames) {
                    topNames.poll();
                }
            }
        }

        // Find the Citizen objects that have the top recurring names
        for (Citizen citizen : citizens) {
            String[] name = citizen.getFullname().split(",");
            if (topNames.contains(name[main_focus_index]) && !topRecurringCitizens.contains(citizen)) {
                topRecurringCitizens.add(citizen);
            }
        }

        return topRecurringCitizens;
    }
}
