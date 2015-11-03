package com.handgold.pjdc.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.handgold.pjdc.R;
import com.handgold.pjdc.entitiy.Menu;
import com.handgold.pjdc.ui.Game.GameLeftFragment;
import com.handgold.pjdc.ui.Game.GameRightFragment;
import com.handgold.pjdc.ui.widget.HeadView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/3.
 */
public class GameShowActivity extends FragmentActivity {

    private HeadView headView;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;

    private GameLeftFragment gameLeftFragment = null;

    private GameRightFragment gameRightFragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 去除title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //去除title和状态栏的操作必须在 super.onCreate()方法之前
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_food_pay);

        mFragmentManager = getSupportFragmentManager();
        // 初始化界面
        initView();

        initFragment();

    }

    private void initView() {
        headView = new HeadView(this);
        headView.h_left_tv.setText("返回");
        headView.h_title.setVisibility(View.GONE);
        headView.h_title_img.setVisibility(View.VISIBLE);
        headView.h_right_tv_llyt.setVisibility(View.GONE);
        headView.h_right_tv.setText("");
        headView.h_right_tv.setTextColor(0x33ffffff);
        headView.h_left.setOnClickListener(mOnClickListener);
        headView.h_left_rlyt.setOnClickListener(mOnClickListener);
//        headView.h_right_tv.setOnClickListener(mOnClickListener);

    }

    private void initFragment() {
        mTransaction = mFragmentManager.beginTransaction();
        if (gameLeftFragment == null) {
            gameLeftFragment = new GameLeftFragment();
            mTransaction.replace(R.id.left_frag, gameLeftFragment, "game_left_fragment");

        }

        if (gameRightFragment == null) {
            Bundle bundle = new Bundle();
            ArrayList<Menu> dataList = new ArrayList<Menu>();
            //初始化右边fragment的数据
//            dataList.addAll(sortedMap.get(MenuTypeEnum.RECOMMEND.ordinal()));
            bundle.putParcelableArrayList("dataList", dataList);
            gameRightFragment = new GameRightFragment();
            gameRightFragment.setArguments(bundle);
            mTransaction.replace(R.id.right_frag, gameRightFragment, "game_right_fragment");
        }

        mTransaction.commit();

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.head_left:
                case R.id.head_left_rlyt:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
}
