package com.happydinner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happydinner.base.BaseActivity;
import com.happydinner.common.list.SimpleListWorkerAdapter;
import com.happydinner.entitiy.Menu;
import com.happydinner.entitiy.Order;
import com.happydinner.ui.listworker.MenuListWorker;
import com.happydinner.ui.widget.HeadView;
import com.happydinner.util.CommonUtils;

import java.util.*;

/**
 * Created by lihb on 15/5/16.
 */
public class FoodShowActivity extends BaseActivity {
    @InjectView(R.id.menu_listview)
    ListView mMenuListview;

    private MenuListWorker mListWorker;

    private SortedMap sortedMap = new TreeMap<Integer, List<Menu>>();

    private SimpleListWorkerAdapter mListAdapter;

    private HeadView headView;
    private Order mOrder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        ButterKnife.inject(this);
        mOrder = new Order();
        mOrder.setOrderId(UUID.randomUUID().toString());
        mOrder.setMenuList(new ArrayList<Menu>());
        mOrder.setStatus(Order.OrderStatus.NOTSUBMIT);
        initView();
        initData();

    }

    private void initView() {
        headView = new HeadView(this);
        headView.h_left_tv.setText("返回");
        headView.h_title.setText("");
        headView.h_right_tv_llyt.setVisibility(View.VISIBLE);
        headView.h_right_tv.setText("确认");
        headView.h_left.setOnClickListener(mOnClickListener);
        headView.h_left_rlyt.setOnClickListener(mOnClickListener);
        headView.h_right_tv.setOnClickListener(mOnClickListener);

    }

    private void initData() {
        com.happydinner.entitiy.Menu meatMenu = new com.happydinner.entitiy.Menu("红烧肉", null, null, 15.67f, "好吃看的见－meat", 1, 0, 1);
        com.happydinner.entitiy.Menu lurouMenu = new com.happydinner.entitiy.Menu("卤肉", null, null, 14.59f, "好吃看的见－lurou", 1, 0, 1);
        com.happydinner.entitiy.Menu luosiMenu = new com.happydinner.entitiy.Menu("田螺", null, null, 18.7f, "好吃看的见－tianluo", 1, 0, 2);
        com.happydinner.entitiy.Menu fishMenu = new com.happydinner.entitiy.Menu("鱼", null, null, 29f, "好吃看的见-fish", 1, 0, 1);
        com.happydinner.entitiy.Menu chickMenu = new com.happydinner.entitiy.Menu("鸡", null, null, 20f, "好吃看的见-chick", 1, 0, 2);
        com.happydinner.entitiy.Menu duckMenu = new com.happydinner.entitiy.Menu("鸭", null, null, 19f, "好吃看的见-duck", 0.8f, 0, 3);
        List<com.happydinner.entitiy.Menu> menuList = new ArrayList<com.happydinner.entitiy.Menu>();
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
            if(newKey == oldKey) {
                tmpList.add(menuItemData);
            }else {
                sortedMap.put(oldKey, tmpList);
                tmpList = new ArrayList<Menu>();
                tmpList.add(menuItemData);
                oldKey = newKey;
            }
        }
        sortedMap.put(oldKey, tmpList); // 处理最后一组数据

        if (mListWorker == null) {
            mListWorker = new MenuListWorker(FoodShowActivity.this, sortedMap, new MenuListWorker.OnListWorkerListener(){
                @Override
                public void onItemClicked(Object itemData, int index) {
                    com.happydinner.entitiy.Menu menu = (com.happydinner.entitiy.Menu) itemData;
                    Toast.makeText(FoodShowActivity.this, "你点击了：" + menu.getName(),
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAddMenuClicked(Object itemData) {
                    mListAdapter.notifyDataSetChanged();

                }

                @Override
                public void onSubMenuClicked(Object itemData) {
                    mListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onAddToOrderClicked(Object itemData) {
                    Menu menu = (Menu) itemData;
                    mOrder.addMenu(menu);
                    CommonUtils.toastText(FoodShowActivity.this, "总价:" + mOrder.getTotalPrice());
                    mListAdapter.notifyDataSetChanged();

                }
            });
            mListAdapter = new SimpleListWorkerAdapter(mListWorker);
            mMenuListview.setAdapter(mListAdapter);
            // ListView的 ItemClick 由 ListWorker 转发
            mMenuListview.setOnItemClickListener(mListWorker);
        } else {
            mListWorker.setData(sortedMap);
            mListAdapter.notifyDataSetChanged();
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.head_left:
                case R.id.head_left_rlyt:
                    finish();
                    break;
                case R.id.head_right_tv:
                case R.id.head_right_tv_layout:
                    Intent intent = new Intent(FoodShowActivity.this, OrderShowActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("orderData", mOrder);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };



}
