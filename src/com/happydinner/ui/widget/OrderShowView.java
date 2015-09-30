package com.happydinner.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.happydinner.activity.PayActivity;
import com.happydinner.activity.R;
import com.happydinner.base.ApplicationEx;
import com.happydinner.common.list.SimpleListWorkerAdapter;
import com.happydinner.entitiy.Menu;
import com.happydinner.entitiy.Order;
import com.happydinner.ui.listworker.OrderListWorker;
import com.happydinner.util.CommonUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2015/9/29.
 */
public class OrderShowView extends RelativeLayout {

    private TextView mTextOrderCount = null;

    private TextView mTextDelAll = null;

    private TextView mTextCancel = null;

    private TextView mTextOrderPrice = null;

    private TextView mTextOrderNow = null;

    private ListView mListView = null;

    private Order mOrder = null;

    private Activity mActivity = null;

    private OrderListWorker mListWorker;

    private SimpleListWorkerAdapter mListAdapter;

    private OnOrderViewListener mOnOrderViewListener;

    public OrderShowView(Context context) {
        super(context);
    }

    public OrderShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void initViews() {
        mTextOrderCount = (TextView) findViewById(R.id.text_order_count);
        mTextDelAll = (TextView) findViewById(R.id.text_order_del_all);
        mTextCancel = (TextView) findViewById(R.id.text_order_cancel);
        mTextOrderPrice = (TextView) findViewById(R.id.text_order_price);
        mTextOrderNow = (TextView) findViewById(R.id.text_order_now);
        mListView = (ListView) findViewById(R.id.order_show_lv);

        mTextDelAll.setOnClickListener(mOnclickListener);
        mTextCancel.setOnClickListener(mOnclickListener);
        mTextOrderNow.setOnClickListener(mOnclickListener);
    }

    public void initData() {
        //获取订单数据
        mOrder = (Order) ((ApplicationEx)(mActivity).getApplication()).receiveInternalActivityParam("order");
        if (mOrder == null) {
            mOrder = new Order();
            mOrder.setOrderId(UUID.randomUUID().toString());
            mOrder.setMenuList(new ArrayList<Menu>());
            mOrder.setStatus(Order.OrderStatus.NOTSUBMIT);
            ((ApplicationEx) (mActivity).getApplication()).setInternalActivityParam("order", mOrder);
        }

        setTextOrderCount(mOrder.getSize());
        setTextOrderPrice(CommonUtils.round(mOrder.getTotalPrice(), 1, BigDecimal.ROUND_HALF_UP));

        // 配置listworker
        if (mListWorker == null) {
            mListWorker = new OrderListWorker(mActivity, mOrder.getMenuList(), new OrderListWorkerCallBack());
            mListAdapter = new SimpleListWorkerAdapter(mListWorker);
            mListView.setAdapter(mListAdapter);
            mListView.setOnItemClickListener(mListWorker);
        } else {
            mListWorker.setData(mOrder.getMenuList());
            mListAdapter.notifyDataSetChanged();
        }
    }

    public void setData(List<Menu> dataList) {
        if (mListWorker != null) {
            mListWorker.setData(dataList);
            mListAdapter.notifyDataSetChanged();
        }
    }

    private View.OnClickListener mOnclickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (v == mTextDelAll) {
                mOrder.clear();
                setTextOrderCount(mOrder.getSize());
                setTextOrderPrice(CommonUtils.round(mOrder.getTotalPrice(), 1, BigDecimal.ROUND_HALF_UP));
                setData(mOrder.getMenuList());
            }else  if (v == mTextCancel) {
                exitView();
            }else {
                Intent intent = new Intent(mActivity, PayActivity.class);
                intent.putExtra("totalPrice", mOrder.getTotalPrice());
                mActivity.startActivity(intent);
            }
        }
    };

    public void setTextOrderCount(int count) {
        mTextOrderCount.setText("" + count);
    }

    public void setTextOrderPrice(float price) {
        mTextOrderPrice.setText("总计¥" + price);
    }

    public void exitView() {
        ((ViewGroup)getParent()).setVisibility(GONE);
        if (mOnOrderViewListener != null) {
            mOnOrderViewListener.onBackToFoodFrag(mOrder);
        }
    }

    public boolean isVisible() {
        return (((ViewGroup)getParent()).getVisibility() == VISIBLE);
    }

    /**
     * OrderListWorker回调类
     */
    class OrderListWorkerCallBack implements OrderListWorker.OnListWorkerListener {


        @Override
        public void onItemClick(int index) {

        }

        @Override
        public void onAddMenuClicked(Object itemData) {
            Menu menu = (Menu) itemData;
            mOrder.addMenu(menu);
            setTextOrderCount(mOrder.getSize());
            setTextOrderPrice(CommonUtils.round(mOrder.getTotalPrice(), 1, BigDecimal.ROUND_HALF_UP));
            mListAdapter.notifyDataSetChanged();
        }

        @Override
        public void onSubMenuClicked(Object itemData) {
            Menu menu = (Menu) itemData;
            mOrder.delMenu(menu);
            setTextOrderCount(mOrder.getSize());
            setTextOrderPrice(CommonUtils.round(mOrder.getTotalPrice(), 1, BigDecimal.ROUND_HALF_UP));
            mListAdapter.notifyDataSetChanged();
        }

    }

    public void setOnOrderViewListener(OnOrderViewListener listener) {
        this.mOnOrderViewListener = listener;
    }

    public interface OnOrderViewListener{
        void onBackToFoodFrag(Order order);
    }
}
