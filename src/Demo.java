package src;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Demo {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(4,5,6,7,1,8,2,3);

//        Stream<Integer> data = nums.stream().map(n-> (n*2) -5);
//        long count = data.count(); // the stream has been consumed
//
//        System.out.println(count);
//        data.forEach(System.out::println);

//        Stream<Integer> sorted_data = data.sorted();
//        sorted_data.forEach(n -> System.out.println(n));

//        Stream<Integer> mapped_data = data.map(n-> n*2);
//        mapped_data.forEach(System.out::println);

        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer n) {
                if(n%2==1){
                    return true;
                }else return false;
            }
        };

        Function<Integer,Integer> function = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer*2;
            }
        };

//        nums.stream().filter(predicate).sorted().map(n -> n).forEach(System.out::println);

//        nums.stream().filter(n -> n%2==1).sorted().map(n -> n).forEach(System.out::println);


        int result  = nums.stream().filter(predicate).sorted().map(function).reduce(0,(c,e)->c+e);

        System.out.println(result);
    }
}
