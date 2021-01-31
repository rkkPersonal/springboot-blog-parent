package org.xr.happy.java.list;

import java.util.LinkedList;

public class LinkListDemo {


    public static void main(String[] args) {


        Node<String> header = new Node<>(null, "hello", null);

        Node<String> steven1 = new Node<>(header, "心茹", null);
        Node<String> steven = new Node<>(header, "Steven", steven1);


    }
}

class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "Node{" +
                "item=" + item +
                ", next=" + next +
                ", prev=" + prev +
                '}';
    }
}
