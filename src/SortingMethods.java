package src;

import java.util.ArrayList;
import java.util.List;

public class SortingMethods {

    char [] order = {'<','>'};
    char ascend_descend;
    public List<Citizen> SortNames(Citizen citizen, boolean sortOrder){

        if (sortOrder) ascend_descend = order[1]; else ascend_descend = order[0];

    return (List<Citizen>) citizen;
    }

    public List<Citizen> SortAge(List<Citizen> citizen, boolean sortOrder){
        QuickSortNums proceed =  new QuickSortNums();
        proceed.quickSortNums(citizen, 0, citizen.size() - 1,"age",sortOrder);
        return citizen;
    }

}
