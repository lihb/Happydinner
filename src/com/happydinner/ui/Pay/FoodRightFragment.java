package com.happydinner.ui.Pay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

    private ArrayList<Menu> mDataList;

    private MenuListWorker mListWorker;

    private SimpleListWorkerAdapter mListAdapter;

    private Order mOrder;

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
        mOrderShowView = (OrderShowView) view.findViewById(R.id.order_show_view);
        mOrderShowView.setActivity(getActivity());
        mOrderShowView.initData();
        mOrderShowViewRelativeLayout = (RelativeLayout) view.findViewById(R.id.order_show_view_relativeLayout);
        mShoppingCardView.setOnClickListener(mOnClickListener);
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
        mShoppingCardView.setTextCount(mOrder.getSize());
        mShoppingCardView.setTextPrice(CommonUtils.round(mOrder.getTotalPrice(), 1, BigDecimal.ROUND_HALF_UP));
        mOrderShowView.setTextOrderCount(mOrder.getSize());
        mOrderShowView.setTextOrderPrice(CommonUtils.round(mOrder.getTotalPrice(), 1, BigDecimal.ROUND_HALF_UP));
    }


   private View.OnClickListener mOnClickListener = new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           if (v == mShoppingCardView) {
               if (mOrder.getSize() <= 0) {
                   CommonUtils.toastText(getActivity(), "请至少选择一个菜品");
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
            mShoppingCardView.setTextCount(mOrder.getSize());
            mShoppingCardView.setTextPrice(CommonUtils.round(mOrder.getTotalPrice(), 1, BigDecimal.ROUND_HALF_UP));
            mOrderShowView.setTextOrderCount(mOrder.getSize());
            mOrderShowView.setTextOrderPrice(CommonUtils.round(mOrder.getTotalPrice(), 1, BigDecimal.ROUND_HALF_UP));
        }

        @Override
        public void onSubMenuClicked(Object itemData) {
            Menu menu = (Menu) itemData;
            mOrder.delMenu(menu);
            mShoppingCardView.setTextCount(mOrder.getSize());
            mShoppingCardView.setTextPrice(CommonUtils.round(mOrder.getTotalPrice(), 1, BigDecimal.ROUND_HALF_UP));
            mOrderShowView.setTextOrderCount(mOrder.getSize());
            mOrderShowView.setTextOrderPrice(CommonUtils.round(mOrder.getTotalPrice(), 1, BigDecimal.ROUND_HALF_UP));
        }

        @Override
        public void onAddToOrderClicked(Object itemData) {

        }

        @Override
        public void onGotoLookDesc(View view, Object itemData) {
//            Menu menu = (Menu) itemData;
//            zoomViewFromMain(menu);
        }
    }
}
