package org.xr.happy.java.list;

public class LinkListTest {


    public static void main(String[] args) {

        Node2 head = new Node2("心茹");
        head.next=new Node2("凯凯");
        head.next.next=new Node2("宝贝");

        head.next.next.next= new Node2("Steven");


        System.out.println(head.toString());
    }
}

class Node2{

    Object value;

    Node2 next;

    public Node2 (Object value){
        this.value=value;
    }

    @Override
    public String toString() {
        return  value +
                ", " + next;
    }
}
