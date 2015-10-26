package com.handgold.pjdc.ui.Pay;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import android.view.animation.*;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.handgold.pjdc.R;
import com.handgold.pjdc.base.ApplicationEx;
import com.handgold.pjdc.common.list.SimpleListWorkerAdapter;
import com.handgold.pjdc.entitiy.Menu;
import com.handgold.pjdc.entitiy.Order;
import com.handgold.pjdc.ui.listworker.MenuListWorker;
import com.handgold.pjdc.ui.widget.OrderShowView;
import com.handgold.pjdc.ui.widget.PopupMenuDetailView;
import com.handgold.pjdc.ui.widget.ShoppingCartView;
import com.handgold.pjdc.util.CommonUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

/**
 * 类说明：
 *
 * @author Administrator
 * @version 1.0
 * @date 2015/6/16
 */

public class FoodRightFragment extends Fragment {

    private ListView mListView;

    private ShoppingCartView mShoppingCardView;

    private OrderShowView mOrderShowView;

    private RelativeLayout mOrderShowViewRelativeLayout;

    private PopupMenuDetailView mPopupMenuDetailView;

    private RelativeLayout mPopupMenuDetailViewRelativeLayout;

    private ArrayList<Menu> mDataList;

    private MenuListWorker mListWorker;

    private SimpleListWorkerAdapter mListAdapter;

    private Order mOrder;

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int[] location = new int[2];
                View subView = ((ViewGroup) v).getChildAt(0);
                subView.getLocationInWindow(location);
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (x < location[0] || x > location[0] + subView.getWidth() ||
                        y < location[1] || y > location[1] + subView.getHeight()) {
                    if (subView instanceof OrderShowView) {
                        ((OrderShowView)subView).exitView();
                    }else if(subView instanceof PopupMenuDetailView){
                        ((PopupMenuDetailView)subView).exitView();
                    }
                }
            }
            return true;
        }
    };

    private int number = 0;
    //是否完成清理
    private boolean isClean = false;
    private FrameLayout animation_viewGroup;
    private Handler myHandler = new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case 0:
                    //用来清除动画后留下的垃圾
                    try{
                        animation_viewGroup.removeAllViews();
                    }catch(Exception e){

                    }

                    isClean = false;

                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.food_right_fragment, null);
        mListView = (ListView) view.findViewById(R.id.food_right_lv);
        mShoppingCardView = (ShoppingCartView) view.findViewById(R.id.shopping_cart_view);

        mOrderShowView = (OrderShowView) getActivity().findViewById(R.id.order_show_view);
        mOrderShowViewRelativeLayout = (RelativeLayout) getActivity().findViewById(R.id.order_show_view_relativeLayout);
        mOrderShowView.setActivity(getActivity());
        mOrderShowView.initData();

        mPopupMenuDetailView = (PopupMenuDetailView) getActivity().findViewById(R.id.popup_detail_view);
        mPopupMenuDetailViewRelativeLayout = (RelativeLayout) getActivity().findViewById(R.id.popup_view_relativeLayout);

        mShoppingCardView.setOnClickListener(mOnClickListener);
        mOrderShowViewRelativeLayout.setOnTouchListener(mOnTouchListener);
        mPopupMenuDetailViewRelativeLayout.setOnTouchListener(mOnTouchListener);

        mOrderShowView.setOnOrderViewListener(new OrderShowView.OnOrderViewListener() {
            @Override
            public void onBackToFoodFrag(Order order) {
                if (mListWorker != null) {
                    mListWorker.setData(mDataList);
                    mListAdapter.notifyDataSetChanged();
                }
                mShoppingCardView.setTextCount(order.getSize());
                mShoppingCardView.setTextPrice(CommonUtils.round(order.getTotalPrice(), 1, BigDecimal.ROUND_HALF_UP));
            }
        });
        animation_viewGroup = createAnimLayout();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        mDataList = bundle.getParcelableArrayList("dataList");
        // 配置listworker
        if (mListWorker == null) {
            mListWorker = new MenuListWorker(getActivity(), mDataList, new MenuListWorkerCallBack());
            mListAdapter = new SimpleListWorkerAdapter(mListWorker);
            mListView.setAdapter(mListAdapter);
            // ListView的 ItemClick 由 ListWorker 转发
            mListView.setOnItemClickListener(mListWorker);
        } else {
            mListWorker.setData(mDataList);
            mListAdapter.notifyDataSetChanged();
        }

        //获取订单数据
        mOrder = (Order) ((ApplicationEx)(getActivity()).getApplication()).receiveInternalActivityParam("order");
        if (mOrder == null) {
            mOrder = new Order();
            mOrder.setOrderId(UUID.randomUUID().toString());
            mOrder.setMenuList(new ArrayList<Menu>());
            mOrder.setStatus(Order.OrderStatus.NOTSUBMIT);
            ((ApplicationEx) (getActivity()).getApplication()).setInternalActivityParam("order", mOrder);
        }
        updateView();
    }


   private View.OnClickListener mOnClickListener = new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           if (v == mShoppingCardView) {
               if (mOrder.getSize() <= 0) {
                   ObjectAnimator animator = ObjectAnimator.ofFloat(mShoppingCardView, "translationX", 0, 30, -30, 30, -30,20, -20, 10, -10, 0);
                   animator.setDuration(500);
                   animator.start();
                   CommonUtils.toastText(getActivity(), "购物车是空的哦~");
               }else {
                   mOrderShowViewRelativeLayout.setVisibility(View.VISIBLE);
                   mOrderShowView.setData(mOrder.getMenuList());
                   mOrderShowView.startAnimation(orderShowViewAnimation());
               }
           }
       }
   };

    private Animation orderShowViewAnimation() {

        AnimationSet animationSet = new AnimationSet(false);
        TranslateAnimation translateAnimation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, 1.0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 1.0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.0f);
//        translateAnimation.setInterpolator(new OvershootInterpolator(1.5f));
        animationSet.addAnimation(translateAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        animationSet.addAnimation(alphaAnimation);
//        animationSet.setFillAfter(true);
        animationSet.setDuration(400);

        return animationSet;
    }
    /**
     * MenuListWorker回调类
     */
    class MenuListWorkerCallBack implements MenuListWorker.OnListWorkerListener {

        @Override
        public void onItemClicked(Object itemData, int index) {

        }

        @Override
        public void onAddMenuClicked(Object itemData, View v) {
            Menu menu = (Menu) itemData;
            mOrder.addMenu(menu);
            int[] startLocation = new int[2];
            v.getLocationInWindow(startLocation);
            doAnim(startLocation);
            updateView();
        }

        @Override
        public void onSubMenuClicked(Object itemData) {
            Menu menu = (Menu) itemData;
            mOrder.delMenu(menu);
            updateView();
        }

        @Override
        public void onAddToOrderClicked(Object itemData) {

        }

        @Override
        public void onGotoLookDesc(View view, Object itemData) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f,ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(300);
            mPopupMenuDetailView.startAnimation(scaleAnimation);
            mPopupMenuDetailViewRelativeLayout.setVisibility(View.VISIBLE);
            Menu menu = (Menu) itemData;
            mPopupMenuDetailView.initData(menu);
            mPopupMenuDetailView.setOnPopDetailViewListener(new PopupMenuDetailView.OnPopDetailViewListener() {
                @Override
                public void onDataChanged(Menu menu, int operation) {
                    if (operation == PopupMenuDetailView.OPERATION_ADD) {
                        mOrder.addMenu(menu);
                    }else if (operation == PopupMenuDetailView.OPERATION_SUB){
                        mOrder.delMenu(menu);
                    }
                    updateView();
                    mListAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * 更新购物车和订单页面的菜品个数和总价数据
     */
    private void updateView() {
        mShoppingCardView.setTextCount(mOrder.getSize());
        mShoppingCardView.setTextPrice(CommonUtils.round(mOrder.getTotalPrice(), 1, BigDecimal.ROUND_HALF_UP));
        mOrderShowView.setTextOrderCount(mOrder.getSize());
        mOrderShowView.setTextOrderPrice(CommonUtils.round(mOrder.getTotalPrice(), 1, BigDecimal.ROUND_HALF_UP));
    }

    /************************添加到购物车动画*******************************************/

    private void doAnim(int[] start_location){
        if(!isClean){
            setAnim(start_location);
        }else{
            try{
                animation_viewGroup.removeAllViews();
                isClean = false;
                setAnim(start_location);
            }catch(Exception e){
                e.printStackTrace();
            }
            finally{
                isClean = true;
            }
        }
    }

    /**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */
    private FrameLayout createAnimLayout(){
        ViewGroup rootView = (ViewGroup)getActivity().getWindow().getDecorView();
        FrameLayout animLayout = new FrameLayout(getActivity());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;

    }

    /**
     * @deprecated 将要执行动画的view 添加到动画层
     * @param vg
     *        动画运行的层 这里是frameLayout
     * @param view
     *        要运行动画的View
     * @param location
     *        动画的起始位置
     * @return
     */
    private View addViewToAnimLayout(ViewGroup vg,View view,int[] location){
        int x = location[0];
        int y = location[1];
        vg.addView(view);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(lp);

        return view;
    }


    /**
     * 动画效果设置
     *
     * @param start_location
     *        起始位置
     */
    private void setAnim(int[] start_location){


        Animation mScaleAnimation = new ScaleAnimation(1.5f,0.0f,1.5f,0.0f,Animation.RELATIVE_TO_SELF,0.1f,Animation.RELATIVE_TO_SELF,0.1f);
        mScaleAnimation.setFillAfter(true);


        final TextView textView = new TextView(getActivity());
        textView.setText("1");
        textView.setTextColor(0xffffffff);
        textView.setTextSize(16);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.order_count_text_bg);
        final View view = addViewToAnimLayout(animation_viewGroup, textView,start_location);
        view.setAlpha(0.6f);

        int[] end_location = new int[2];
        mShoppingCardView.getLocationOnScreen(end_location);
        int endX = end_location[0]-start_location[0] + mShoppingCardView.getWidth() / 2;
        int endY = end_location[1]-start_location[1] + mShoppingCardView.getHeight() / 2;

        Animation mTranslateAnimationX = new TranslateAnimation(0,endX,0,0);
        mTranslateAnimationX.setInterpolator(new LinearInterpolator());
        Animation mTranslateAnimationY = new TranslateAnimation(0,0,0,endY);
        mTranslateAnimationX.setInterpolator(new BounceInterpolator());
        Animation mRotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AnimationSet mAnimationSet = new AnimationSet(true);

        mAnimationSet.setFillAfter(true);
        mAnimationSet.addAnimation(mRotateAnimation);
        mAnimationSet.addAnimation(mScaleAnimation);
        mAnimationSet.addAnimation(mTranslateAnimationX);
        mAnimationSet.addAnimation(mTranslateAnimationY);
        mAnimationSet.setDuration(400);

        mAnimationSet.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                number++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub

                number--;
                if(number==0){
                    isClean = true;
                    myHandler.sendEmptyMessage(0);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

        });
        view.startAnimation(mAnimationSet);
    }
}
