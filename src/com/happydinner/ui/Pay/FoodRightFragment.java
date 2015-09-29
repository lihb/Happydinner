package com.happydinner.ui.Pay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.happydinner.activity.R;
import com.happydinner.common.list.SimpleListWorkerAdapter;
import com.happydinner.entitiy.Menu;
import com.happydinner.ui.listworker.MenuListWorker;

import java.util.ArrayList;

/**
 * 类说明：
 *
 * @author Administrator
 * @version 1.0
 * @date 2015/6/16
 */

public class FoodRightFragment extends Fragment {

    ListView mListView;

    private ArrayList<Menu> mDataList;

    private MenuListWorker mListWorker;

    private SimpleListWorkerAdapter mListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.food_right_fragment, null);
        mListView = (ListView) view.findViewById(R.id.food_right_lv);
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
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    /**
     * MenuListWorker回调类
     */
    class MenuListWorkerCallBack implements MenuListWorker.OnListWorkerListener {

        @Override
        public void onItemClicked(Object itemData, int index) {
            Menu menu = (Menu) itemData;
            Toast.makeText(getActivity(), "你点击了：" + menu.getName(), Toast.LENGTH_SHORT).show();
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
           /* mOrder.addMenu(menu);
            CommonUtils.toastText(getActivity(), "总价:" + mOrder.getTotalPrice());
            mListAdapter.notifyDataSetChanged();*/

        }

        @Override
        public void onGotoLookDesc(View view, Object itemData) {
//            Menu menu = (Menu) itemData;
//            zoomViewFromMain(menu);
        }
    }
}
