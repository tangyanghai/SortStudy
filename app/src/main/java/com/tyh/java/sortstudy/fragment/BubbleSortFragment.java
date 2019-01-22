package com.tyh.java.sortstudy.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.tyh.java.lib_base.BaseFragment;
import com.tyh.java.sortstudy.R;
import com.tyh.java.sortstudy.SortViewGroup;
import com.tyh.java.sortstudy.sort.AbsSort;
import com.tyh.java.sortstudy.sort.ArrFactory;
import com.tyh.java.sortstudy.sort.SortEnum;
import com.tyh.java.sortstudy.sort.SortFactory;

import java.io.FileInputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/22
 * 描述:冒泡排序
 */
public class BubbleSortFragment extends BaseFragment implements SortViewGroup.OnViewStateChangedListener, AbsSort.OnElementChangedListener {
    private int arr_count = 10;

    @BindView(R.id.sv)
    SortViewGroup sv;

    protected int[] arr;
    int[] colorArr = new int[]{Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW};
    volatile ArrayList<int[]> list;
    protected boolean canChangedByClick = true;
    private boolean startChange;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (!list.isEmpty()) {
                    int[] remove = null;
                    while (remove == null&&!list.isEmpty()) {
                        remove = list.remove(0);
                    }

                    if (remove == null) {
                        startChange = false;
                        return;
                    }

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
    protected int getContentView() {
        return R.layout.fragment_sort_bubble;
    }

    @Override
    public void initView(View view) {
        sv.setOnViewStateChangedListener(this);
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        resetArr();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @OnClick({R.id.bt_sort, R.id.bt_go_back, R.id.bt_change_arr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_sort:
                sort();
                break;
            case R.id.bt_go_back:
                resetMarks();
                resetSv();
                break;
            case R.id.bt_change_arr:
                resetMarks();
                resetArr();
                break;
        }
    }

    private void resetMarks() {
        handler.removeMessages(1);
        canChangedByClick = true;
        list.clear();
        startChange = false;
    }

    protected void sort() {
        if (canChangedByClick) {
            canChangedByClick = false;
            AbsSort sort = SortFactory.createSort(SortEnum.BubbleSort, arr);
            sort.setOnElementChangedListener(this);
            sort.start();
        }
    }

    private void resetArr() {
        arr = ArrFactory.createRandomArr(arr_count);
        resetSv();
    }

    private void resetSv() {
        if (arr != null && arr.length > 0) {
            sv.removeAllViews();
            for (int i = 0; i < arr.length; i++) {
                sv.addView(createView(arr[i]));
            }
        }
    }

    private View createView(int value) {
        TextView tv = new TextView(getActivity());
        tv.setText(String.valueOf(value));
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(colorArr[0]);
        SortViewGroup.LayoutParams params = new SortViewGroup.LayoutParams(80, 80);
        tv.setLayoutParams(params);
        return tv;
    }


    @Override
    public void onMoving(View v) {
        v.setBackgroundColor(colorArr[1]);
    }

    @Override
    public void onFinishedMove(View v) {
        v.setBackgroundColor(colorArr[0]);
    }

    @Override
    public void onElementChanged(int index1, int index2) {
        list.add(new int[]{index1, index2});
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
}
