package org.xr.happy.tread.future;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo {

    public static final List<Integer> list = new ArrayList<>();

    public static ForkJoinPool forkJoinPool = new ForkJoinPool(1, ForkJoinPool.defaultForkJoinWorkerThreadFactory, new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println(t.getName() + "不可以解决");
        }
    }, true);


    public static void main(String[] args) {

        Job job = new Job(10, list);
        ForkJoinTask submit = forkJoinPool.submit(job);

        try {
            System.out.println(submit.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


}

class Job extends RecursiveTask {

    private int start;
    private int end;
    private int blockSize;

    private List<Integer> urls;

    public Job(int start, int end, List<Integer> urls) {
        this.start = start;
        this.end = end;

    }

    public Job(int blockSize, List<Integer> urls) {
        this.blockSize = blockSize;
        this.urls = urls;
    }

    @SneakyThrows
    @Override
    protected Object compute() {


        if (urls.size() < 10) {

            Integer group = urls.size() / blockSize + 1;

            for (int i = 0; i < group.intValue(); i++) {

                end = start + group;
                Job job = new Job(start, end, urls);
                job.fork();

            }


        } else {

            Job job = new Job(0, 10, urls);

            ForkJoinTask fork = job.fork();
            Object o = fork.get();
        }

        return null;
    }
}
