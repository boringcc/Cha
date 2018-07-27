package com.cc.admin.test.SF.Sort;

public class BaseSort {
    //判断是否前一个比后一个小
    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    //交换
    public static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
