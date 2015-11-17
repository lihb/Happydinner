package com.handgold.pjdc.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.handgold.pjdc.R;
import com.handgold.pjdc.base.BaseActivity;
import com.handgold.pjdc.ui.Pay.PayLeftFragment;
import com.handgold.pjdc.ui.Pay.PayRightWeChatFragment;
import com.handgold.pjdc.ui.Pay.PayRightZhiFuBaoFragment;
import com.handgold.pjdc.ui.widget.HeadView;
import com.handgold.pjdc.ui.widget.PopupPayInfoView;
import com.umeng.analytics.MobclickAgent;

/**
 * 类说明：
 *
 * @author Administrator
 * @version 1.0
 * @date 2015/6/16
 */

public class PayActivity extends BaseActivity {

    private HeadView headView;

    private LinearLayout mLeftFragLayout;
    private LinearLayout mRightFragLayout;

    private FragmentManager mFragmentManager;

    private FragmentTransaction mTransaction;

    private PayRightZhiFuBaoFragment payRightZhiFuBaoFragment;
    private PayRightWeChatFragment payRightWeChatFragment;

    private PayLeftFragment payLeftFragment;

    private RelativeLayout mPayInfoRelativeLayout;

    private PopupPayInfoView mPopupPayInfoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 去除title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_pay);
        initView();
        mFragmentManager = getFragmentManager();
        initFragment();

    }

    private void initView() {
        headView = new HeadView(this);
        headView.h_left_tv.setText("返回");
        headView.h_title.setText("选择支付方式付款");
        headView.h_right_tv_llyt.setVisibility(View.VISIBLE);
        headView.h_right_tv.setText("");
        headView.h_left.setOnClickListener(mOnClickListener);
        headView.h_left_rlyt.setOnClickListener(mOnClickListener);

        mPayInfoRelativeLayout = (RelativeLayout) findViewById(R.id.popup_pay_info_relativeLayout);
        mPopupPayInfoView = (PopupPayInfoView) findViewById(R.id.popup_pay_info_view);

        mLeftFragLayout = (LinearLayout) findViewById(R.id.left_frag);
        mRightFragLayout = (LinearLayout) findViewById(R.id.right_frag);

        mLeftFragLayout.setBackgroundResource(R.drawable.paying_left);
        mRightFragLayout.setBackgroundResource(R.drawable.paying_right);

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.head_left:
                case R.id.head_left_rlyt:
                    finish();
//                    mPayInfoRelativeLayout.setVisibility(View.VISIBLE);
//                    mPopupPayInfoView.updateUI(true);
                    break;

                default:
                    break;
            }
        }
    };

    private void initFragment() {
        mTransaction = mFragmentManager.beginTransaction();

        if (payLeftFragment == null) {
            payLeftFragment = new PayLeftFragment();
            mTransaction.replace(R.id.left_frag, payLeftFragment, "pay_left_frag");
        }

        if (payRightZhiFuBaoFragment == null) {
            payRightZhiFuBaoFragment = new PayRightZhiFuBaoFragment();
            Intent intent = getIntent();
            float price = intent.getFloatExtra("totalPrice", 0f);
            Bundle bundle = new Bundle();
            bundle.putFloat("price", price);
            payRightZhiFuBaoFragment.setArguments(bundle);
            mTransaction.replace(R.id.right_frag, payRightZhiFuBaoFragment, "pay_right_zhifubao_frag");
        }
        mTransaction.commit();

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

}
