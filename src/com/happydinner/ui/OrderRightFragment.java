package com.happydinner.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happydinner.activity.R;
import com.happydinner.common.list.SimpleListWorkerAdapter;
import com.happydinner.entitiy.Menu;
import com.happydinner.entitiy.Order;
import com.happydinner.ui.listworker.OrderRightListWorker;

import java.util.List;

/**
 * Created by lihb on 15/5/21.
 */
public class OrderRightFragment extends Fragment {

    @InjectView(R.id.order_right_lv)
    ListView orderRightLv;

    private OrderRightListWorker mListWorker;
    private SimpleListWorkerAdapter mListAdapter;


    private Order mOrder;
    private List<Menu> orderMenuList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rightView = inflater.inflate(R.layout.order_right_fragment, container, false);

        ButterKnife.inject(this, rightView);
        return rightView;
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
                mListWorker = new OrderRightListWorker(getActivity(), orderMenuList, new OrderRightListWorker.OnListWorkerListener() {
                    @Override
                    public void onItemClick(int index) {

                    }

                    @Override
                    public void onAddMenuClicked(Object itemData) {
                        Menu menu = (Menu) itemData;
                        mOrder.addMenu(menu);
                        mListAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onSubMenuClicked(Object itemData) {
                        Menu menu = (Menu) itemData;
                        mOrder.delMenu(menu);
                        mListAdapter.notifyDataSetChanged();
                    }
                });
                mListAdapter = new SimpleListWorkerAdapter(mListWorker);
                orderRightLv.setAdapter(mListAdapter);
                // ListView的 ItemClick 由 ListWorker 转发
                orderRightLv.setOnItemClickListener(mListWorker);
            } else {
                mListWorker.setData(orderMenuList);
                mListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
