package org.xr.happy.tread;

public class   Consumer extends Thread {


    org.xr.happy.tread.Storage storage;

    public Consumer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {


        while (true) {


            synchronized (storage) {

                if (storage.linkedList.size() <= 0) {


                    System.out.println(Thread.currentThread().getName() + "仓库已经空了");
                    try {
                        storage.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {
                    storage.linkedList.remove();
                    storage.notifyAll();
                    System.out.println(Thread.currentThread().getName() + "开始消费 东西");

                }

            }


        }


    }
}

