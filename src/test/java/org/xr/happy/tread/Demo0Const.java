package org.xr.happy.tread;

import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * 描述：有4个线程和1个公共的字符数组。线程1的功能就是向数组输出A，线程2的功能就是向字符输出B，线程3的功能就是向数组输出C，线程4的功能就是向数组输出D。要求按顺序向数组赋值ABCDABCDABCD，ABCD的个数由线程函数1的参数指定。
 * Ps:如果觉得过于简单，可以提升难度 获得额外的5分 – 即 输出结果为 用户输入 3 –输出 ABCDABCDABCD (同学们可以自行考验自己的成果.)
 *
 * 提示：巧妙应用线程的阻塞方案。
 *
 *
 *
 * 示例： 用户输入10
 *
 * 结果： ABCDABCDAB
 */
public class Demo0Const {


    @SneakyThrows
    public static void main(String[] args) {

        System.out.println("请输入数量：");
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        String[] arrays = new String[count];

        methodTwo(arrays, count);

    }

    /**
     * 实现方法1
     *
     * @param arrays
     * @param count
     * @throws InterruptedException
     */
    private static void methodOne(String[] arrays, int count) throws InterruptedException {

        // 11 /4= 2; 11 %4=3
        int circle = count / 4 + 1;

        AtomicInteger numStr = new AtomicInteger(0);
        for (int i = 0; i < circle; i++) {
            threadGroups(arrays, count, numStr);
        }

        System.out.println(Arrays.toString(arrays));
    }

    private static void threadGroups(String[] arrays, int count, AtomicInteger numStr) throws InterruptedException {
        Thread thread0 = null;
        if (numStr.get() < count) {
            thread0 = new Thread(() -> {
                arrays[numStr.get()] = "A";
            });
            thread0.start();
            thread0.join();
            numStr.incrementAndGet();
        }


        Thread thread1 = null;
        if (numStr.get() < count) {
            thread1 = new Thread(() -> {
                arrays[numStr.get()] = "B";
            });
            thread1.start();
            thread1.join();
            numStr.incrementAndGet();
        }


        Thread thread2 = null;
        if (numStr.get() < count) {
            thread2 = new Thread(() -> {
                arrays[numStr.get()] = "C";
            });
            thread2.start();
            thread2.join();
            numStr.incrementAndGet();
        }
        Thread thread3 = null;
        if (numStr.get() < count) {
            thread3 = new Thread(() -> {
                arrays[numStr.get()] = "D";
            });
            thread3.start();
            thread3.join();
            numStr.incrementAndGet();
        }
    }

    private static void methodTwo(String[] arrays, int count) throws InterruptedException {
        // 设置shu


        AtomicInteger numStr = new AtomicInteger(65);

        for (int i = 0; i < count; i++) {
            int index = i;
            Thread thread = new Thread(() -> {
                arrays[index] = String.valueOf((char) numStr.get());
            });

            thread.start();
            thread.join();
            numStr.incrementAndGet();

            if (numStr.get() > 68) {
                numStr.set(65);
            }

        }

        System.out.println(Arrays.toString(arrays));
    }
}
