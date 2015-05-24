package com.happydinner.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happydinner.activity.R;
import com.happydinner.common.list.SimpleListWorkerAdapter;
import com.happydinner.entitiy.Menu;
import com.happydinner.entitiy.Order;
import com.happydinner.ui.listworker.OrderLeftListWorker;
import com.happydinner.util.CommonUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lihb on 15/5/21.
 */
public class OrderLeftFragment extends Fragment {

    @InjectView(R.id.order_menu_tv)
    TextView orderMenuTv;
    @InjectView(R.id.order_count_tv)
    TextView orderCountTv;  //结算按钮
    @InjectView(R.id.order_price_tv)
    TextView orderPriceTv; // 价格
    @InjectView(R.id.order_price_submit_tv)
    TextView orderPriceSubmitTv;
    @InjectView(R.id.order_left_lv)
    ListView orderLeftLv;
    @InjectView(R.id.order_content_left_rl)
    RelativeLayout orderContentLeftRl;

    private Order mOrder;
    private List<Menu> orderMenuList;

    private OrderLeftListWorker mListWorker;
    private SimpleListWorkerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View leftView = inflater.inflate(R.layout.order_left_fragment, container, false);

        ButterKnife.inject(this, leftView);
        return leftView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mOrder = bundle.getParcelable("order");
            orderMenuList = mOrder.getMenuList();
            if (mListWorker == null) {
                mListWorker = new OrderLeftListWorker(getActivity(), orderMenuList, new OrderLeftListWorker.OnListWorkerListener() {
                    @Override
                    public void onItemClick(int index) {

                    }
                });
                mAdapter = new SimpleListWorkerAdapter(mListWorker);
                orderLeftLv.setAdapter(mAdapter);
                orderLeftLv.setOnItemClickListener(mListWorker);
            } else {
                mListWorker.setData(orderMenuList);
                mAdapter.notifyDataSetChanged();
            }
            float temp = CommonUtils.round(mOrder.getTotalPrice(), 2, BigDecimal.ROUND_HALF_UP);
            String totalPrice = String.valueOf(temp);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append("¥").append(totalPrice).append("元");
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan(30, true), 1, spannableStringBuilder.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            orderPriceTv.setText(spannableStringBuilder);
        }
        orderCountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.toastText(getActivity(), "结算按钮被点击。。");
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void refresh(Order order) {
        orderMenuList = order.getMenuList();
        if (mListWorker != null) {
            mListWorker.setData(orderMenuList);
            mAdapter.notifyDataSetChanged();
        }
        float temp = CommonUtils.round(mOrder.getTotalPrice(), 2, BigDecimal.ROUND_HALF_UP);
        String totalPrice = String.valueOf(temp);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append("¥").append(totalPrice).append("元");
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(30, true), 1, spannableStringBuilder.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        orderPriceTv.setText(spannableStringBuilder);

    }
}
