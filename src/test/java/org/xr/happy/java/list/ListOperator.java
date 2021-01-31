package org.xr.happy.java.list;

import java.util.ArrayList;
import java.util.LinkedList;

public class ListOperator {

    public static void main(String[] args) {


        LinkedList<String> strings = new LinkedList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.add("5");

        System.out.println(strings);

        strings.addFirst("0");

        System.out.println(strings);

        strings.addLast("7");
        System.out.println(strings);

        String s = strings.removeFirst();
        System.out.println(strings);
        System.out.println(s);

        String s1 = strings.removeLast();
        System.out.println(strings);
        System.out.println(s1);


        while (strings.size()>0){

            boolean pop = strings.offer("af");
            System.out.println(strings);
        }

        System.out.println(strings);

    }

}
