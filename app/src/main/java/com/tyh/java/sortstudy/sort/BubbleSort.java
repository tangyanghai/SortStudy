package com.tyh.java.sortstudy.sort;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/16
 * 描述:冒泡排序
 */
class BubbleSort extends AbsSort {
    public BubbleSort(int[] nums) {
        super(nums);
    }

    @Override
    public int[] sort() {
        int count;
        for (int i = 0; i < size - 1; i++) {
            count = 0;
            for (int j = 0; j < size - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    changElements(j, j + 1);
                    count++;
                }
            }

            elementAtIndexSortFinished(size - 1 - i);

            if (count == 0) {
                //这里是优化,防止已经排序好了的序列不断重复排序
                for (int j = size - 2 - i; j >= 1; j--) {
                    elementAtIndexSortFinished(j);
                }
                break;
            }
        }

        elementAtIndexSortFinished(0);

        return nums;
    }


}
