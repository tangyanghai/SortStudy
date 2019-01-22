package com.tyh.java.sortstudy;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tyh.java.lib_base.BaseActivity;
import com.tyh.java.sortstudy.fragment.BubbleSortFragment;
import com.tyh.java.sortstudy.fragment.InsertSortFragment;
import com.tyh.java.sortstudy.fragment.QuickSortFragment;
import com.tyh.java.sortstudy.fragment.SelectSortFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/21
 * 描述:
 */
public class SortActivity
        extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.fl_sort)
    FrameLayout flSort;
    private SlidingMenu slidingMenu;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sort;
    }

    @Override
    protected void initView() {
        showFragment(new BubbleSortFragment());
        initMenu();
    }

    private void initMenu() {
        //实例化菜单控件
        slidingMenu = new SlidingMenu(this);
        //设置相关属性
        slidingMenu.setMode(SlidingMenu.RIGHT);//菜单靠右
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//全屏支持触摸拖拉
        slidingMenu.setBehindOffset(600);//SlidingMenu划出时主页面显示的剩余宽度
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//不包含ActionBar
        slidingMenu.setMenu(R.layout.menu_sort_types);
        TextView btBubble = findViewById(R.id.bt_sort_type_bubble);
        TextView btQuick = findViewById(R.id.bt_sort_type_quick);
        TextView btSelect = findViewById(R.id.bt_sort_type_select);
        TextView btInsert = findViewById(R.id.bt_sort_type_insert);
        btBubble.setOnClickListener(this);
        btQuick.setOnClickListener(this);
        btSelect.setOnClickListener(this);
        btInsert.setOnClickListener(this);
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_sort, fragment).commit();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_back, R.id.img_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_menu:
                slidingMenu.showMenu();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sort_type_bubble:
                showFragment(new BubbleSortFragment());
                break;
            case R.id.bt_sort_type_quick:
                showFragment(new QuickSortFragment());
                break;
            case R.id.bt_sort_type_select:
                showFragment(new SelectSortFragment());
                break;
            case R.id.bt_sort_type_insert:
                showFragment(new InsertSortFragment());
                break;
        }
    }

}
