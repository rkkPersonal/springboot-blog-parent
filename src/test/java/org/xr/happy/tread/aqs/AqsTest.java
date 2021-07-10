package org.xr.happy.tread.aqs;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class AqsTest {

    volatile static int i = 0;


    public static void add() {
        i++;
    }

    @SneakyThrows
    public static void main(String[] args) {

        //testLock();
        //testSemaphore();
        testCountDownLatch();
    }

    private static void testCountDownLatch() {

        KaiCountDownLatch kaiCountDownLatch = new KaiCountDownLatch(2);


        for (int j = 0; j < 2; j++) {

            new Thread(() -> {
                kaiCountDownLatch.countDown();


                System.out.println(Thread.currentThread().getName() + "开始执行");
            }).start();
        }

        kaiCountDownLatch.await();
        System.out.println("执行结束");
    }

    private static void testSemaphore() {
        KaiSemaphore kaiSemaphore = new KaiSemaphore(3);

        for (int j = 0; j < 8; j++) {

            new Thread(() -> {

                kaiSemaphore.acquire();
                obtainToken();
                kaiSemaphore.release();

            }).start();
        }
    }

    private static void testLock() throws InterruptedException {
        KaiLock kaiLock = new KaiLock();

        CountDownLatch downLatch = new CountDownLatch(2);
        for (int j = 0; j < 2; j++) {

            new Thread(() -> {
                for (int k = 0; k < 10000; k++) {

                    kaiLock.lock();
                    add();
                    kaiLock.unlock();
                }

                downLatch.countDown();
            }).start();
        }

        downLatch.await();

        System.out.println(i);
    }

    @SneakyThrows
    private static void obtainToken() {

        System.out.println(Thread.currentThread() + "拿到手牌");
        Random random = new Random();
        int i = random.nextInt(3000);
        Thread.sleep(i);

        System.out.println(Thread.currentThread() + "释放令牌");
    }
}
