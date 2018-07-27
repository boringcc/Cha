package com.cc.admin.test.SF.Search;


import com.sun.org.apache.regexp.internal.RE;

public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private Node root;
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        Key key;
        Value val;
        Node left, right;
        int N;
        boolean color;

        Node(Key key, Value val, int n, boolean color) {
            this.key = key;
            this.val = val;
            N = n;
            this.color = color;
        }
    }

    private boolean isRed(Node x) {
        if (x == null) {
            return false;
        }
        return x.color == RED;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.N;
    }



    public void put(Key key,Value value){
        root = put(root,key,value);
        root.color = BLACK;
    }


    //包含
    private boolean contains(Node x, Key key) {
        return (get(x, key) != null);
    }
    public boolean contains(Key key) {
        return (get(key) != null);
    }
    //红黑树的查找，和二叉树的查找是一样的，二叉树的用的是递归查找这里用的是for循环实现的
    public Value get(Key key) { return get(root, key); }
    private Value get(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else{
                return x.val;
            }
        }
        return null;
    }
    //插入操作
    private Node put(Node h, Key key, Value val) {
        //如果为空则返回一个新的节点，高度为1，color为RED
        if(h == null){
            return new Node(key,val,1,RED);
        }
        int cmp = key.compareTo(h.key);
        if(cmp < 0) {
            h.left = put(h.left,key,val);
        }
        else if(cmp > 0){
            h.right = put(h.right,key,val);
        }else {
            h.val = val;
        }
        return balance(h);
    }

    //

    //平衡红黑树
    public Node balance(Node h){
        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }
    //颜色转换
    public void flipColors(Node h){
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }
    //删除的颜色转换
    public void filpColorsDelete(Node h){
        h.color = BLACK;
        h.left.color = RED;
        h.right.color = RED;
    }
    //左旋转
    public Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }
    //右旋转
    public Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }
}