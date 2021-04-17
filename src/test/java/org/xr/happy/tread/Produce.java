package org.xr.happy.tread;

public class Produce extends Thread {


    Storage storage;


    public Produce(Storage storage){
        this.storage=storage;
    }

    @Override
    public void run() {


        while (true) {


            synchronized (storage) {

                if (storage.linkedList.size()>storage.MAX_SIZE){

                    try {
                        System.out.println(Thread.currentThread().getName()+"仓库已经满了");
                        storage.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    storage.linkedList.add(new Object());
                    System.out.println(Thread.currentThread().getName()+"开始生产 东西");
                    storage.notifyAll();
                }

            }


        }


    }
}
