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
            case QuikeSort:
                sort = new QuikeSort(nums);
                break;
            case OriginQuikeSort:
                sort = new OriginQuikeSort(nums);
                break;
        }
        return sort;
    }
}
