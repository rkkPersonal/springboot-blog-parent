package org.xr.happy.practice.futuretask;

import lombok.SneakyThrows;

import java.util.concurrent.*;

public class FutureTaskTest {

    static ThreadPoolExecutor threadPoolExecutor;

    static {
        threadPoolExecutor = new ThreadPoolExecutor(5, 20, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "sxr-task-" + r.hashCode());
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                Future r1 = (Future) r;
                r1.cancel(true);
            }
        });


    }


    @SneakyThrows
    public static void main(String[] args) {

        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {

                String hello = "Hello World !";
                return hello;
            }
        };
        Future submit = threadPoolExecutor.submit(callable);

        System.out.println(submit.get());

        SxrFutureTask sxrFutureTask = new SxrFutureTask(callable);
        new Thread(sxrFutureTask).start();

        System.out.println(sxrFutureTask.get());

        FutureTask futureTask = new FutureTask<>(callable);

        Future<?> submit1 = threadPoolExecutor.submit(futureTask);
        System.out.println(futureTask.get());

        threadPoolExecutor.shutdown();


    }
}