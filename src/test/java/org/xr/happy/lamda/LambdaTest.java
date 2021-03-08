package org.xr.happy.lamda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static sun.misc.Version.println;

public class LambdaTest {

    public static void main(String[] args) {

  /*      new LambadaObj(new MyInterface() {
            @Override
            public void methodA() {
                System.out.println("hello");
            }
        });

        new LambadaObj(() -> {
            System.out.println("lambada is test!");
        });

        new LambadaObj(() -> System.out.println("no hi"));*/

/*        new LambadaObj((a) -> {
            int b = 10;
            System.out.println(a + b);
        });

        new LambadaObj(a -> System.out.println(200));
        new LambadaObj(a->{
            int b = 20;
            System.out.println(a+b);
        });

        new LambadaObj(System.out::println);*/

   /*     new LambadaObj(new MyInterface() {
            @Override
            public int methodC(int a, int b) {

                return a+b;
            }
        });

        new LambadaObj((a,b)->{

            return a+b+10;
        });

        new LambadaObj( (a,b)->a+b+20);*/


        new LambadaObj(() -> {
            System.out.println();
        });


        ArrayList<Integer> list = new ArrayList<>();

        Collections.addAll(list, 5, 4, 3, 6, 7, 8, 1, 2);

        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {

                return 0;
            }
        });

    }

    private Comparator<String> getComparator() {

        return (s, b) -> s.length() > b.length() ? 0 : 1;

    }
}

@FunctionalInterface
interface FunctionallyLambada {

    void show();

    default void show2() {

    }

    static void show3() {

    }
}
