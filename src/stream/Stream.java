package stream;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Stream {
    public static void main(String[] args) {
        parallel();
    }

    public static void filter(){
        List<String> strings = Arrays.asList("abc","", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println(filtered);
    }

    public static void forEach(){
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
    }

    public static void map(){
        List<Integer> nums = Arrays.asList(3,2,2,3,7,3,5);
        List<Integer> result = nums.stream().map(i -> i*i).distinct().collect(Collectors.toList());
        System.out.println(result);
    }

    public static void parallel(){
        List<String> strings = Arrays.asList("abc","", "bc", "efg", "abcd","", "jkl");
        long count = strings.parallelStream().filter(string->string.isEmpty()).count();
        System.out.println(count);
    }
}
