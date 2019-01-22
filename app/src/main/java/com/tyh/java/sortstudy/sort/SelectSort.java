package com.tyh.java.sortstudy.sort;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/22
 * 描述:选择排序
 * <p>原理:</p>
 * <p>1.从一个元素开始,找到最小的元素,和第一个元素交换</p>
 * <p>2.从第二个元素开始,找到最小的元素和第二个元素交换</p>
 * <p>...</p>
 */
public class SelectSort extends AbsSort {
    public SelectSort(int[] nums) {
        super(nums);
    }

    @Override
    protected int[] sort() {
        int minIndex = 0;
        for (int i = 0; i < size; i++) {
            minIndex = i;
            for (int j = i+1; j < size; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex!=i) {
                changElements(i,minIndex);
            }
            elementAtIndexSortFinished(i);
        }

        return nums;
    }
}
