package org.xr.happy.tread.aqs;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class KaiLock implements Lock {

    private Sync sync;

    public KaiLock() {
        sync = new Sync();
    }

    @Override
    public void lock() {
        sync.acquire();
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public void unlock() {
        sync.release();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }


    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }


}

class CountDownSync extends AbstractQueueSynchronizer {

    public CountDownSync() {
    }

    public CountDownSync(int state) {
        this.state.set(state);
    }

    @Override
    protected int tryAcquireShare() {
        return this.state.get() == 0 ? 1 : -1;
    }

    @Override
    protected boolean tryReleaseShare() {
        return this.state.decrementAndGet() == 0;
    }
}

class Sync extends AbstractQueueSynchronizer {

    public Sync() {
    }

    public Sync(int state) {
        this.state.set(state);
    }

    /*
        独享锁
         */
    @Override
    protected boolean tryAcquire() {
        return owner.compareAndSet(null, Thread.currentThread());
    }

    /*
    独享锁释放
     */
    @Override
    protected boolean tryRelease() {
        return owner.compareAndSet(Thread.currentThread(), null);
    }

    /*
    共享锁
     */
    @Override
    protected int tryAcquireShare() {

        for (; ; ) {
            int count = state.get();
            int value = count - 1;

            if (count <= 0 || value < 0) {
                return -1;
            }

            if (state.compareAndSet(count, value)) {
                return 1;
            }
        }
    }

    /*
    共享锁释放
     */
    @Override
    protected boolean tryReleaseShare() {
        return state.incrementAndGet() >= 0;
    }

    public int getState() {
        return this.state.get();
    }

}


abstract class AbstractQueueSynchronizer {

    volatile AtomicReference<Thread> owner = new AtomicReference<>();

    volatile LinkedBlockingDeque<Thread> waiter = new LinkedBlockingDeque<>();

    volatile AtomicInteger state = new AtomicInteger(0);

    protected void acquireShare() {

        boolean flag = true;
        while (tryAcquireShare() < 0) {
            if (flag) {
                waiter.offer(Thread.currentThread());
                flag = false;
            } else {
                LockSupport.park();
            }
        }

        waiter.remove(Thread.currentThread());

    }

    protected int tryAcquireShare() {
        throw new UnsupportedOperationException();
    }


    protected void releaseShare() {

        if (tryReleaseShare()) {
            Iterator<Thread> iterator = waiter.iterator();

            while (iterator.hasNext()) {

                Thread next = iterator.next();
                LockSupport.unpark(next);
            }
        }

    }

    protected boolean tryReleaseShare() {
        throw new UnsupportedOperationException();
    }


    // standalone
    protected void acquire() {

        boolean flag = true;
        while (!tryAcquire()) {
            if (flag) {
                waiter.offer(Thread.currentThread());
                flag = false;
            } else {
                LockSupport.park();
            }
        }

        waiter.remove(Thread.currentThread());

    }

    protected boolean tryAcquire() {
        throw new UnsupportedOperationException();
    }


    protected void release() {

        if (tryRelease()) {
            Iterator<Thread> iterator = waiter.iterator();

            while (iterator.hasNext()) {

                Thread next = iterator.next();
                LockSupport.unpark(next);
            }
        }

    }

    protected boolean tryRelease() {
        throw new UnsupportedOperationException();
    }

}