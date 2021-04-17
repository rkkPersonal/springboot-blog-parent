package org.xr.happy.tread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

public class MyFutureTask<T> implements Runnable {

    LinkedBlockingQueue<Thread> blockingQueue = new LinkedBlockingQueue<>();

    private Callable<T> callable;

    T result;

    String status = "NEW";

    public MyFutureTask(Callable<T> callable) {
        this.callable = callable;
    }

    public T get() {
        boolean flag = false;
        while (true) {
            if (status.equals("END")) {

                return result;
            } else {

                if (false) {
                    blockingQueue.offer(Thread.currentThread());
                    LockSupport.park();
                    flag = true;
                }

            }
        }
    }

    @Override
    public void run() {
        try {
            result = (T) callable.call();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            status = "END";
        }

        Thread poll = blockingQueue.poll();

        LockSupport.unpark(poll);
    }
}
