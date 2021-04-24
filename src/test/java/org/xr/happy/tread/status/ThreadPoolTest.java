package org.xr.happy.tread.status;

import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) {


        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "test-" + r.hashCode());
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                String name = Thread.currentThread().getName();
                System.out.println(name + "is rejected");
            }
        });
        int queueSize = poolExecutor.getQueue().size();
        int corePoolSize = poolExecutor.getCorePoolSize();
        int poolSize = poolExecutor.getPoolSize();

        int activeCount = poolExecutor.getActiveCount();
        long taskCount = poolExecutor.getTaskCount();
        System.out.println("执行之前 corePoolSize2= "+corePoolSize);
        System.out.println("执行之前 poolSize2=     "+poolSize);
        System.out.println("执行之前 activeCount2=  "+activeCount);
        System.out.println("执行之前 taskCount2=    "+taskCount);
        System.out.println("执行之前 queueSize=     "+queueSize);


        for (int i = 0; i < 11; i++) {

            poolExecutor.submit(new Runnable() {
                @Override
                public void run() {

                    System.out.println(Thread.currentThread().getName() + "开始执行");
                }
            });
        }

        int corePoolSize2 = poolExecutor.getCorePoolSize();
        int poolSize2 = poolExecutor.getPoolSize();

        int activeCount2 = poolExecutor.getActiveCount();
        long taskCount2 = poolExecutor.getTaskCount();

        int size = poolExecutor.getQueue().size();
        System.out.println("执行之后 corePoolSize2= "+corePoolSize2);
        System.out.println("执行之后 poolSize2=     "+poolSize2);
        System.out.println("执行之后 activeCount2=  "+activeCount2);
        System.out.println("执行之后 taskCount2=    "+taskCount2);
        System.out.println("执行之后 queueSize=       "+size);
    }
}
