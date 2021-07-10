package org.xr.happy.practice.futuretask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class Request {

    static List<String> urls = new ArrayList<>();

    static {
        Collections.addAll(urls,
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com"
        );

    }

    public static void doRequest(List<String> urls) {


    }

    public static void main(String[] args) {
//int parallelism,
//                        ForkJoinWorkerThreadFactory factory,
//                        UncaughtExceptionHandler handler,
//                        boolean asyncMode
        ForkJoinPool forkJoinPool = new ForkJoinPool(10, ForkJoinPool.defaultForkJoinWorkerThreadFactory, new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("un");
            }
        }, false);


        int start = 1;
        int end = 1;

        Job job = new Job(urls, start, urls.size());

        ForkJoinTask submit = forkJoinPool.submit(job);

    }
}
