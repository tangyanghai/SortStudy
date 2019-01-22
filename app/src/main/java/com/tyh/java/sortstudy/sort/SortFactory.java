package com.tyh.java.sortstudy.sort;


/**
 * 创建人: tyh
 * 创建时间: 2019/1/16
 * 描述:
 */
public class SortFactory {
    public static AbsSort createSort(SortEnum e, int[] nums) {
        AbsSort sort = null;
        switch (e) {
            case BubbleSort:
                sort = new BubbleSort(nums);
                break;
            case QuickSort:
                sort = new QuickSort(nums);
                break;
            case SelectSort:
                sort = new SelectSort(nums);
                break;
            case InsertSort:
                sort = new InsertSort(nums);
                break;
            case ShellSort:
                sort = new ShellSort(nums);
                break;

        }
        return sort;
    }
}
