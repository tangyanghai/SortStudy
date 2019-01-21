package com.tyh.java.sortstudy;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.tyh.java.lib_base.BaseActivity;
import com.tyh.java.sortstudy.sort.AbsSort;
import com.tyh.java.sortstudy.sort.ArrFactory;
import com.tyh.java.sortstudy.sort.SortEnum;
import com.tyh.java.sortstudy.sort.SortFactory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/21
 * 描述:
 */
public class SortActivity
        extends BaseActivity
        implements AbsSort.OnElementChangedListener, SortViewGroup.OnViewStateChangedListener {

    private final int ARR_COUNT = 10;

    @BindView(R.id.sv)
    SortViewGroup sv;

    volatile ArrayList<int[]> list;

    int[] arr;
    int[] colorArr = new int[]{Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW};

    boolean canChangedByClick = true;
    private boolean startChange = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sort;
    }

    @Override
    protected void initView() {
        sv.setOnViewStateChangedListener(this);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        arr = ArrFactory.createRandomArr(ARR_COUNT);
        for (int i = 0; i < arr.length; i++) {
            sv.addView(createView(arr[i]));
        }
    }

    private View createView(int value) {
        TextView tv = new TextView(this);
        tv.setText(String.valueOf(value));
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(colorArr[0]);
        SortViewGroup.LayoutParams params = new SortViewGroup.LayoutParams(80, 80);
        tv.setLayoutParams(params);
        return tv;
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (!list.isEmpty()) {
                    int[] remove = list.remove(0);
                    if (remove[0] >= 0) {
                        sv.changeChild(remove[0], remove[1]);
                    } else {
                        View v = sv.getChildAtIndex(remove[1]);
                        if (v != null) {
                            v.setBackgroundColor(colorArr[3]);
                        }
                    }
                    sendEmptyMessageDelayed(1, 500);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onElementChanged(int e1, int e2) {
        list.add(new int[]{e1, e2});
        if (!startChange) {
            startChange = true;
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    public void onElementSortFinish(int index) {
        list.add(new int[]{-1, index});
        if (!startChange) {
            startChange = true;
            handler.sendEmptyMessage(1);
        }
    }

    @OnClick({R.id.bt_bubble_sort, R.id.bt_quick_sort, R.id.bt_reset_view, R.id.bt_reset_arr_and_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_bubble_sort:
                sort(SortEnum.BubbleSort);
                break;
            case R.id.bt_quick_sort:
                sort(SortEnum.OriginQuikeSort);
                break;
            case R.id.bt_reset_view:
                reset();
                break;
            case R.id.bt_reset_arr_and_view:
                arr = ArrFactory.createRandomArr(ARR_COUNT);
                reset();
                break;
        }
    }

    private void reset() {
        handler.removeMessages(1);
        canChangedByClick = true;
        list.clear();
        startChange = false;
        sv.removeAllViews();
        for (int i = 0; i < arr.length; i++) {
            sv.addView(createView(arr[i]));
        }
    }

    private void sort(SortEnum sortEnum) {
        if (canChangedByClick) {
            canChangedByClick = false;
            AbsSort sort = SortFactory.createSort(sortEnum, arr);
            sort.setOnElementChangedListener(this);
            sort.start();
        }
    }

    @Override
    public void onMoving(View v) {
        v.setBackgroundColor(colorArr[1]);
    }

    @Override
    public void onFinishedMove(View v) {
        v.setBackgroundColor(colorArr[0]);
    }
}
