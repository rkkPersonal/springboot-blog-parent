package org.xr.happy.tread;

public class ProduceAndConsumerTest {


    public static void main(String[] args) {

        Storage storage = new Storage();

        Produce produce1 = new Produce(storage);
        Produce produce2 = new Produce(storage);
        Produce produce3 = new Produce(storage);

        Consumer consumer1 = new Consumer(storage);
        Consumer consumer2 = new Consumer(storage);
        Consumer consumer3 = new Consumer(storage);

        produce1.start();
        produce2.start();
        produce3.start();

        consumer1.start();
        consumer2.start();
        consumer3.start();

    }
}
