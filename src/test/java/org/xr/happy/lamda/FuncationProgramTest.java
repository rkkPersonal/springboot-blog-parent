package org.xr.happy.lamda;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FuncationProgramTest {


    public static void main(String[] args) {


        String s = testSupply(() -> {

            String name = "stenve 666";
            return name;
        });
        System.out.println(s);


        testConsumer("steven", s1 -> {
            String substring = s1.substring(0, 3);
            System.out.println(substring);
        });

        boolean sxrlove = testPredicate("sxrlove", (l) -> {
            return l.equals(1);
        });
        System.out.println(sxrlove);


        Integer integer = testFuncation("100", (s1 -> {

            return Integer.parseInt(s1) + 100;
        }));
        System.out.println(integer);
    }

    public static String testSupply(Supplier<String> supplier) {
        String s = supplier.get();
        return s;

    }

    public static void testConsumer(String name, Consumer<String> consumer) {

        consumer.accept(name);
    }

    public static boolean testPredicate(String name, Predicate<String> predicate) {
      return   predicate.test(name);
    }

    public static Integer testFuncation(String name, Function<String,Integer> function) {

        Integer apply = function.apply(name);

        return apply;
    }
}





