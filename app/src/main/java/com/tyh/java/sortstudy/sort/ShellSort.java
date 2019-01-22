package com.tyh.java.sortstudy.sort;

import android.util.Log;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/22
 * 描述:希尔排序
 * <p>
 * <p>希尔排序是升级版的插入排序</p>
 * <p>提升方式:</p>
 * <p>通过一个步长k,实现让元素插入时一次可以跨越一大步</p>
 * <p>逐步缩小k的值,最终k=1就是插入排序了,但是此时进行排序的元素已经很少了</p>
 */
public class ShellSort extends AbsSort {

    public ShellSort(int[] nums) {
        super(nums);
    }

    @Override
    protected int[] sort() {
        int step = size / 2;
        while (step >= 1) {
            for (int i = step; i < size; i++) {
                for (int k = i; k > 0 && k - step >= 0 && nums[k] < nums[k - step]; k -= step) {
                    changElements(k, k - step);
                }
            }
            step /= 2;
        }
        return nums;
    }

}
