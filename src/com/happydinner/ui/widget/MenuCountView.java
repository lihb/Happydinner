package com.happydinner.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.InjectView;
import com.happydinner.activity.R;

/**
 * Created by Administrator on 2015/9/28.
 */
public class MenuCountView extends RelativeLayout {

    @InjectView(R.id.button_sub)
    ImageView mButtonSub;

    @InjectView(R.id.button_add)
    ImageView mButtonAdd;

    @InjectView(R.id.text_price)
    TextView mTextPrice;

    private int mCount = 0;

    public MenuCountView(Context context) {
        super(context);
    }

    public MenuCountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuCountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mButtonAdd = (ImageView) findViewById(R.id.button_add);
        mButtonSub = (ImageView) findViewById(R.id.button_sub);
        mTextPrice = (TextView) findViewById(R.id.text_price);
        mTextPrice.setText("" + mCount);
        isShowDetail();
        mButtonAdd.setOnClickListener(mOnClickListener);
        mButtonSub.setOnClickListener(mOnClickListener);

    }

    private  View.OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == mButtonAdd) {
                mCount++;
                isShowDetail();
            }else {
                mCount--;
                isShowDetail();
            }
        }
    };

    public void isShowDetail() {
        mTextPrice.setText("" + mCount);
        if (mCount > 0) {
            mButtonSub.setVisibility(VISIBLE);
            mTextPrice.setVisibility(VISIBLE);
            setBackgroundResource(R.drawable.menu_count_view_bg);
        }else {
            mButtonSub.setVisibility(GONE);
            mTextPrice.setVisibility(GONE);
            setBackgroundDrawable(null);
        }
    }
}
