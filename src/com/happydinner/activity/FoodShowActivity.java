package com.happydinner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happydinner.base.ApplicationEx;
import com.happydinner.common.list.SimpleListWorkerAdapter;
import com.happydinner.entitiy.Menu;
import com.happydinner.entitiy.Order;
import com.happydinner.ui.VideoPlayerFragment;
import com.happydinner.ui.listworker.MenuListWorker;
import com.happydinner.ui.widget.HeadView;
import com.happydinner.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.UUID;

/**
 * Created by lihb on 15/5/16.
 */
public class FoodShowActivity extends FragmentActivity {
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

    @InjectView(R.id.desc_menu_count_sub_tv)
    TextView descMenuCountSubTv;

    @InjectView(R.id.desc_menu_count_confirm_tv)
    TextView descMenuCountConfirmTv;

    @InjectView(R.id.desc_menu_count_add_tv)
    TextView descMenuCountAddTv;

    @InjectView(R.id.desc_menu_count_ll)
    LinearLayout descMenuCountLl;

    @InjectView(R.id.video_info_tv)
    TextView videoInfoTv;

    private MenuListWorker mListWorker;

    private SortedMap<Integer, List<Menu>> sortedMap;

    private SimpleListWorkerAdapter mListAdapter;

    private HeadView headView;

    private Order mOrder;

    private View mExpandedView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    VideoPlayerFragment videoPlayerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 去除title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //去除title和状态栏的操作必须在 super.onCreate()方法之前
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        ButterKnife.inject(this);
        fragmentManager = getSupportFragmentManager();
        // 初始化界面
        initView();

        // 获取菜品数据
        sortedMap = (SortedMap) ((ApplicationEx) getApplication()).receiveInternalActivityParam("allMenuList");
       /* if (sortedMap == null) {
            sortedMap = new TreeMap<Integer, List<Menu>>();
            initData();
        }*/

        //获取订单数据
        mOrder = (Order) ((ApplicationEx) getApplication()).receiveInternalActivityParam("order");
        if (mOrder == null) {
            mOrder = new Order();
            mOrder.setOrderId(UUID.randomUUID().toString());
            mOrder.setMenuList(new ArrayList<Menu>());
            mOrder.setStatus(Order.OrderStatus.NOTSUBMIT);
            ((ApplicationEx) getApplication()).setInternalActivityParam("order", mOrder);
        }
        // 配置listworker
        if (mListWorker == null) {
            mListWorker = new MenuListWorker(FoodShowActivity.this, sortedMap, new MenuListWorkerCallBack());
            mListAdapter = new SimpleListWorkerAdapter(mListWorker);
            mMenuListview.setAdapter(mListAdapter);
            // ListView的 ItemClick 由 ListWorker 转发
            mMenuListview.setOnItemClickListener(mListWorker);
        } else {
            mListWorker.setData(sortedMap);
            mListAdapter.notifyDataSetChanged();
        }

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


    private View.OnClickListener mOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.head_left:
                case R.id.head_left_rlyt: // 如果详情页面打开，则按返回按钮是关闭详情页面
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
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 返回按钮的功能，如果详情页面打开，则按返回键是关闭详情页面
     */
    @Override
    public void onBackPressed() {
        if (mExpandedView != null) {
            mExpandedView.setVisibility(View.GONE);
            videoPlayerFragment.toggeleUIShow(true);
            videoPlayerFragment = null;
            mExpandedView = null;
        } else {
            super.onBackPressed();
        }

    }
    /**
     * 详情菜单页面
     *
     * @param menu 哪个菜品的详情
     */
    private void zoomViewFromMain(final Menu menu) {

        // Load the high-resolution "zoomed-in" image.
        mExpandedView = findViewById(R.id.menu_desc_rl);
        menuTitleTv.setText(menu.getName());
        menuImageIv.setImageResource(R.drawable.image_2);
        descMenuCountConfirmTv.setText("" + (menu.count));
        menuDescTv.setText("菜品简介:" + menu.getInfo());

        menu.setVideoUrl("http://download.cloud.189.cn/v5/downloadFile.action?downloadRequest=1_266BEB5F2F53474145C6EBE33E9A75D592251F2581CFE66ED934BC80674F070BA6790DA91C37DD2867779B6A435B6E040ED7928D6EFEB456A463C8E6238E8DA431473E7443FCC8025B64223A6700BF64EDD9FFDFEEA7447A59FC024F4CE7979319CFCCF6F79641E0E10945F7D23B60F7557901BF94E0BF88DFACD44EF40A4A4D0E77B882");

        descMenuCountAddTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mOrder.addMenu(menu);
                descMenuCountConfirmTv.setText("" + (menu.count));
            }
        });

        descMenuCountSubTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mOrder.delMenu(menu);
                int count = menu.count;
                if (count <= 0) {
                    descMenuCountConfirmTv.setText("" + 0);
                } else {
                    descMenuCountConfirmTv.setText("" + count);
                }
            }
        });

        videoInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodShowActivity.this, VideoPlayer2Activity.class);
                intent.putExtra("fileName", menu.getName());
                intent.putExtra("videoUrl", menu.getVideoUrl());
                startActivity(intent);

            }
        });

        fragmentTransaction = fragmentManager.beginTransaction();
        videoPlayerFragment = (VideoPlayerFragment) fragmentManager.findFragmentByTag("videoPlayerFragment");
        if (videoPlayerFragment == null) {
            videoPlayerFragment = new VideoPlayerFragment();
            Bundle args = new Bundle();
            args.putString("videoUrl", menu.getVideoUrl());
            videoPlayerFragment.setArguments(args);
        }

        fragmentTransaction.replace(R.id.menu_video_frag_ll, videoPlayerFragment, "videoPlayerFragment");
        fragmentTransaction.commit();

        // thumbView.setAlpha(0f); //不透明消失
        // 显示详情菜单，右侧确认按钮隐藏，只显示返回按钮
        mExpandedView.setVisibility(View.VISIBLE);
        headView.h_right_tv.setVisibility(View.GONE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations to the top-left corner of
        // the zoomed-in view (the default is the center of the view).
        mExpandedView.setPivotX(0f);
        mExpandedView.setPivotY(0f);

    }

    /**
     * MenuListWorker回调类
     */
    class MenuListWorkerCallBack implements MenuListWorker.OnListWorkerListener {

        @Override
        public void onItemClicked(Object itemData, int index) {
            Menu menu = (Menu) itemData;
            Toast.makeText(FoodShowActivity.this, "你点击了：" + menu.getName(), Toast.LENGTH_SHORT).show();
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
    }

}
