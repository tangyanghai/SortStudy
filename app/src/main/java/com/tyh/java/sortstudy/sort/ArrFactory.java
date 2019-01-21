package com.tyh.java.sortstudy.sort;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/21
 * 描述:
 */
public class ArrFactory {

    public static int[] createSortedArr(int count){

        int[] arr = new int[count];

        for (int i = 0; i < count; i++) {
            arr[i] = count-i;
        }

        return arr;

    }


    public static int[] createRandomArr(int count){
        int[] arr = new int[count];

        for (int i = 0; i < count; i++) {
            arr[i] = (int) (Math.random()*100+Math.random()*50);
        }

        return arr;
    }


}
