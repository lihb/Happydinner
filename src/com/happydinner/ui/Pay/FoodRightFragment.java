package com.happydinner.ui.Pay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.happydinner.activity.R;
import com.happydinner.base.ApplicationEx;
import com.happydinner.common.list.SimpleListWorkerAdapter;
import com.happydinner.entitiy.Menu;
import com.happydinner.entitiy.Order;
import com.happydinner.ui.listworker.MenuListWorker;
import com.happydinner.ui.widget.OrderShowView;
import com.happydinner.ui.widget.PopupMenuDetailView;
import com.happydinner.ui.widget.ShoppingCartView;
import com.happydinner.util.CommonUtils;

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
                   CommonUtils.toastText(getActivity(), "购物车是空的哦~");
               }else {
                   mOrderShowViewRelativeLayout.setVisibility(View.VISIBLE);
                   mOrderShowView.setData(mOrder.getMenuList());
               }
           }
       }
   };
    /**
     * MenuListWorker回调类
     */
    class MenuListWorkerCallBack implements MenuListWorker.OnListWorkerListener {

        @Override
        public void onItemClicked(Object itemData, int index) {

        }

        @Override
        public void onAddMenuClicked(Object itemData) {
            Menu menu = (Menu) itemData;
            mOrder.addMenu(menu);
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
}
