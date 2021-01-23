package org.xr.happy.tread.future;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {

            new Thread(() -> {

                try {
                    countDownLatch.countDown();
                    System.out.println("0000000000");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        countDownLatch.await();
        System.out.println("---------");

    }
}
