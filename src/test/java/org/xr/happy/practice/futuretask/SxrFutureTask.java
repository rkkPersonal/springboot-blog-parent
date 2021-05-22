package org.xr.happy.practice.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

public class SxrFutureTask implements Runnable {


    private Callable callable;

    private Object result;

    private String state = "new";

    private LinkedBlockingQueue<Thread> wait = new LinkedBlockingQueue<>();

    public SxrFutureTask(Callable callable) {
        this.callable = callable;
    }

    public Object get() {
        if (state.equals("end")) {
            return result;
        }

        while (state == "new") {
            wait.offer(Thread.currentThread());
            LockSupport.park(Thread.currentThread());
        }

        return result;
    }


    @Override
    public void run() {
        String string = Thread.currentThread().getState().toString();
        System.out.println("run:" + string);

        try {
            result = callable.call();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            state = "end";
        }
        Thread poll = wait.poll();

        while (null != poll) {
            LockSupport.unpark(poll);
            wait.poll();
        }
    }
}
