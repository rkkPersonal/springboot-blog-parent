package org.xr.happy.tread;

public class ProduceAndConsumerTest {


    public static void main(String[] args) {

        org.xr.happy.tread.Storage storage = new Storage();

        org.xr.happy.tread.Produce produce1 = new org.xr.happy.tread.Produce(storage);
        org.xr.happy.tread.Produce produce2 = new org.xr.happy.tread.Produce(storage);
        org.xr.happy.tread.Produce produce3 = new Produce(storage);

        org.xr.happy.tread.Consumer consumer1 = new org.xr.happy.tread.Consumer(storage);
        org.xr.happy.tread.Consumer consumer2 = new org.xr.happy.tread.Consumer(storage);
        org.xr.happy.tread.Consumer consumer3 = new Consumer(storage);

        produce1.start();
        produce2.start();
        produce3.start();

        consumer1.start();
        consumer2.start();
        consumer3.start();

    }
}
