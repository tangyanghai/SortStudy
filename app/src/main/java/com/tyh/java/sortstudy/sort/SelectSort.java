package com.tyh.java.sortstudy.sort;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/22
 * 描述:选择排序
 * <p>原理:</p>
 * <p>1.从一个元素开始,找到最小的元素,和第一个元素交换</p>
 * <p>2.从第二个元素开始,找到最小的元素和第二个元素交换</p>
 * <p>...</p>
 * <p>最后,对最后两个数进行交换</p>
 * <p></p>
 * <p></p>
 * <p></p>
 * <p></p>
 * <p></p>
 * <p></p>
 * <p></p>
 */
public class SelectSort extends AbsSort {
    public SelectSort(int[] nums) {
        super(nums);
    }

    @Override
    protected int[] sort() {
        int[] arr = new int[size];
        int minIndex = 0;
        for (int i = 0; i < size; i++) {
            minIndex = i;
            temp = nums[i];
            for (int j = i+1; j < size; j++) {
                if (nums[j] < temp) {
                    temp = nums[j];
                    minIndex = j;
                }
            }
            if (minIndex!=i) {
                changElements(i,minIndex);
            }
            elementAtIndexSortFinished(i);
        }

        return arr;
    }
}
