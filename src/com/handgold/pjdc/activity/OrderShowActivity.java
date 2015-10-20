package com.handgold.pjdc.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.handgold.pjdc.R;
import com.handgold.pjdc.entitiy.Order;
import com.handgold.pjdc.ui.Order.OrderLeftFragment;
import com.handgold.pjdc.ui.Order.OrderRightFragment;
import com.handgold.pjdc.ui.widget.HeadView;

/**
 * 类说明：
 *
 * @author Administrator
 * @version 1.0
 * @date 2015/5/20
 */

public class OrderShowActivity extends FragmentActivity implements OrderRightFragment.RefreshLeftFragListener {

    /**
     * 左侧结算fragment
     */
    private OrderLeftFragment mOrderLeftFragment;

    /**
     * 右侧展示菜品fragment
     */
    private OrderRightFragment mOrderRightFragment;

    private FragmentManager fragmentManager;

    private HeadView headView;

    public static final int ORDERSHOWACTIVITY = 2000;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 去除title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        headView = new HeadView(this);
        fragmentManager = getSupportFragmentManager();
        initFragment();

        headView.h_left_tv.setText("返回");
        headView.h_title.setText("自助下单");
        headView.h_left_rlyt.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
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

        String rightTag = createFragmentTag(R.id.order_right_frg);
        mOrderRightFragment = (OrderRightFragment) fragmentManager.findFragmentByTag(rightTag);
        if (mOrderRightFragment == null) {
            rightneedAdd = true;
            mOrderRightFragment = new OrderRightFragment();
            Bundle args = new Bundle();
            args.putInt(OrderRightFragment.ORDERRIGHTFRAGMENT, ORDERSHOWACTIVITY);
            mOrderRightFragment.setArguments(args);
        }

        String leftTag = createFragmentTag(R.id.order_left_frg);
        mOrderLeftFragment = (OrderLeftFragment) fragmentManager.findFragmentByTag(leftTag);
        if (mOrderLeftFragment == null) {
            leftneedAdd = true;
            mOrderLeftFragment = new OrderLeftFragment();
        }

        if (rightneedAdd) {
            fragmentTransaction.replace(R.id.order_right_frg, mOrderRightFragment, rightTag);
        }
        if (leftneedAdd) {
            fragmentTransaction.replace(R.id.order_left_frg, mOrderLeftFragment, leftTag);
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
