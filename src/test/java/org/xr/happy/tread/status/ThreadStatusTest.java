package org.xr.happy.tread.status;

public class ThreadStatusTest {
    public static Thread thread1;
    public static Demo2 obj;

    public static void main(String[] args) throws Exception {
        // 第一种状态切换 - 新建 -> 运行 -> 终止
        System.out.println("#######第一种状态切换  - 新建 -> 运行 -> 终止################################");
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread1当前状态：" + Thread.currentThread().getState().toString());// RUNNABLE
                System.out.println("thread1 执行了");
            }
        });
        System.out.println("没调用start方法，thread1当前状态：" + thread1.getState().toString());// new
        thread1.start();
        Thread.sleep(2000L); // 等待thread1执行结束，再看状态
        System.out.println("等待两秒，再看thread1当前状态：" + thread1.getState().toString()); //terminal
        // thread1.start(); TODO 注意，线程终止之后，再进行调用，会抛出IllegalThreadStateException异常

        System.out.println();
        System.out.println("############第二种：新建 -> 运行 -> 等待 -> 运行 -> 终止(sleep方式)###########################");
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {// 将线程2移动到等待状态，1500后自动唤醒
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2当前状态：" + Thread.currentThread().getState().toString()); //runnable
                System.out.println("thread2 执行了");
            }
        });
        System.out.println("没调用start方法，thread2当前状态：" + thread2.getState().toString());// new
        thread2.start();
        System.out.println("调用start方法，thread2当前状态：" + thread2.getState().toString()); //runnable
        Thread.sleep(200L); // 等待200毫秒，再看状态
        System.out.println("等待200毫秒，再看thread2当前状态：" + thread2.getState().toString());//TIMED_WAITING
        Thread.sleep(3000L); // 再等待3秒，让thread2执行完毕，再看状态
        System.out.println("等待3秒，再看thread2当前状态：" + thread2.getState().toString());// terminal

        System.out.println();
        System.out.println("############第三种：新建 -> 运行 -> 阻塞 -> 运行 -> 终止###########################");
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (Demo2.class) {
                    System.out.println("thread3当前状态：" + Thread.currentThread().getState().toString());
                    System.out.println("thread3 执行了");
                }
            }
        });
        synchronized (Demo2.class) {
            System.out.println("没调用start方法，thread3当前状态：" + thread3.getState().toString());
            thread3.start();
            System.out.println("调用start方法，thread3当前状态：" + thread3.getState().toString());
            Thread.sleep(200L); // 等待200毫秒，再看状态
            System.out.println("等待200毫秒，再看thread3当前状态：" + thread3.getState().toString());
        }
        Thread.sleep(3000L); // 再等待3秒，让thread3执行完毕，再看状态
        System.out.println("等待3秒，让thread3抢到锁，再看thread3当前状态：" + thread3.getState().toString());

    }
}

class Demo2 {

}
