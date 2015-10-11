package com.happydinner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.happydinner.base.ApplicationEx;
import com.happydinner.base.TypeEnum;
import com.happydinner.entitiy.Menu;
import com.happydinner.ui.Pay.FoodLeftFragment;
import com.happydinner.ui.Pay.FoodRightFragment;
import com.happydinner.ui.VideoPlayerFragment;
import com.happydinner.ui.widget.HeadView;
import com.happydinner.ui.widget.OrderShowView;
import com.happydinner.ui.widget.PopupMenuDetailView;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 * Created by lihb on 15/5/16.
 */
public class FoodShowActivity extends FragmentActivity {

    private SortedMap<Integer, List<Menu>> sortedMap;

    private HeadView headView;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    private VideoPlayerFragment videoPlayerFragment;

    private FoodLeftFragment foodLeftFragment = null;

    private FoodRightFragment foodRightFragment = null;

    private OrderShowView mOrderShowView = null;

    private PopupMenuDetailView mPopupMenuDetailView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 去除title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //去除title和状态栏的操作必须在 super.onCreate()方法之前
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_food_pay);

        mFragmentManager = getSupportFragmentManager();
        // 初始化界面
        initView();

        // 获取菜品数据
        sortedMap = (SortedMap) ((ApplicationEx) getApplication()).receiveInternalActivityParam("allMenuList");

        initFragment();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Log.i("displayMetrics------>", displayMetrics.toString());

    }

    private void initView() {
        headView = new HeadView(this);
        headView.h_left_tv.setText("返回");
        headView.h_title.setVisibility(View.GONE);
        headView.h_title_img.setVisibility(View.VISIBLE);
       /* headView.h_right_tv_llyt.setVisibility(View.VISIBLE);
        headView.h_right_tv.setText("确认");*/
        headView.h_left.setOnClickListener(mOnClickListener);
        headView.h_left_rlyt.setOnClickListener(mOnClickListener);
//        headView.h_right_tv.setOnClickListener(mOnClickListener);

        mOrderShowView = (OrderShowView) findViewById(R.id.order_show_view);
        mPopupMenuDetailView = (PopupMenuDetailView) findViewById(R.id.popup_detail_view);
    }

    private void initFragment() {
        mTransaction = mFragmentManager.beginTransaction();
        if (foodLeftFragment == null) {
            foodLeftFragment = new FoodLeftFragment();
            mTransaction.replace(R.id.left_frag, foodLeftFragment, "food_left_fragment");

        }

        if (foodRightFragment == null) {
            Bundle bundle = new Bundle();
            ArrayList<Menu> dataList = new ArrayList<Menu>();
            dataList.addAll(sortedMap.get(TypeEnum.DRINK.ordinal()));
            bundle.putParcelableArrayList("dataList", dataList);
            foodRightFragment = new FoodRightFragment();
            foodRightFragment.setArguments(bundle);
            mTransaction.replace(R.id.right_frag, foodRightFragment, "food_right_fragment");
        }

        mTransaction.commit();

    }


    private View.OnClickListener mOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.head_left:
                case R.id.head_left_rlyt:
                    finish();
                    break;
                case R.id.head_right_tv:
                case R.id.head_right_tv_layout:
                    Intent intent = new Intent(FoodShowActivity.this, OrderShowActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (mOrderShowView.isVisible() || mPopupMenuDetailView.isVisible()) {
            mOrderShowView.exitView();
            mPopupMenuDetailView.exitView();
        } else {
            super.onBackPressed();
        }
    }
}
