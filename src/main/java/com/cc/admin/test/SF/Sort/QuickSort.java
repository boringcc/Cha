package com.cc.admin.test.SF.Sort;

import java.io.*;
import java.util.*;

public class QuickSort extends BaseSort{
    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v)) {
                if (i == hi) {
                    break;
                }
            }
            while (less(v, a[--j])) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    public static void createEx(int size,List<Integer> tofile) throws IOException {
        FileWriter fileWriter=new FileWriter("E:\\text.txt");
        Random rand = new Random(100);
        for (int i = 100; i >0; i--) {
            fileWriter.write(String.valueOf(i));
            fileWriter.write("\n");
        }
        fileWriter.flush();
        fileWriter.close();
    }


    public static void main(String[] args) throws IOException {

        int size = 60;
        List<Integer> toFile = new ArrayList();
        for(int x = 0 ; x < size ;x++){
            toFile.add(x);
        }
        createEx(size,toFile);
        File file = new File("E:\\text.txt");

        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;
        int row = 0;

        List read =new ArrayList();
        int i = 0;
        while ((line = in.readLine())!=null){
            read.add(line);
            i++;
        }
        Comparable[] a = new Comparable[i];


        for(int j=0;j < a.length ; j++){
            a[j] = (Comparable) read.get(j);
            System.out.println(a[j]);
        }
        sort(a);
        System.out.println("------------");
        for(int j=0;j < a.length ; j++){
            System.out.println(a[j]);
        }
        in.close();
    }

}