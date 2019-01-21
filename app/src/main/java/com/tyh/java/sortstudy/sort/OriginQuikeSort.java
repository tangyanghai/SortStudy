package com.tyh.java.sortstudy.sort;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/17
 * 描述:
 */
public class OriginQuikeSort extends AbsSort {
    public OriginQuikeSort(int[] nums) {
        super(nums);
    }

    @Override
    public int[] sort() {
        qsort(nums, 0, size - 1);
        return nums;
    }

    private void qsort(int[] arr, int start, int end) {
        if (start < end) {
            int pivot = partition(arr, start, end);        //将数组分为两部分
            elementAtIndexSortFinished(pivot);
            qsort(arr, start, pivot - 1);                   //递归排序左子数组
            qsort(arr, pivot + 1, end);                  //递归排序右子数组
        }else if(start == end){
            elementAtIndexSortFinished(end);
        }
    }

    private int partition(int[] arr, int start, int end) {
        int pivot = arr[start];     //枢轴记录
        while (start < end) {
            while (start < end && arr[end] >= pivot) --end;
//            arr[start] = arr[end];             //交换比枢轴小的记录到左端
            changElements(start,end);
            while (start < end && arr[start] <= pivot) ++start;
//            arr[end] = arr[start];           //交换比枢轴小的记录到右端
            changElements(start,end);
            changeCount++;
        }
        //扫描完成，枢轴到位
//        arr[start] = pivot;
        //返回的是枢轴的位置
        return start;
    }

}
