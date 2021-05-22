package org.xr.happy.tread.future;

import java.util.concurrent.*;

public class StevenFutureTask {


    public static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws Exception {


        org.xr.happy.tread.future.MyFutureTask futureTask = new MyFutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hfdsafas";
            }
        });

        new Thread(futureTask).start();
        Object o = futureTask.get();
        System.out.println(o);
    }
}
