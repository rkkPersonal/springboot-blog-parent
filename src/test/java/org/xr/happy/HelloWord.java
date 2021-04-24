package org.xr.happy;

import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class HelloWord {

    @SneakyThrows
    public static void main(String[] args) {

        MyQueue order1 = new MyQueue("order1", 5L, TimeUnit.SECONDS);
        MyQueue order2 = new MyQueue("order2", 10L, TimeUnit.SECONDS);
        MyQueue order3 = new MyQueue("order3", 15L, TimeUnit.SECONDS);


        DelayQueue<MyQueue> delayeds = new DelayQueue<>();
        delayeds.put(order1);
        delayeds.put(order2);
        delayeds.put(order3);

        System.out.println(String.format("当前时间：%s", LocalDateTime.now()));

        while (delayeds.size() > 0) {

            MyQueue poll = delayeds.poll();
            if (poll != null)
                System.out.println(poll.name + "," + poll.time + "," + LocalDateTime.now());

            Thread.sleep(1000);
        }
    }


}

class MyQueue implements Delayed {

    public Long time;

    public String name;

    public MyQueue(String name, Long time, TimeUnit unit) {
        this.name = name;
        this.time = System.currentTimeMillis() + (time > 0 ? unit.toMillis(time) : 0);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        MyQueue o1 = (MyQueue) o;
        long l = time - o1.time;
        if (l <= 0) {
            return -1;
        }
        return 1;
    }


    public static void main(String[] args) {

        ArrayList<String> l1 = new ArrayList<String>();

        ArrayList<Integer> l2 = new ArrayList<Integer>();

        l1.add("1");

        l2.add(1);

        System.out.println(l1.get(0).getClass());

        System.out.println(l2.get(0).getClass());

        System.out.println(l1.getClass() == l2.getClass());

    }

}