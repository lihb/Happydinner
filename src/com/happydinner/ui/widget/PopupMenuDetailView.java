package com.happydinner.ui.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.*;
import com.happydinner.R;
import com.happydinner.entitiy.Menu;
import com.happydinner.ui.Controller.VideoController;

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

    private VideoView mVideoView = null;

    private FrameLayout mPopupVideoViewFramelayout = null;

    private VideoController mVideoController = null;

    private boolean showPlayIcon = true;

    private Menu mMenuData = null;

    private int mNum = 0;

    public static final int OPERATION_ADD = 1;
    public static final int OPERATION_SUB = 2;

    public int curOperation = 0;

    private int mCurrPos = 0;

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

        mVideoView = (VideoView) findViewById(R.id.popup_videoview);

        mPopupVideoViewFramelayout = (FrameLayout) findViewById(R.id.popup_videoview_framelayout);

//        mVideoImage.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.image_4), 8, RoundedImageView.CORNER_BOTTOM_LEFT);

        mVideoImage.setOnClickListener(mOnClickListener);
        mCloseImage.setOnClickListener(mOnClickListener);
        mPlayImage.setOnClickListener(mOnClickListener);
        mAddImage.setOnClickListener(mOnClickListener);
        mSubImage.setOnClickListener(mOnClickListener);
        mPopupVideoViewFramelayout.setOnClickListener(mOnClickListener);
    }

    private VideoController.OnVideoControllerListener mOnVideoControllerListener = new VideoController.OnVideoControllerListener() {
        @Override
        public void onCompletion() {
            showPlayIcon = true;
            togglePlayVideo();
        }

        @Override
        public void onError() {
            if (mVideoController != null) {
                mVideoController.stop();
            }
            showPlayIcon = true;
            togglePlayVideo();
        }

        @Override
        public void onPause(int pos) {
            showPlayIcon = true;
            togglePlayVideo();
            mCurrPos = pos;
        }

        @Override
        public void onStop() {
            showPlayIcon = true;
            togglePlayVideo();
        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v == mPlayImage || v == mVideoImage) {
                showPlayIcon = false;
                togglePlayVideo();
                if (mVideoController == null) {
                    mVideoController = new VideoController();
                    mVideoController.setVideoView(mVideoView);
                    Uri uri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.produce);
//                    Uri uri2 = Uri.parse(mMenuData.getVideoUrl());
                    mVideoController.setUri(uri);
                    mVideoController.setOnVideoControllerListener(mOnVideoControllerListener);
                    mVideoController.play(0);
                } else {
                    mVideoController.play(mCurrPos);
                }
            } else if (v == mCloseImage) {
                exitView();
            } else if (v == mPopupVideoViewFramelayout) {
                if (mVideoController != null) {
                    mVideoController.pause();
                }
            } else if (v == mSubImage) {
                mNum--;
                curOperation = OPERATION_SUB;
                showSubOper();
            } else {
                mNum++;
                curOperation = OPERATION_ADD;
                showSubOper();
            }
        }
    };

    /**
     * 切换显示视频播放的UI
     */
    private void togglePlayVideo() {
        mPlayImage.setVisibility(showPlayIcon ? VISIBLE : GONE);
        mVideoImage.setVisibility(showPlayIcon ? VISIBLE : GONE);
        mPopupVideoViewFramelayout.setVisibility(showPlayIcon ? GONE : VISIBLE);
    }

    /**
     * 退出该View
     */
    public void exitView() {
        if (mOnPopDetailViewListener != null) {
            if (curOperation == OPERATION_ADD) {
                mNum = mNum - 1;
            } else if (curOperation == OPERATION_SUB) {
                mNum = mNum + 1;
            }
            mMenuData.setCount(mNum);
            mOnPopDetailViewListener.onDataChanged(mMenuData, curOperation);
        }
        mNum = 0;
        curOperation = 0;
        if (mVideoController != null) {
            mVideoController.stop();
            // 修复从别的activity返回到该界面的activity时，视频会再次播放的bug
            mVideoView.setVideoURI(null);
            mVideoController = null;
        }
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,0.0f,1.0f,0.0f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(400);
        startAnimation(scaleAnimation);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((ViewGroup) getParent()).setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    /**
     * 初始化数据
     *
     * @param menu
     */
    public void initData(Menu menu) {
        mMenuData = menu;
        mNum = menu.count;
        mMenuName.setText(menu.getName());
        mMenuPrice.setText("" + menu.getPrice());
        mMenuRestaurant.setText(menu.getRestaurantName());
        mMenuDesc.setText(menu.getInfo());
        showSubOper();
    }

    /**
     * 是否显示减号操作栏
     */
    private void showSubOper() {
        if (mNum > 0) {
            mPopOperationLayout.setBackgroundResource(R.drawable.menu_count_view_bg);
            mSubImage.setVisibility(VISIBLE);
            mMenuCount.setVisibility(VISIBLE);
            mMenuCount.setText("" + mNum);
        } else {
            mPopOperationLayout.setBackground(null);
            mSubImage.setVisibility(GONE);
            mMenuCount.setVisibility(GONE);
        }
    }

    /**
     * 判断该View是否正在显示
     *
     * @return
     */
    public boolean isVisible() {
        return (((ViewGroup) getParent()).getVisibility() == VISIBLE);
    }


    private OnPopDetailViewListener mOnPopDetailViewListener = null;

    public void setOnPopDetailViewListener(OnPopDetailViewListener onPopDetailViewListener) {
        this.mOnPopDetailViewListener = onPopDetailViewListener;
    }

    /**
     * 回调接口
     */
    public interface OnPopDetailViewListener {
        void onDataChanged(Menu menu, int operation);
    }
}
