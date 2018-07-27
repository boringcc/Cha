package com.cc.admin.test.SF.Sort;

public class MaxPQ<Key extends Comparable<Key>>{
    private Key[] pq;
    private int N = 0;

    public MaxPQ(int maxN) {
        pq = (Key[]) new Comparable[maxN + 1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private void exch(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }
    void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }
    private void sink(Comparable[] a,int k,int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            //与子节点中大的那个交换
            if (j < n && less(a[j], a[j + 1])) {
                j++;
            }
            if (!less(a[k], a[j])) {
                break;
            }
            exch(a,k, j);
            k = j;
        }
    }


}