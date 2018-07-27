package com.cc.admin.test.dataStructure;

public class Stack<Item> {

    private Node top = null;
    private int N = 0;

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void push(Item item) {
        Node newTop = new Node();
        newTop.item = item;
        newTop.next = top;
        top = newTop;
        N++;
    }

    public Item pop() {
        Item item = top.item;
        top = top.next;
        N--;
        return item;
    }
}