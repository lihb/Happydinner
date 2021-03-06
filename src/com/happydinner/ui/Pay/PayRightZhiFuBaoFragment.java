package com.happydinner.ui.Pay;

import java.math.BigDecimal;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.happydinner.activity.R;
import com.happydinner.util.CommonUtils;

/**
 * 类说明：
 *
 * @author Administrator
 * @version 1.0
 * @date 2015/6/16
 */

public class PayRightZhiFuBaoFragment extends Fragment {

    @InjectView(R.id.pay_price_tv)
    TextView payPriceTv;

    @InjectView(R.id.pay_choice_tv)
    TextView payChoiceTv;

    @InjectView(R.id.pay_info_step1_img)
    ImageView payInfoStep1Img;

    @InjectView(R.id.pay_info_step1_tv)
    TextView payInfoStep1Tv;

    @InjectView(R.id.pay_info_step2_img)
    ImageView payInfoStep2Img;

    @InjectView(R.id.pay_info_step2_tv)
    TextView payInfoStep2Tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pay_right_fragment, null);
        ButterKnife.inject(this, view);
        Bundle bundle = getArguments();
        float price = bundle.getFloat("price");
        float temp = CommonUtils.round(price, 2, BigDecimal.ROUND_HALF_UP);
        payPriceTv.setText("金额：" + temp + "元");
        payChoiceTv.setText("支付宝支付");
        payInfoStep1Tv.setText("1.打开手机支付宝应用\n点击扫一扫功能");
        payInfoStep2Tv.setText("2.扫描该二维码，成功后\n按照提示输入密码");

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
