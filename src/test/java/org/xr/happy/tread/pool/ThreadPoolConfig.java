package org.xr.happy.tread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ThreadPoolConfig {


    public static final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10)
            , new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "test-pool-" + r.hashCode());
        }
    }, new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            Future r1 = (Future) r;
            r1.cancel(true);
        }
    });


    public static void main(String[] args) {


        String main = Thread.currentThread().getName();
        System.out.println(main + "process");
        List<Callable> callableList = new ArrayList<>();
        try {
            for (int i = 0; i < 10; i++) {

                Callable callable = new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return "hello";
                    }
                };
                callableList.add(callable);
            }


        } catch (Exception e) {
            System.out.println(main + "faild");
        }
        poolExecutor.execute(new ProcessFutureTask(callableList));
    }

    static class ProcessFutureTask implements Runnable {

        public List<Callable> list;

        public ProcessFutureTask(List<Callable> list) {

            this.list = list;
        }


        @Override
        public void run() {

            List<Future> futureList = new ArrayList<Future>();
            for (Callable future : list) {
                Future submit = poolExecutor.submit(future);
                futureList.add(submit);
            }

            try {

                List<Future> collect = futureList.stream().filter(s -> !s.isCancelled()).collect(Collectors.toList());

                List<Object> resultList = new ArrayList<>();
                while (resultList.size() <= collect.size()) {
                    for (Future future : futureList) {

                        System.out.println(Thread.currentThread().getName() + "inprocess");
                        if (future.isDone() && future.isCancelled()) {
                            Object result = future.get();
                            System.out.println(Thread.currentThread().getName() + "finished");
                            resultList.add(result);
                            futureList.remove(future);
                        } else {
                            futureList.remove(future);
                            Object o = new Object();
                            resultList.add(o);
                            System.out.println(Thread.currentThread().getName() + "faied");
                        }

                    }
                }

            } catch (Exception e) {
                System.out.println("error" + e.getMessage());
            }
        }
    }
}
