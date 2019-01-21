package com.tyh.java.sortstudy.sort;

import com.tyh.java.sortstudy.SortViewGroup;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/16
 * 描述:快速排序
 * <p>
 * <p>原理:</p>
 * <p>采用分治法,分段对序列进行排序,加快排序速度</p>
 * <p>方法:</p>
 * <p>一个序列a,长度为l,一个基准点base,一个分治点m,一个起始位置start,一个终点end,有两个小兵pl,pr</p>
 * <p>最开始,起始位置就是start = 0,终点end = l - 1</p>
 * <p>如果start>=end,就说明该序列已经只有一个元素或者没有元素了,排序结束</p>
 * <p>基准点就定为序列的起始位置元素a[start]</p>
 * <p>pl从a[start + 1]开始,不断向右移动,遇到比base大的元素就停下</p>
 * <p>pr从a[end]开始,不断向左移动,遇到比base小的元素就停下</p>
 * <p>=====找到分治点的情况=====</p>
 * <p>情况1:当pl遇到pr</p>
 * <p>相遇的位置暂定为分治点m</p>
 * <p>如果a[m] < base,m就是分治点,将a[m]和base进行调换,然后分别对m左边和右边进行排序</p>
 * <p>如果a[m] > base,那么m-1才是分治点,即m = m -1,将a[m]和base进行调换,然后分别对m左边和右边进行排序</p>
 * <p>如果a[m] = base,那么分治点将会有两个即ml = m-1,mr = m,将a[m-1]和base进行调换,然后分别对ml左边和mr右边进行排序</p>
 * <p>情况2:当pl的位置移动到了pr的右边</p>
 * <p>说明pr及其左边都比base小,右边都比base大,即pr的位置为分治点m,调换base和a[m],然后分别对m左边和右边进行排序</p>
 * <p>=====需要调换元素的情况=====</p>
 * <p>当pl和pr都停止了走动,那么交换pl和pr对应元素的位置,继续走动</p>
 */
public class QuikeSort extends AbsSort {
    LeftPre pl;
    RightPre pr;

    public QuikeSort(int[] nums) {
        super(nums);
    }

    @Override
    public int[] sort() {
        pl = new LeftPre(0);
        pr = new RightPre(0);
        devideNums(nums, 0, size - 1);
        return nums;
    }

    /**
     * @param origin 要排序的数组
     * @param start  左边起始点
     * @param end    右边起始点
     */
    private void devideNums(int[] origin, int start, int end) {
        if (start >= end) {
            if (end == start) {
                elementAtIndexSortFinished(end);
            }
            return;
        }

        int base = origin[start];
        pl.cur = start + 1;
        pr.cur = end;
        boolean goleft;
        boolean goRight;
        int middle;
        while (true) {
            //如果两个相遇了,将base和相遇的位置调换,再分别去排两边
            if (pl.meet(pr)) {
                middle = pl.cur;
                if (base > origin[middle]) {
                    changElements(start, middle);
                    elementAtIndexSortFinished(middle);
                    devideNums(origin, start, middle - 1);
                    devideNums(origin, middle + 1, end);
                } else if (base < origin[middle]) {
                    middle = middle - 1;
                    changElements(start, middle);
                    elementAtIndexSortFinished(middle);
                    devideNums(origin, start, middle - 1);
                    devideNums(origin, middle + 1, end);
                } else {
                    changElements(start, middle - 1);
                    elementAtIndexSortFinished(middle - 1);
                    elementAtIndexSortFinished(middle);
                    devideNums(origin, start, middle - 2);
                    devideNums(origin, middle + 1, end);
                }
                break;
            }

            //如果两个交叉了
            if (pl.cur > pr.cur) {
                middle = pr.cur;
                changElements(start, middle);
                elementAtIndexSortFinished(middle);
                devideNums(origin, start, middle - 1);
                devideNums(origin, middle + 1, end);
                break;
            }

            //走一步
            goleft = pl.goNext(origin, base);
            goRight = pr.goNext(origin, base);

            //都停下了,就交换
            if (!goleft && !goRight) {
                changElements(pl.cur, pr.cur);
                pl.go();
                pr.go();
            }
        }
    }

    abstract class AbsPre {
        int cur;

        int end;

        public AbsPre(int cur) {
            this.cur = cur;
        }

        /**
         * @return 是否还有下一个
         */
        abstract boolean goNext(int[] nums, int base);

        /**
         * @return 两个小兵是否相遇
         */
        boolean meet(AbsPre pre) {
            return this.cur == pre.cur;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        abstract void go();
    }

    /**
     * 左边的小兵
     * 不断往右走,直到有比base大的数,或者到了终点,就停下来
     */
    class LeftPre extends AbsPre {
        public LeftPre(int cur) {
            super(cur);
        }

        @Override
        boolean goNext(int[] nums, int base) {

            if (nums[cur] > base) {
                return false;
            }

            go();
            return true;
        }

        @Override
        void go() {
            cur++;
        }
    }

    /**
     * 右边的小兵
     * 不断往左走,遇到比base小的数或或者到了终点,就停下
     */
    class RightPre extends AbsPre {

        public RightPre(int cur) {
            super(cur);
        }

        @Override
        boolean goNext(int[] nums, int base) {
            if (nums[cur] < base) {
                return false;
            }

            go();
            return true;
        }

        @Override
        void go() {
            cur--;
        }
    }

}
