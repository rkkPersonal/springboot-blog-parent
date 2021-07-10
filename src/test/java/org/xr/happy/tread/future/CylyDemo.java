package org.xr.happy.tread.future;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CylyDemo {

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

        for (int i = 0; i < 100; i++) {

            new Thread(() -> {

                try {
                    System.out.println("开始了");
                    cyclicBarrier.await();

                    System.out.println("------我执行了------");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }).start();
        }

        System.out.println("===========--------------============");
    }
}
