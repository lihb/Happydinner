package com.happydinner.activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.happydinner.base.BaseActivity;
import com.happydinner.ui.PayLeftFragment;

/**
 * 类说明：
 *
 * @author Administrator
 * @version 1.0
 * @date 2015/6/16
 */

public class PayActivity extends BaseActivity implements PayLeftFragment.OnPayLeftFragItemClickedListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //去除title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

    }

    @Override
    public void onItemClickListener() {

    }
}
