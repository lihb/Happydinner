package com.happydinner.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
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
    @InjectView(R.id.menu_desc_rl)
    RelativeLayout menuDescRl;

    private MenuListWorker mListWorker;

    private SortedMap sortedMap = new TreeMap<Integer, List<Menu>>();

    private SimpleListWorkerAdapter mListAdapter;

    private HeadView headView;
    private Order mOrder;

    /**
     * Hold a reference to the current animator, so that it can be canceled mid-way.
     */
    private Animator mCurrentAnimator;
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
                    zoomViewFromMain(view, menu);

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

    private void zoomViewFromMain(final View thumbView, Menu menu) {
        // If there's an animation in progress, cancel it immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        mExpandedView = findViewById(R.id.menu_desc_rl);
        menuTitleTv.setText(menu.getName());
        menuImageIv.setImageResource(R.drawable.image_2);
        menuDescTv.setText(menu.getInfo());

        // Calculate the starting and ending bounds for the zoomed-in image. This step
        // involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail, and the
        // final bounds are the global visible rectangle of the container view. Also
        // set the container view's offset as the origin for the bounds, since that's
        // the origin for the positioning animation properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.menu_activity).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final bounds using the
        // "center crop" technique. This prevents undesirable stretching during the animation.
        // Also calculate the start scaling factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation begins,
        // it will position the zoomed-in view in the place of the thumbnail.
        thumbView.setAlpha(0f);
        mExpandedView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations to the top-left corner of
        // the zoomed-in view (the default is the center of the view).
        mExpandedView.setPivotX(0f);
        mExpandedView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and scale properties
        // (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(mExpandedView, View.X, startBounds.left,
                        finalBounds.left))
                .with(ObjectAnimator.ofFloat(mExpandedView, View.Y, startBounds.top,
                        finalBounds.top))
                .with(ObjectAnimator.ofFloat(mExpandedView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(mExpandedView, View.SCALE_Y, startScale, 1f));
        set.setDuration(200);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down to the original bounds
        // and show the thumbnail instead of the expanded image.
        final float startScaleFinal = startScale;
        mExpandedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel, back to their
                // original values.
                AnimatorSet set = new AnimatorSet();
                set
                        .play(ObjectAnimator.ofFloat(mExpandedView, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(mExpandedView, View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(mExpandedView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(mExpandedView, View.SCALE_Y, startScaleFinal));
                set.setDuration(200);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        mExpandedView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        mExpandedView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }


}
