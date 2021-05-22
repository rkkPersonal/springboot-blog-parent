package org.xr.happy.tread.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static ExecutorService executorService = Executors.newFixedThreadPool(20);

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(5);


        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("开始执行");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("执行完了");
                semaphore.release();

            }).start();


        }

    }
}
