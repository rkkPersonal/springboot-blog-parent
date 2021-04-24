package org.xr.happy.tread.task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutorDemo {

    private static ExecutorService queryThread = Executors.newFixedThreadPool(10);
    private static ExecutorService uploadThread = Executors.newFixedThreadPool(10);
    private static ExecutorService missLogThread = Executors.newFixedThreadPool(5);

    public static List<Integer> blockingDeque = new LinkedList<>();

    public static void main(String[] args) throws Exception {


        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        for (Integer integers : list) {

            queryThread.submit(() -> {
                Integer result = query(integers);
                try {

                    uploadThread.submit(() -> {

                            upload(result);

                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });




        }



    }


    private static Integer query(Integer integers) {
        System.out.println("query start " + integers);
        return integers;
    }

    private static void upload(Integer integers) {
        System.out.println("upload success :" + integers);
    }
}


