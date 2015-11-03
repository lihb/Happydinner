package com.handgold.pjdc.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.handgold.pjdc.R;
import com.handgold.pjdc.base.ApplicationEx;
import com.handgold.pjdc.base.GameTypeEnum;
import com.handgold.pjdc.entitiy.GameInfo;
import com.handgold.pjdc.ui.Game.GameLeftFragment;
import com.handgold.pjdc.ui.Game.GameRightFragment;
import com.handgold.pjdc.ui.widget.HeadView;

import java.util.*;

/**
 * Created by Administrator on 2015/11/3.
 */
public class GameShowActivity extends FragmentActivity {

    private HeadView headView;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;

    private GameLeftFragment gameLeftFragment = null;

    private GameRightFragment gameRightFragment = null;

    private SortedMap<Integer, List<GameInfo>> sortedDataMap;

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

        // 获取游戏数据
        sortedDataMap = (SortedMap) ((ApplicationEx) getApplication()).receiveInternalActivityParam("allGameList");
        if (sortedDataMap == null) {
            sortedDataMap = new TreeMap<Integer, List<GameInfo>>();
            initData();
        }
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

    private void initData() {
        List<GameInfo> gameInfoList = new ArrayList<GameInfo>();
        for (int i = 0; i < 30; i++) {
            GameInfo gameInfo = new GameInfo("游戏" + (i+1), "http://www.dddd.com", (i / 6 + 1));
            gameInfoList.add(gameInfo);

        }
        /**
         * 比较器：给menu按照type排序用
         */
        Comparator<GameInfo> comparator = new Comparator<GameInfo>() {
            @Override
            public int compare(GameInfo lhs, GameInfo rhs) {

                return lhs.getType() - rhs.getType();
            }
        };
        Collections.sort(gameInfoList, comparator);

        List<GameInfo> tmpList = new ArrayList<GameInfo>();

        int oldKey = gameInfoList.get(0).getType();

        for (int i = 0; i < gameInfoList.size(); i++) {
            GameInfo gameItemData = gameInfoList.get(i);
            int newKey = gameItemData.getType();
            if (newKey == oldKey) {
                tmpList.add(gameItemData);
            } else {
                sortedDataMap.put(oldKey, tmpList);
                tmpList = new ArrayList<GameInfo>();
                tmpList.add(gameItemData);
                oldKey = newKey;
            }
        }
        sortedDataMap.put(oldKey, tmpList); // 处理最后一组数据

        ((ApplicationEx) getApplication()).setInternalActivityParam("allGameList", sortedDataMap);
    }

    private void initFragment() {
        mTransaction = mFragmentManager.beginTransaction();
        if (gameLeftFragment == null) {
            gameLeftFragment = new GameLeftFragment();
            mTransaction.replace(R.id.left_frag, gameLeftFragment, "game_left_fragment");

        }

        if (gameRightFragment == null) {
            Bundle bundle = new Bundle();
            ArrayList<GameInfo> dataList = new ArrayList<GameInfo>();
            //初始化右边fragment的数据
            dataList.addAll(sortedDataMap.get(GameTypeEnum.COOLRUN.ordinal()));
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
