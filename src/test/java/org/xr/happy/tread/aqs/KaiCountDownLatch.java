package org.xr.happy.tread.aqs;


import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 还有点小问题
 */
public class KaiCountDownLatch {

    private CountDownSync countDownSync;

    public KaiCountDownLatch(int count) {
        countDownSync = new CountDownSync(count);
    }

    public void countDown() {
        countDownSync.releaseShare();

    }

    public void await() {
        countDownSync.acquireShare();

    }


    public static void main(String[] args) {

        KaiCountDownLatch countDownLatch = new KaiCountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {

                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName() + "排队中");
                try {
                    countDownLatch.await();
                    testPay();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }).start();
        }
    }

    private static void testPay() {

        try {
            Thread.sleep(new Random().nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "执行了");
    }

}
