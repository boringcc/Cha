package com.cc.admin.test;

import com.cc.admin.test.dataStructure.Stack;

import java.util.ArrayList;
import java.util.Arrays;

public class jianZhiOffer {


    //3:数组中重复的数字

    /**
     * 在一个长度为 n 的数组里的所有数字都在 0 到 n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字是重复的，
     * 也不知道每个数字重复几次。请找出数组中任意一个重复的数字。例如，如果输入长度为 7 的数组 {2, 3, 1, 0, 2, 5}，
     * 那么对应的输出是第一个重复的数字 2。
     * 要求复杂度为 O(N) + O(1)，也就是时间复杂度 O(N)，空间复杂度 O(1)。
     * 因此不能使用排序的方法，也不能使用额外的标记数组。
     * 牛客网讨论区这一题的首票答案使用 nums[i] + length 来将元素标记，这么做会有加法溢出问题。
     */
    //牛客网的答案
    public boolean duplicate(int[] nums, int length, int[] duplication) {
        if (nums == null || length <= 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            while (nums[i] != i) {
                if (nums[i] == nums[nums[i]]) {
                    duplication[0] = nums[i];
                    return true;
                }
                swap(nums, i, nums[i]);
            }
        }
        return false;
    }

    //大神的解法
    public boolean duplicate1(int numbers[], int length, int[] duplication) {
        boolean[] k = new boolean[length]; //初始值全部为false
        for (int i = 0; i < k.length; i++) {
            if (k[numbers[i]] == true) {
                duplication[0] = numbers[i];
                return true;
            }
            k[numbers[i]] = true;
        }
        return false;
    }

    //辅助函数，交换数组的2个值
    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }


    //4：二维数组中的查找

    /**
     * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
     * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     * Consider the following matrix:
     * [
     * [1,   4,  7, 11, 15],
     * [2,   5,  8, 12, 19],
     * [3,   6,  9, 16, 22],
     * [10, 13, 14, 17, 24],
     * [18, 21, 23, 26, 30]
     * ]
     * Given target = 5, return true.
     * Given target = 20, return false.
     */

    //左下角查询，比他大就列数+1 ，比他小就行数减一
    public boolean Find(int target, int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int rows = matrix.length, cols = matrix[0].length;


        for (int i = rows - 1, j = 0; i >= 0 && j < cols; ) {
            if (target == matrix[i][j]) {
                return true;
            }
            if (target < matrix[i][j]) {
                i--;
                continue;
            }
            if (target > matrix[i][j]) {
                j++;
                continue;
            }
        }
        return false;
    }

    //5：替换空格

    /**
     * 请实现一个函数，将一个字符串中的空格替换成“%20”。
     * 当字符串为 We Are Happy. 则经过替换之后的字符串为 We%20Are%20Happy。
     * <p>
     * 牛客网的思路：首先得到空格的个数，然后增加字符串的长度，每加一个空格增加3个长度
     * 然后设置2个坐标，一个指向原来字符串的结尾，一个指向新字符串的结尾
     * 然后进行从后往前的复制，遇到空格就复制替换的单位
     */
    public String replaceSpace(StringBuffer str) {
        int oldLen = str.length();
        for (int i = 0; i < oldLen; i++) {
            if (str.charAt(i) == ' ') {
                str.append("  ");
            }
        }
        int P1 = oldLen - 1, P2 = str.length() - 1;
        while (P1 >= 0 && P2 > P1) {
            char c = str.charAt(P1--);
            if (c == ' ') {
                str.setCharAt(P2--, '0');str.setCharAt(P2--, '2');
                str.setCharAt(P2--, '%');
            } else {
                str.setCharAt(P2--, c);
            }
        }
        return str.toString();
    }


    //6：从尾到头打印链表

    /**
     * 输入链表的第一个节点，从尾到头反过来打印出每个结点的值。
     */
    //方法一：使用栈
    public void printListFromTailToHead() {

        /**
         *递归思想，每次将左右两颗子树当成新的子树进行处理，中序的左右子树索引很好找，
         * 前序的开始结束索引通过计算中序中左右子树的大小来计算，然后递归求解，
         * 直到startPre>endPre||startIn>endIn说明子树整理完到。方法每次返回左子树活右子树的根节点
         *
         preorder = [3,9,20,15,7]
         inorder =  [9,3,15,20,7]
         int[] pre, int preL, int preR, int[] in, int inL, int inR
         第一次是（pre,0,4,in,0,4）
         root = pre[0];pre[0] = 3 ;index = inorder.get(3) = 1;  leftTreeSize = index - inL = 1,
         leftTreeSzie就是根的左子树大小，
         然后插入左子树 递归该函数(pre,1,1,in,0,4)
         root = pre[1] ,pre[1] = 9 ; index = 0; leftTreeSize = 0;
         然后插入左子树 递归该函数(pre,2,1,in,0,4) 这里就会出现 preL > pre
         */
    }

    //7:重建二叉树


    public static void main(String[] args) {
        String str = "123 231";
        System.out.println(str.toString().replaceAll(" ", "%20"));
    }

}


