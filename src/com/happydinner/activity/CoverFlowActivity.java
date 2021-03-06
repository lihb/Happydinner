package com.happydinner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.happydinner.base.ApplicationEx;
import com.happydinner.entitiy.GameEntity;
import com.happydinner.entitiy.Menu;
import com.happydinner.ui.CoverFlowAdapter;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

import java.util.*;


public class CoverFlowActivity extends ActionBarActivity {

    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    private ArrayList<GameEntity> mData = new ArrayList<GameEntity>(0);
    private TextSwitcher mTitle;
    private SortedMap<Integer, List<Menu>> sortedMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去除title
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coverflow);

        mData.add(new GameEntity(R.drawable.image_2, R.string.title2));
        mData.add(new GameEntity(R.drawable.image_3, R.string.title3));
        mData.add(new GameEntity(R.drawable.image_4, R.string.title4));
        mData.add(new GameEntity(R.drawable.menu, R.string.title1));

        mTitle = (TextSwitcher) findViewById(R.id.title);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(CoverFlowActivity.this);
                TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                return textView;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);

        mAdapter = new CoverFlowAdapter(this);
        mAdapter.setData(mData);
        mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("position===" , position+"");
                int index = position % mAdapter.getCount();
                if (index == 3) {
                    Intent intent = new Intent(CoverFlowActivity.this, FoodShowActivity.class);
                    startActivity(intent);
                } else if (index == 0) {
                    Intent intent = new Intent(CoverFlowActivity.this, VideoListActivity.class);
                    startActivity(intent);
                }

            }
        });

        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                mTitle.setText(getResources().getString(mData.get(position).titleResId));
            }

            @Override
            public void onScrolling() {
                mTitle.setText("");
            }
        });

        // 获取菜品数据
        sortedMap = (SortedMap) ((ApplicationEx) getApplication()).receiveInternalActivityParam("allMenuList");
        if (sortedMap == null) {
            sortedMap = new TreeMap<Integer, List<Menu>>();
            initData();
        }
    }

    private void initData() {
        Menu meatMenu = new Menu("红烧肉", null, null, 15.67f, "好吃看的见－meat", 1, 0, 1);
        Menu lurouMenu = new Menu("卤肉", null, null, 14.59f, "好吃看的见－lurou", 1, 0, 1);
        Menu luosiMenu = new Menu("田螺", null, null, 18.7f, "好吃看的见－tianluo", 1, 0, 2);
        Menu fishMenu = new Menu("鱼", null, null, 29f, "好吃看的见-fish", 1, 0, 1);
        Menu chickMenu = new Menu("鸡", null, null, 20f, "好吃看的见-chick", 1, 0, 2);
        Menu duckMenu = new Menu("鸭", null, null, 19f, "好吃看的见-duck", 1f, 0, 3);
        List<Menu> menuList = new ArrayList<Menu>();
        menuList.add(meatMenu);
        menuList.add(lurouMenu);
        menuList.add(luosiMenu);
        menuList.add(fishMenu);
        menuList.add(chickMenu);
        menuList.add(duckMenu);

        /**
         * 比较器：给menu按照type排序用
         */
        Comparator<Menu> comparator = new Comparator<Menu>() {
            @Override
            public int compare(Menu lhs, Menu rhs) {

                return lhs.getType() - rhs.getType();
            }
        };
        Collections.sort(menuList, comparator);

        List<Menu> tmpList = new ArrayList<Menu>();

        int oldKey = menuList.get(0).getType();

        for (int i = 0; i < menuList.size(); i++) {
            Menu menuItemData = menuList.get(i);
            int newKey = menuItemData.getType();
            if (newKey == oldKey) {
                tmpList.add(menuItemData);
            } else {
                sortedMap.put(oldKey, tmpList);
                tmpList = new ArrayList<Menu>();
                tmpList.add(menuItemData);
                oldKey = newKey;
            }
        }
        sortedMap.put(oldKey, tmpList); // 处理最后一组数据

        ((ApplicationEx) getApplication()).setInternalActivityParam("allMenuList", sortedMap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁首页时，清除全局变量
        ((ApplicationEx) getApplication()).clearInternalActivityParam();
    }
}
