package org.xr.happy.tread.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;

public class DogAndCatDemo {

    public static void main(String[] args) {
        DogAndCatQueue<Animal> dogAndCatQueue = new DogAndCatQueue<>();
        Dog dog1 = new Dog("比熊");
        Dog dog2 = new Dog("阿拉斯加");
        Dog dog3 = new Dog("金毛");
        Cat cat1 = new Cat("狸猫");
        Cat cat2 = new Cat("黑猫");

        dogAndCatQueue.add(dog1);
        dogAndCatQueue.add(dog2);
        dogAndCatQueue.add(dog3);
        dogAndCatQueue.add(cat1);
        dogAndCatQueue.add(cat2);

        boolean empty = dogAndCatQueue.isEmpty();
        boolean catEmpty = dogAndCatQueue.isCatEmpty();
        boolean dogEmpty = dogAndCatQueue.isDogEmpty();
        System.out.println("empty ：" + empty);
        System.out.println("catEmpty ：" + catEmpty);
        System.out.println("dogEmpty ：" + dogEmpty);

        System.out.println("pollDog :" + dogAndCatQueue.pollDog());
        System.out.println("pollCat :" + dogAndCatQueue.pollCat());
        System.out.println("dogAndCatQueue size :" + dogAndCatQueue.size());

        dogAndCatQueue.add(dog1);
        dogAndCatQueue.add(dog2);
        dogAndCatQueue.add(dog3);
        dogAndCatQueue.add(cat1);
        dogAndCatQueue.add(cat2);
        System.out.println("dogAndCatQueue size :" + dogAndCatQueue.size());

        System.out.println("poll all :" + dogAndCatQueue.pollAll());
    }

}

class DogAndCatQueue<T> {

    private LinkedBlockingDeque<Animal> linkedBlockingDeque = new LinkedBlockingDeque<>();

    public long size() {
        return linkedBlockingDeque.size();
    }

    /**
     * 用户可以调用 add 方法将 cat 或者 dog 放入队列中
     *
     * @param animal
     */
    public void add(Animal animal) {
        linkedBlockingDeque.offer(animal);
    }

    /**
     * 用户可以调用 pollAll 方法将队列中的 cat 和 dog 按照进队列的先后顺序依次弹出
     */
    public List<Animal> pollAll() {

        List<Animal> list = new ArrayList<>();
        int size = linkedBlockingDeque.size();
        for (int i = 0; i < size; i++) {
            Animal poll = linkedBlockingDeque.poll();
            list.add(poll);
        }

        return list;
    }

    /**
     * 用户可以调用 pollDog 方法将队列中的 dog 按照进队列的先后顺序依次弹出
     */
    public List<Animal> pollDog() {
        return pollDogFilter();
    }

    private List<Animal> pollDogFilter() {
        List<Animal> list = new ArrayList<>();
        int size = linkedBlockingDeque.size();
        for (int i = 0; i < size; i++) {
            Animal peek = linkedBlockingDeque.peek();
            if (peek instanceof Dog) {
                Animal poll = linkedBlockingDeque.poll();
                list.add(poll);
            }
        }

        return list;
    }

    /**
     * 用户可以调用 pollCat 方法将队列中的 cat 按照进队列的先后顺序依次弹出
     */
    public List<Animal> pollCat() {
        return pollCatFilter();
    }

    private List<Animal> pollCatFilter() {
        List<Animal> list = new ArrayList<>();
        int size = linkedBlockingDeque.size();
        for (int i = 0; i < size; i++) {
            Animal peek = linkedBlockingDeque.peek();
            if (peek instanceof Cat) {
                Animal poll = linkedBlockingDeque.poll();
                list.add(poll);
            }
        }

        return list;
    }

    /**
     * 用户可以调用 isEmpty 方法检查队列中是否还有 dog 或 cat
     */
    public boolean isEmpty() {
        return linkedBlockingDeque.isEmpty();
    }

    /**
     * 用户可以调用 isDogEmpty 方法检查队列中是否还有 dog
     */
    public boolean isDogEmpty() {
        return linkedBlockingDeque.stream().filter(s -> s instanceof Dog).count() <= 0;
    }

    /**
     * 用户可以调用 isCatEmpty 方法检查队列中是否还有 cat
     */
    public boolean isCatEmpty() {
        return linkedBlockingDeque.stream().filter(s -> s instanceof Cat).count() <= 0;
    }

}

interface Animal {

}

class Dog implements Animal {

    private String name;

    public Dog() {
    }

    public Dog(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return Objects.equals(name, dog.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

class Cat implements Animal {
    private String name;

    public Cat() {
    }

    public Cat(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cag{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equals(name, cat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}