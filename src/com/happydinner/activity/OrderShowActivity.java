package com.happydinner.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.happydinner.base.ApplicationEx;
import com.happydinner.base.BaseActivity;
import com.happydinner.entitiy.Menu;
import com.happydinner.entitiy.Order;
import com.happydinner.ui.OrderLeftFragment;
import com.happydinner.ui.OrderRightFragment;
import com.happydinner.ui.widget.HeadView;

import java.util.List;

/**
 * 类说明：
 *
 * @author Administrator
 * @version 1.0
 * @date 2015/5/20
 */

public class OrderShowActivity extends BaseActivity implements OrderRightFragment.RefreshLeftFragListener {

    /**
     * 左侧结算fragment
     */
    private OrderLeftFragment mOrderLeftFragment;
    /**
     * 右侧展示菜品fragment
     */
    private OrderRightFragment mOrderRightFragment;

    private FragmentManager fragmentManager;

    private Order mOrder;

    private List<Menu> orderMenuList;

    private HeadView headView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //去除title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        headView = new HeadView(this);
        Order tempOrder = (Order)((ApplicationEx) getApplication()).receiveInternalActivityParam("order");
        mOrder = tempOrder;
        fragmentManager = getFragmentManager();
        initFragment();

        headView.h_left_tv.setText("返回");
        headView.h_title.setText("自助下单");
        headView.h_left_rlyt.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.head_left:
                case R.id.head_left_rlyt:
                    OrderShowActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    };

    private void initFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        boolean leftneedAdd = false;
        boolean rightneedAdd = false;

        if (mOrder != null) {
            orderMenuList = mOrder.getMenuList();
        }

        String rightTag = createFragmentTag(R.id.order_right_frg);
        mOrderRightFragment = (OrderRightFragment) fragmentManager.findFragmentByTag(rightTag);
        if (mOrderRightFragment == null) {
            rightneedAdd = true;
           /* Bundle bundle = new Bundle();
            bundle.putParcelable("order", mOrder);
            mOrderRightFragment.setArguments(bundle);*/
            mOrderRightFragment = new OrderRightFragment();
        }

        String leftTag = createFragmentTag(R.id.order_left_frg);
        mOrderLeftFragment = (OrderLeftFragment) fragmentManager.findFragmentByTag(leftTag);
        if (mOrderLeftFragment == null) {
            leftneedAdd = true;
            /*Bundle bundle = new Bundle();
            bundle.putParcelable("order", mOrder);
            mOrderLeftFragment.setArguments(bundle);*/
            mOrderLeftFragment = new OrderLeftFragment();
        }

        if (rightneedAdd) {
            fragmentTransaction.add(R.id.order_right_frg, mOrderRightFragment, rightTag);
        }
        if (leftneedAdd) {
            fragmentTransaction.add(R.id.order_left_frg, mOrderLeftFragment, leftTag);
        }
        if (rightneedAdd || leftneedAdd) {
            fragmentTransaction.commit();
        }



    }
    private String createFragmentTag(int id) {
        return this.getClass().getSimpleName() + "_" + id;
    }

    @Override
    public void changeDataToLeft(Order order) {
        mOrderLeftFragment = (OrderLeftFragment) fragmentManager.findFragmentById(R.id.order_left_frg);
        mOrderLeftFragment.refresh(order);
    }
}
