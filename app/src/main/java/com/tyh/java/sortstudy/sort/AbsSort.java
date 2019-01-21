package com.tyh.java.sortstudy.sort;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/16
 * 描述:冒泡排序
 */
public abstract class AbsSort {
    int[] nums;
    int size;
    int temp;
    long changeCount;

    private OnElementChangedListener onElementChangedListener;

    public AbsSort(int[] nums) {
        this.size = nums.length;
        this.nums = Arrays.copyOf(nums, size);
    }

    public void start(){
        Executors.newCachedThreadPool().submit(new Runnable() {
            @Override
            public void run() {
                sort();
            }
        });
    }

    /**
     * @return 排序后的序列
     */
    protected abstract int[] sort();


    /**
     * @param index 该位置的元素排序完成
     */
    protected void elementAtIndexSortFinished(int index) {
        if (onElementChangedListener!=null) {
            onElementChangedListener.onElementSortFinish(index);
        }
    }


    /**
     * 交换两个位置的元素
     */
    protected void changElements(int p1, int p2) {
        if (p1 == p2||nums[p1] == nums[p2]) {
            return;
        }
        temp = nums[p2];
        nums[p2] = nums[p1];
        nums[p1] = temp;
        changeCount++;
        if (onElementChangedListener!=null) {
            onElementChangedListener.onElementChanged(p1,p2);
        }
    }

    public OnElementChangedListener getOnElementChangedListener() {
        return onElementChangedListener;
    }

    public void setOnElementChangedListener(OnElementChangedListener onElementChangedListener) {
        this.onElementChangedListener = onElementChangedListener;
    }

    protected void printChangeCount() {
//        System.out.print("\n交换次数:" + changeCount);
    }

    protected void printSortResult(String name) {
//        System.out.print("\n" + name + ":" + Arrays.toString(nums));
    }

    protected void printTime(String title){
        System.out.print("\n"+title+": "+System.currentTimeMillis()/1000);
    }

    /**
     * 元素变化回调
     */
    public interface OnElementChangedListener{
        /**
         * 两个元素调换位置
         */
        void onElementChanged(int e1,int e2);

        /**
         * 某个元素排序结束
         */
        void onElementSortFinish(int index);
    }

}
