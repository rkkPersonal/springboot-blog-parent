package org.xr.happy.tread.future;

import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.LockSupport;

public class MyFutureTask implements Runnable {


    private Callable callable;

    T result;

    String status = "NEW";

    public MyFutureTask(Callable callable) {
        this.callable = callable;
    }

    public T get() {
        while (true) {
            if (status.equals("END")) {

                return result;
            } else {

                LockSupport.park();
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
    }
}
