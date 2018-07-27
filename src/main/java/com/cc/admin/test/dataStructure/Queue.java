package com.cc.admin.test.dataStructure;

public class Queue<Item> {
    private Node first;
    private Node last;
    int N = 0;
    private class Node{
        Item item;
        Node next;
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public int size(){
        return N;
    }

    // 入队列
    public void enqueue(Item item){
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;
        if(isEmpty()){
            last = newNode;
            first = newNode;
        } else{
            last.next = newNode;
            last = newNode;
        }
        N++;
    }

    // 出队列
    public Item dequeue() {
        if (isEmpty()){ return null;}
        Node node = first;
        first = first.next;
        N--;
        if (isEmpty()) {last = null;}
        return node.item;
    }
}