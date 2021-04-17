package org.xr.happy.tread.queue;

import org.assertj.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.*;

public class ThreadPoolAndCountDownLatch {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolAndCountDownLatch.class);

    volatile static int s = 0;

    /**
     * 实现一种猫狗队列的结构，要求如下：
     * <p>
     * 1. 用户可以调用 add 方法将 cat 或者 dog 放入队列中
     * <p>
     * 2. 用户可以调用 pollAll 方法将队列中的 cat 和 dog 按照进队列的先后顺序依次弹出
     * <p>
     * 3. 用户可以调用 pollDog 方法将队列中的 dog 按照进队列的先后顺序依次弹出
     * <p>
     * 4. 用户可以调用 pollCat 方法将队列中的 cat 按照进队列的先后顺序依次弹出
     * <p>
     * 5. 用户可以调用 isEmpty 方法检查队列中是否还有 dog 或 cat
     * <p>
     * 6. 用户可以调用 isDogEmpty 方法检查队列中是否还有 dog
     * <p>
     * 7. 用户可以调用 isCatEmpty 方法检查队列中是否还有 cat
     * <p>
     * <p>
     * <p>
     * 提示：1.猫狗队列是一个类。并不指定是某个队列
     * <p>
     * 2.所有方法均为合法操作.
     */

    public static void main(String[] args) throws Exception {

        //ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();


        final CountDownLatch countDownLatch = new CountDownLatch(10);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 30; i++) {


            executorService.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {

                    //    countDownLatch.countDown();
                    System.out.println("=--=" + s++);
                    cyclicBarrier.await();
                    System.out.println("一起跑路");
                    return true;

                }
            });

        }


        // countDownLatch.await();


    }

    private static void getScheduleThreadPoolExecutor() throws Exception {

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);

        scheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {

                System.out.println(Thread.currentThread().getName() + ",执行了");
            }
        }, 1000, TimeUnit.MILLISECONDS);

//        testSubmit(scheduledThreadPoolExecutor);


        Thread.sleep(10000);

    }

    private static void getFixScheduleThreadPoolExecutor() throws Exception {

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);

/*        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {

            System.out.println("开始执行:" + DateUtil.formatAsDatetime(new Date()));

        }, 2, 3, TimeUnit.SECONDS);*/


        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            System.out.println("WithFix执行:" + DateUtil.formatAsDatetime(new Date()));


        }, 2, 3, TimeUnit.SECONDS);

    }

    private static ThreadPoolExecutor getThreadPoolExecutor() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(3), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                logger.info(Thread.currentThread().getName() + "--被拒绝");
            }
        });
        testSubmit(threadPoolExecutor);

        System.out.println("执行中：" + threadPoolExecutor.getPoolSize() + "," + threadPoolExecutor.getQueue().size() + "," + threadPoolExecutor.getActiveCount());
        Thread.sleep(10000);
        System.out.println("执行状态：" + threadPoolExecutor.getPoolSize() + "," + threadPoolExecutor.getQueue().size() + "," + threadPoolExecutor.getActiveCount());
        threadPoolExecutor.shutdown();
        return threadPoolExecutor;
    }


    public static void testSubmit(ThreadPoolExecutor threadPoolExecutor) {


        for (int i = 0; i < 15; i++) {
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    logger.info(Thread.currentThread().getName() + "--开始执行");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        logger.info(Thread.currentThread().getName() + "--打断");
                    }
                    logger.info(Thread.currentThread().getName() + "--结束");
                }
            });

        }


    }
}

class Driver {

    public static void main(String args[]) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(3);

        for (int i = 0; i < 3; ++i) // create and start threads
            new Thread(new Worker(startSignal, doneSignal)).start();

        doSomethingElse();            // don't let run yet
        startSignal.countDown();      // let all threads proceed
        doSomethingElse();
        doneSignal.await();           // wait for all to finish
    }

    private static void doSomethingElse() {
        System.out.println("doSomethingElse()");
    }
}

class Worker implements Runnable {
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;

    Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    public void run() {
        try {
            startSignal.await();
            doWork();
            doneSignal.countDown();
        } catch (InterruptedException ex) {
        } // return;
    }

    void doWork() {

        System.out.println("doWork()");

    }
}


class Driver2 {
    public static void main(String args[]) throws InterruptedException {
        int N = 5;
        CountDownLatch doneSignal = new CountDownLatch(N);
        Executor e = Executors.newFixedThreadPool(10);

        for (int i = 0; i < N; ++i) // create and start threads
            e.execute(new WorkerRunnable(doneSignal, i));

        doneSignal.await();
        System.out.println("开始执行");// wait for all to finish
    }
}

class WorkerRunnable implements Runnable {
    private final CountDownLatch doneSignal;
    private final int i;

    WorkerRunnable(CountDownLatch doneSignal, int i) {
        this.doneSignal = doneSignal;
        this.i = i;
    }

    public void run() {
        try {
            doWork(i);
            doneSignal.countDown();
        } catch (Exception ex) {
        } // return;
    }

    void doWork(int i) {

        System.out.println("doWork()" + i);
    }
}