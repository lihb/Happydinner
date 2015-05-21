package com.happydinner.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happydinner.activity.R;
import com.happydinner.entitiy.Menu;
import com.happydinner.entitiy.Order;

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
    private OrderLeftFragAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mOrder = bundle.getParcelable("order");
            initData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View leftView = inflater.inflate(R.layout.order_left_fragment, container, false);

        ButterKnife.inject(this, leftView);

        return leftView;
    }

    private void initData() {

        orderMenuList = mOrder.getMenuList();
        mAdapter = new OrderLeftFragAdapter(getActivity());
        orderLeftLv.setAdapter(mAdapter);
        mAdapter.setData(orderMenuList);
        orderLeftLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
