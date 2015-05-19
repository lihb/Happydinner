package com.happydinner.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.*;
import com.happydinner.common.list.SimpleListWorkerAdapter;
import com.happydinner.entitiy.GameEntity;
import com.happydinner.ui.CoverFlowAdapter;
import com.happydinner.ui.listworker.MenuListWorker;
import com.happydinner.ui.widget.HeadView;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

import java.util.*;


public class CoverFlowActivity extends ActionBarActivity {

    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    private ArrayList<GameEntity> mData = new ArrayList<GameEntity>(0);
    private TextSwitcher mTitle;

    /**
     * Hold a reference to the current animator, so that it can be canceled mid-way.
     */
    private Animator mCurrentAnimator;

    ListView mMenuListview;

    private MenuListWorker mListWorker;


    private SortedMap sortedMap = new TreeMap<Integer, List<com.happydinner.entitiy.Menu>>();

    private SimpleListWorkerAdapter mListAdapter;

    private HeadView headView;

    private View mExpandedView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
//        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
//        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_coverflow);

        mData.add(new GameEntity(R.drawable.menu, R.string.title1));
        mData.add(new GameEntity(R.drawable.image_2, R.string.title2));
        mData.add(new GameEntity(R.drawable.image_3, R.string.title3));
        mData.add(new GameEntity(R.drawable.image_4, R.string.title4));

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
                /*Toast.makeText(CoverFlowActivity.this,
                        getResources().getString(mData.get(index).titleResId) +", index = " + index,
                        Toast.LENGTH_SHORT).show();*/
                if (index == 0) {
                    /*Intent intent = new Intent(CoverFlowActivity.this, FoodShowActivity.class);
                    startActivity(intent);*/
                    zoomViewFromMain(view);
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

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.head_left:
                case R.id.head_left_rlyt:
                    mExpandedView.setVisibility(View.GONE);
                    break;
                case R.id.head_right_tv:
                case R.id.head_right_tv_layout:
                    //todo 跳转到自主下单activity
                    Toast.makeText(CoverFlowActivity.this,
                            "开发中，尽请期待。。",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    private void initData() {
        final com.happydinner.entitiy.Menu meatMenu = new com.happydinner.entitiy.Menu("红烧肉", null, null, 15.6f, "好吃看的见－meat", 1, false, 1);
        com.happydinner.entitiy.Menu lurouMenu = new com.happydinner.entitiy.Menu("卤肉", null, null, 14.5f, "好吃看的见－lurou", 1, false, 1);
        com.happydinner.entitiy.Menu luosiMenu = new com.happydinner.entitiy.Menu("田螺", null, null, 18.7f, "好吃看的见－tianluo", 1, false, 2);
        com.happydinner.entitiy.Menu fishMenu = new com.happydinner.entitiy.Menu("鱼", null, null, 29f, "好吃看的见-fish", 1, false, 1);
        com.happydinner.entitiy.Menu chickMenu = new com.happydinner.entitiy.Menu("鸡", null, null, 20f, "好吃看的见-chick", 1, false, 2);
        com.happydinner.entitiy.Menu duckMenu = new com.happydinner.entitiy.Menu("鸭", null, null, 19f, "好吃看的见-duck", 0.8f, false, 3);
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
        Comparator<com.happydinner.entitiy.Menu> comparator = new Comparator<com.happydinner.entitiy.Menu>() {
            @Override
            public int compare(com.happydinner.entitiy.Menu lhs, com.happydinner.entitiy.Menu rhs) {

                return lhs.getType() - rhs.getType();
            }
        };
        Collections.sort(menuList, comparator);

        List<com.happydinner.entitiy.Menu> tmpList = new ArrayList<com.happydinner.entitiy.Menu>();

        int oldKey = menuList.get(0).getType();

        for (int i = 0; i < menuList.size(); i++) {
            com.happydinner.entitiy.Menu menuItemData = menuList.get(i);
            int newKey = menuItemData.getType();
            if(newKey == oldKey) {
                tmpList.add(menuItemData);
            }else {
                sortedMap.put(oldKey, tmpList);
                tmpList = new ArrayList<com.happydinner.entitiy.Menu>();
                tmpList.add(menuItemData);
                oldKey = newKey;
            }
        }
        sortedMap.put(oldKey, tmpList); // 处理最后一组数据

        if (mListWorker == null) {
            mListWorker = new MenuListWorker(CoverFlowActivity.this, sortedMap, new MenuListWorker.OnListWorkerListener(){
                @Override
                public void onItemClicked(Object itemData, int index) {
                    com.happydinner.entitiy.Menu menu = (com.happydinner.entitiy.Menu) itemData;
                    Toast.makeText(CoverFlowActivity.this, "你点击了："+ menu.getName(),
                            Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onAddMenuClicked(Object itemData) {
                    /*com.happydinner.entitiy.Menu menu = (com.happydinner.entitiy.Menu) itemData;
                    menu.count++;*/
                    mListAdapter.notifyDataSetChanged();

                }

                @Override
                public void onSubMenuClicked(Object itemData) {
                    /*com.happydinner.entitiy.Menu menu = (com.happydinner.entitiy.Menu) itemData;
                    menu.count--;
                    int count = menu.count;
                    if (count <= 0) {
                        menu.count = 0;
                    }*/
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coverflow_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void zoomViewFromMain(final View mainView) {
        // If there's an animation in progress, cancel it immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        mExpandedView = findViewById(R.id.menu_rl);
//        mExpandedView.setImageResource(imageResId);
        mMenuListview = (ListView) mExpandedView.findViewById(R.id.menu_listview);
        initView();
        initData();

        // Calculate the starting and ending bounds for the zoomed-in image. This step
        // involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail, and the
        // final bounds are the global visible rectangle of the container view. Also
        // set the container view's offset as the origin for the bounds, since that's
        // the origin for the positioning animation properties (X, Y).
        mainView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.coverflow_rl).getGlobalVisibleRect(finalBounds, globalOffset);
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
        mainView.setAlpha(0f);
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
                        mainView.setAlpha(1f);
                        mExpandedView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        mainView.setAlpha(1f);
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
