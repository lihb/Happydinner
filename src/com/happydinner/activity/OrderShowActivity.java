package com.happydinner.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.ButterKnife;
import com.happydinner.base.BaseActivity;
import com.happydinner.entitiy.Menu;
import com.happydinner.entitiy.Order;
import com.happydinner.ui.OrderLeftFragment;
import com.happydinner.ui.OrderRightFragment;

import java.util.List;

/**
 * 类说明：
 *
 * @author Administrator
 * @version 1.0
 * @date 2015/5/20
 */

public class OrderShowActivity extends BaseActivity {

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


    public static void actionStart(Context context, Order order) {
        Intent intent = new Intent(context, OrderShowActivity.class);
        intent.putExtra("orderData", order);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        String testStr = intent.getStringExtra("test");
        mOrder = intent.getParcelableExtra("orderData");
        fragmentManager = getFragmentManager();
        initFragment();
    }

    private void initFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (mOrder != null) {
            orderMenuList = mOrder.getMenuList();
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("order", mOrder);

        mOrderLeftFragment = (OrderLeftFragment) fragmentManager.findFragmentById(R.layout.order_left_fragment);
        if (mOrderLeftFragment == null) {
            mOrderLeftFragment = new OrderLeftFragment();
            mOrderLeftFragment.setArguments(bundle);
        }

        mOrderRightFragment = (OrderRightFragment) fragmentManager.findFragmentById(R.layout.order_right_fragment);
        if (mOrderRightFragment == null) {
            mOrderRightFragment = new OrderRightFragment();
            mOrderRightFragment.setArguments(bundle);
        }

        fragmentTransaction.add(R.layout.order_left_fragment, mOrderLeftFragment);
        fragmentTransaction.add(R.layout.order_right_fragment, mOrderRightFragment).commit();



    }
}
