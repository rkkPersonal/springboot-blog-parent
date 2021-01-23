package org.xr.happy.tread.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutorConsumer {

    private static ExecutorService uploadThread = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {

        while (ThreadExecutorDemo.blockingDeque.size() > 0) {


            uploadThread.submit(() -> {
                upload(ThreadExecutorDemo.blockingDeque.remove(0));
            });

        }

        uploadThread.shutdown();

    }

    private static void upload(Integer integers) {
        System.out.println("upload success :" + integers);
    }
}
