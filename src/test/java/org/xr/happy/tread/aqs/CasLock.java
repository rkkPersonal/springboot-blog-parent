package org.xr.happy.tread.aqs;

import lombok.SneakyThrows;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;

/**
 * 原子性
 */
public class CasLock {

    volatile int value;

    static Unsafe unsafe;

    private static long valueOffset;

    static {

        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
            valueOffset = unsafe.objectFieldOffset(CasLock.class.getDeclaredField("value"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void add() {
        int current;
        do {
            current = unsafe.getIntVolatile(this, valueOffset);
        } while (!unsafe.compareAndSwapInt(this, valueOffset, current, current + 1));
    }


    @SneakyThrows
    public static void main(String[] args) {

        int circle = 2;
        CountDownLatch countDownLatch = new CountDownLatch(circle);
        CasLock casLock = new CasLock();

        for (int i = 0; i < circle; i++) {
            new Thread(() -> {

                for (int j = 0; j < 10000; j++) {
                    casLock.add();
                }

                countDownLatch.countDown();

            }).start();

        }

        countDownLatch.await();
        System.out.println(casLock.value);
    }

}
