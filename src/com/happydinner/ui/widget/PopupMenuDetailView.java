package com.happydinner.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.happydinner.activity.R;
import com.happydinner.entitiy.Menu;

/**
 * Created by Administrator on 2015/9/30.
 */
public class PopupMenuDetailView extends RelativeLayout {

    private RoundedImageView mVideoImage = null;

    private ImageView mCloseImage = null;

    private ImageView mPlayImage = null;

    private ImageView mAddImage = null;

    private ImageView mSubImage = null;

    private TextView mMenuCount = null;

    private RelativeLayout mPopOperationLayout = null;

    private TextView mMenuName = null;

    private TextView mMenuPrice = null;

    private TextView mMenuRestaurant = null;

    private TextView mMenuDesc = null;

    private Menu mMenuData = null;

    private int mNum = 0;

    public static final int OPEARTION_ADD = 1;
    public static final int OPEARTION_SUB = 2;

    public int curOpeartion = 0;

    public PopupMenuDetailView(Context context) {
        super(context);
    }

    public PopupMenuDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopupMenuDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void initViews() {
        mVideoImage = (RoundedImageView) findViewById(R.id.video_pic);
        mCloseImage = (ImageView) findViewById(R.id.iv_popup_close);
        mPlayImage = (ImageView) findViewById(R.id.iv_popup_play);
        mAddImage = (ImageView) findViewById(R.id.popup_imagview_add);
        mSubImage = (ImageView) findViewById(R.id.popup_imagview_sub);

        mMenuName = (TextView) findViewById(R.id.popup_menu_name);
        mMenuCount = (TextView) findViewById(R.id.popup_text_count);
        mMenuPrice = (TextView) findViewById(R.id.popup_menu_price);
        mMenuRestaurant = (TextView) findViewById(R.id.popup_menu_restaurant);
        mMenuDesc = (TextView) findViewById(R.id.popup_menu_desc);

        mPopOperationLayout = (RelativeLayout) findViewById(R.id.popup_relative_operation);

//        mVideoImage.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.image_4), 8, RoundedImageView.CORNER_BOTTOM_LEFT);

        mVideoImage.setOnClickListener(mOnClickListener);
        mCloseImage.setOnClickListener(mOnClickListener);
        mPlayImage.setOnClickListener(mOnClickListener);
        mAddImage.setOnClickListener(mOnClickListener);
        mSubImage.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (v == mPlayImage || v == mVideoImage) {

            }else if (v == mCloseImage) {
                exitView();
            }else if(v == mSubImage){
                mNum--;
                curOpeartion = OPEARTION_SUB;
                showSubOper();
            }else {
                mNum++;
                curOpeartion = OPEARTION_ADD;
                showSubOper();
            }
        }
    };

    public void exitView() {
        ((ViewGroup)getParent()).setVisibility(GONE);
        if (mOnPopDetailViewListener != null) {
            mMenuData.setCount(curOpeartion == OPEARTION_ADD ? mNum - 1 : mNum + 1);
            mOnPopDetailViewListener.onDataChanged(mMenuData, curOpeartion);
        }
    }


    public void initData(Menu menu) {
        mMenuData = menu;
        mNum = menu.count;
        mMenuName.setText(menu.getName());
        mMenuPrice.setText("" + menu.getPrice());
        mMenuRestaurant.setText(menu.getRestaurantName());
        mMenuDesc.setText(menu.getInfo());
        mMenuCount.setText("" + mNum);
    }

    private void showSubOper() {
        if (mNum > 0) {
            mPopOperationLayout.setBackgroundResource(R.drawable.menu_count_view_bg);
            mSubImage.setVisibility(VISIBLE);
            mMenuCount.setVisibility(VISIBLE);
            mMenuCount.setText("" +  mNum);
        }else {
            mPopOperationLayout.setBackground(null);
            mSubImage.setVisibility(GONE);
            mMenuCount.setVisibility(GONE);
        }
    }

    private OnPopDetailViewListener mOnPopDetailViewListener = null;

    public void setOnPopDetailViewListener(OnPopDetailViewListener onPopDetailViewListener) {
        this.mOnPopDetailViewListener = onPopDetailViewListener;
    }

    public interface OnPopDetailViewListener{
        void onDataChanged(Menu menu, int operation);
    }
}
