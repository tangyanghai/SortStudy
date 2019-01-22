package com.tyh.java.sortstudy.fragment;

import com.tyh.java.sortstudy.sort.AbsSort;
import com.tyh.java.sortstudy.sort.SortEnum;
import com.tyh.java.sortstudy.sort.SortFactory;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/22
 * 描述:希尔排序
 */
public class ShellSortFragment extends BubbleSortFragment {
    protected void sort() {
        if (canChangedByClick) {
            canChangedByClick = false;
            AbsSort sort = SortFactory.createSort(SortEnum.ShellSort, arr);
            sort.setOnElementChangedListener(this);
            sort.start();
        }
    }
}
