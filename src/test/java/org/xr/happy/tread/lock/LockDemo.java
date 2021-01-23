package org.xr.happy.tread.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    public static void main(String[] args) throws Exception{

        StevenBlockQueue stevenBlockQueue = new StevenBlockQueue(5);

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                stevenBlockQueue.put("X" + i);
            }

        }).start();


        Thread.sleep(1000);
        System.out.println("===================开始取元素===================");
        for (int i = 0; i < 10; i++) {

            stevenBlockQueue.take();
            Thread.sleep(1000);
        }
    }


}

class StevenBlockQueue {


    private List<Object> list = new ArrayList<>();

    private ReentrantLock lock = new ReentrantLock();

    private Condition putCondition = lock.newCondition();

    private Condition takeCondition = lock.newCondition();

    private int length;

    public StevenBlockQueue(int length) {
        this.length = length;
    }


    public void put(Object obj) {
        try {

            lock.lock();
            if (list.size() > length) {

                putCondition.await();

            } else {
                System.out.println("开始放入队列:" + obj);
                list.add(obj);
                takeCondition.signal();
                return;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public Object take() {
        try {
            lock.lock();
            if (list.size() > 0) {

                Object obj = list.remove(0);
                System.out.println("取得元素从队列中：" + obj);
                putCondition.signal();
                return obj;

            }

            takeCondition.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;

    }
}
