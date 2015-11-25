package com.handgold.pjdc.ui.widget;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.handgold.pjdc.R;

/**
 * Created by Administrator on 2015/10/26.
 */
public class PopupPayInfoView extends RelativeLayout {

    private ImageView mPayStatusImg = null;

    private TextView mPayStatusText = null;

    private TextView mPayDescText = null;

    /**
     * 付款是否成功标志
     */
    private boolean isSuccess = true;
    private boolean isRuuning = true;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public PopupPayInfoView(Context context) {
        super(context);
    }

    public PopupPayInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopupPayInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        mPayStatusImg = (ImageView) findViewById(R.id.imageview_pay_status);
        mPayStatusText = (TextView) findViewById(R.id.text_pay_status);
        mPayDescText = (TextView) findViewById(R.id.text_pay_desc);
    }

    public void updateUI(boolean successed) {
        if (successed) {
            mPayStatusImg.setImageResource(R.drawable.icon_success);
            mPayStatusText.setText("付款成功！");
            mPayStatusText.setTextColor(0xff000000);
            mPayDescText.setText("请耐心等候您的餐点\n\n     5S后自动返回");
            isRuuning = true;
            new Thread(new MyRunnable()).start();
        }else {
            mPayStatusImg.setImageResource(R.drawable.icon_error);
            mPayStatusText.setTextColor(0xffff0000);
            mPayStatusText.setText("付款失败！");
            mPayDescText.setText("请重新执行付款操作.");
        }
    }

    public void exitView() {
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.setVisibility(GONE);
        }
    }

    public android.os.Handler mHandler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what > 0) {
                mPayDescText.setText("请耐心等候您的餐点\n\n     "+ msg.what +"S后自动返回");
            }else {
                isRuuning = false;
                exitView();
            }
        }
    };

    private class MyRunnable implements Runnable{
        int duration = 4;
        @Override
        public void run() {
            while (isRuuning) {
              /*  try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                mHandler.sendEmptyMessageDelayed(duration > 0 ? duration : -1, 1000);
                duration--;
            }

        }
    }
}
