package com.tyh.java.sortstudy.sort;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/22
 * 描述:插入排序
 * <p>1.取第二个元素a</p>
 * <p>2.元素a依次于它前面的元素对比,比前面的元素小就交换位置,直到前面的元素小于等于a</p>
 * <p>3.元素a插入后,继续取下一个元素,重复步骤2</p>
 * <p>最终,所有元素都从前到后被逐渐插入到正确位置</p>
 */
public class InsertSort extends AbsSort {
    public InsertSort(int[] nums) {
        super(nums);
    }

    @Override
    protected int[] sort() {
        for (int i = 1; i < size; i++) {
            for (int j = i; j > 0 && nums[j] < nums[j - 1]; j--) {
                changElements(j,j-1);
            }
        }
        return nums;
    }

}
