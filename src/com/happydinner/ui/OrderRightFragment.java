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
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happydinner.activity.R;
import com.happydinner.entitiy.Menu;
import com.happydinner.entitiy.Order;

import java.util.List;

/**
 * Created by lihb on 15/5/21.
 */
public class OrderRightFragment extends Fragment {

    @InjectView(R.id.order_right_lv)
    ListView orderRightLv;

    private Order mOrder;
    private List<Menu> orderMenuList;
    private OrderRightFragAdapter mAdapter;

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
        View rightView = inflater.inflate(R.layout.order_right_fragment, container, false);

        ButterKnife.inject(this, rightView);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mOrder = bundle.getParcelable("order");
            initData();
        }
        return rightView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        initData();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initData();
    }

    private void initData() {
//        Bundle bundle = getArguments();
//        mOrder = bundle.getParcelable("order");
        orderMenuList = mOrder.getMenuList();
        mAdapter = new OrderRightFragAdapter(getActivity());
        orderRightLv.setAdapter(mAdapter);
        mAdapter.setData(orderMenuList);
        orderRightLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
