package com.happydinner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
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
    @InjectView(R.id.menu_title_tv)
    TextView menuTitleTv;
    @InjectView(R.id.menu_image_iv)
    ImageView menuImageIv;
    @InjectView(R.id.menu_desc_tv)
    TextView menuDescTv;
    @InjectView(R.id.menu_add_to_order_tv_desc)
    TextView menuAddToOrderTvDesc;
    @InjectView(R.id.menu_desc_rl)
    RelativeLayout menuDescRl;

    private MenuListWorker mListWorker;

    private SortedMap sortedMap = new TreeMap<Integer, List<Menu>>();

    private SimpleListWorkerAdapter mListAdapter;

    private HeadView headView;
    private Order mOrder;

    private View mExpandedView;

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
        Menu meatMenu = new Menu("红烧肉", null, null, 15.67f, "好吃看的见－meat", 1, 0, 1);
        Menu lurouMenu = new Menu("卤肉", null, null, 14.59f, "好吃看的见－lurou", 1, 0, 1);
        Menu luosiMenu = new Menu("田螺", null, null, 18.7f, "好吃看的见－tianluo", 1, 0, 2);
        Menu fishMenu = new Menu("鱼", null, null, 29f, "好吃看的见-fish", 1, 0, 1);
        Menu chickMenu = new Menu("鸡", null, null, 20f, "好吃看的见-chick", 1, 0, 2);
        Menu duckMenu = new Menu("鸭", null, null, 19f, "好吃看的见-duck", 0.8f, 0, 3);
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

        if (mListWorker == null) {
            mListWorker = new MenuListWorker(FoodShowActivity.this, sortedMap, new MenuListWorker.OnListWorkerListener() {
                @Override
                public void onItemClicked(Object itemData, int index) {
                    Menu menu = (Menu) itemData;
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

                @Override
                public void onGotoLookDesc(View view, Object itemData) {
                    Menu menu = (Menu) itemData;
                    zoomViewFromMain(menu);

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
            switch (v.getId()) {
                case R.id.head_left:
                case R.id.head_left_rlyt:
                    if (mExpandedView != null) {
                        mExpandedView.setVisibility(View.GONE);
                        mExpandedView = null;
                        headView.h_right_tv.setVisibility(View.VISIBLE);
                    } else {
                        finish();
                    }
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

    @Override
    public void onBackPressed() {
        if (mExpandedView != null) {
            mExpandedView.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }

    }

    private void zoomViewFromMain(final Menu menu) {

        // Load the high-resolution "zoomed-in" image.
        mExpandedView = findViewById(R.id.menu_desc_rl);
        menuTitleTv.setText(menu.getName());
        menuImageIv.setImageResource(R.drawable.image_2);
        menuDescTv.setText(menu.getInfo());
        menuAddToOrderTvDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrder.addMenu(menu);
                CommonUtils.toastText(FoodShowActivity.this, "总价:" + mOrder.getTotalPrice());
            }
        });

//        thumbView.setAlpha(0f); //不透明消失
        //显示详情菜单，右侧确认按钮隐藏，只显示返回按钮
        mExpandedView.setVisibility(View.VISIBLE);
        headView.h_right_tv.setVisibility(View.GONE);


        // Set the pivot point for SCALE_X and SCALE_Y transformations to the top-left corner of
        // the zoomed-in view (the default is the center of the view).
        mExpandedView.setPivotX(0f);
        mExpandedView.setPivotY(0f);

    }


}
