package src;

import java.util.List;

class QuickSortStrings{

    public void quickSortStrings(List<Citizen> strings, int lowIndex, int highIndex, String val, boolean ascendingOrder) {
        if (lowIndex >= highIndex) {
            return;
        }

        int pivotIndex = partition(strings, lowIndex, highIndex, val, ascendingOrder);
        quickSortStrings(strings, lowIndex, pivotIndex - 1, val, ascendingOrder);
        quickSortStrings(strings, pivotIndex + 1, highIndex, val, ascendingOrder);
    }

    private int partition(List<Citizen> strings, int lowIndex, int highIndex, String val, boolean ascendingOrder) {
        String pivot = strings.get(highIndex).getValue(val).toString();
        int leftPointer = lowIndex;
        int rightPointer = highIndex - 1;

        while (leftPointer <= rightPointer) {
            while (compare(strings.get(leftPointer).getValue(val).toString(), pivot, ascendingOrder) < 0 && leftPointer <= rightPointer) {
                leftPointer++;
            }
            while (compare(strings.get(rightPointer).getValue(val).toString(), pivot, ascendingOrder) > 0 && leftPointer <= rightPointer) {
                rightPointer--;
            }
            if (leftPointer <= rightPointer) {
                swap(strings, leftPointer, rightPointer);
                leftPointer++;
                rightPointer--;
            }
        }

        swap(strings, leftPointer, highIndex);
        return leftPointer;
    }

    private void swap(List<Citizen> strings, int index1, int index2) {
        Citizen temp = strings.get(index1);
        strings.set(index1, strings.get(index2));
        strings.set(index2, temp);
    }

    private int compare(String s1, String s2, boolean ascendingOrder) {
        int result = s1.compareTo(s2);
        if (!ascendingOrder) {
            result *= -1;
        }
        return result;
    }


}
class QuickSortNums {
    public void quickSortNums(List<Citizen> citizens, int lowIndex, int highIndex, String val, boolean ascending) {
        if (lowIndex >= highIndex) {
            return;
        }
        boolean condition = val.equals("age") || val.equals("district");
        ;

        int pivot = 0;
        if (condition) {
            pivot = (Integer) citizens.get(highIndex).getValue(val);
        } else {
            pivot = Integer.parseInt((String) citizens.get(highIndex).getValue(val)); // parse the string to integer
        }
        int leftPointer = lowIndex;
        int rightPointer = highIndex;

        while (leftPointer < rightPointer) {
            if (ascending) {
                if (condition) {
                    while ((Integer) citizens.get(leftPointer).getValue(val) < pivot && leftPointer < rightPointer) {
                        leftPointer++;
                    }
                    while ((Integer) citizens.get(rightPointer).getValue(val) >= pivot && leftPointer < rightPointer) {
                        rightPointer--;
                    }
                } else {
                    while (Integer.parseInt((String) citizens.get(leftPointer).getValue(val)) < pivot && leftPointer < rightPointer) { // parse the string to integer for comparison
                        leftPointer++;
                    }
                    while (Integer.parseInt((String) citizens.get(rightPointer).getValue(val)) >= pivot && leftPointer < rightPointer) { // parse the string to integer for comparison
                        rightPointer--;
                    }
                }
            } else {
                if (condition) {
                    while ((Integer) citizens.get(leftPointer).getValue(val) > pivot && leftPointer < rightPointer) {
                        leftPointer++;
                    }
                    while ((Integer) citizens.get(rightPointer).getValue(val) <= pivot && leftPointer < rightPointer) {
                        rightPointer--;
                    }
                } else {
                    while (Integer.parseInt((String) citizens.get(leftPointer).getValue(val)) > pivot && leftPointer < rightPointer) { // parse the string to integer for comparison
                        leftPointer++;
                    }
                    while (Integer.parseInt((String) citizens.get(rightPointer).getValue(val)) <= pivot && leftPointer < rightPointer) { // parse the string to integer for comparison
                        rightPointer--;
                    }
                }
            }
            swap(citizens, leftPointer, rightPointer);
        }
        swap(citizens, leftPointer, highIndex);
        quickSortNums(citizens, lowIndex, leftPointer - 1, val, ascending);
        quickSortNums(citizens, leftPointer + 1, highIndex, val, ascending);
    }
    private void swap(List<Citizen> citizens, int index1, int index2) {
        Citizen temp = citizens.get(index1);
        citizens.set(index1, citizens.get(index2));
        citizens.set(index2, temp);
    }

}


