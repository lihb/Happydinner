package com.happydinner.ui.Order;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.happydinner.activity.R;
import com.happydinner.base.ApplicationEx;
import com.happydinner.entitiy.Menu;
import com.happydinner.entitiy.Order;
import com.happydinner.ui.OrderRightFragAdapter;

/**
 * Created by lihb on 15/5/21.
 */
public class OrderRightFragment extends android.support.v4.app.Fragment {

    static final int NUM_ITEMS = 4;

    static ListView orderRightLv;

    private static Order mOrder;

    private static List<Menu> orderMenuList;

    private static RefreshLeftFragListener mRefreshLeftFragListener;

    private static OrderRightFragAdapter mAdapter;

    private ViewPager viewPager;

    private static StatePagerAdapter statePagerAdapter;

    private PagerTabStrip pagerTabStrip;

    private List<String> titleList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleList = new ArrayList<String>();
        titleList.add("店长推荐");
        titleList.add("特色菜");
        titleList.add("家常菜");
        titleList.add("湘菜");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rightView = inflater.inflate(R.layout.order_right_fragment, container, false);

        viewPager = (ViewPager) rightView.findViewById(R.id.viewpager);
        pagerTabStrip = (PagerTabStrip) rightView.findViewById(R.id.pagertabstrip);
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.orange));
        pagerTabStrip.setDrawFullUnderline(false);
        pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.win_bar_bg));
        pagerTabStrip.setTextSpacing(50);
        statePagerAdapter = new StatePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(statePagerAdapter);

//        ButterKnife.inject(this, rightView);
        return rightView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mRefreshLeftFragListener = (RefreshLeftFragListener) activity;
        } catch (Exception e) {
            throw new ClassCastException(activity.toString() + "must implement RefreshLeftFragListener");
        }
    }


    /**
     * 回调接口，传递数据到activity，用来刷新左边的fragment
     */
    public interface RefreshLeftFragListener {
        void changeDataToLeft(Order order);
    }


    public class StatePagerAdapter extends FragmentStatePagerAdapter {

        public StatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ArrayListFragment.getInstances(position);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        /**
         * 刷新本viewpager的页面事件一定要调用的方法
         * 
         * @param object
         * @return
         */
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }

    public static class ArrayListFragment extends android.support.v4.app.Fragment {

        int num;

        static ArrayListFragment getInstances(int num) {
            ArrayListFragment listFragment = new ArrayListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("num", num);
            listFragment.setArguments(bundle);
            return listFragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            num = (getArguments() != null ? getArguments().getInt("num") : 1);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.order_right_frag_pager_list, null);
            orderRightLv = (ListView) view.findViewById(R.id.order_right_lv);
            mOrder = (Order) ((ApplicationEx) getActivity().getApplication()).receiveInternalActivityParam("order");
            mAdapter = new OrderRightFragAdapter(getActivity(), new AdapterCallBack());
            mAdapter.setData(mOrder.getMenuList());
            orderRightLv.setAdapter(mAdapter);
            return view;
        }

        @Override
        public void onPause() {
            super.onPause();
        }

    }

    public static class AdapterCallBack implements OrderRightFragAdapter.OrderRightFragListener {

        @Override
        public void onItemClicked(int index) {

        }

        @Override
        public void onAddMenuClicked(Object itemData) {
            Menu menu = (Menu) itemData;
            mOrder.addMenu(menu);
            mRefreshLeftFragListener.changeDataToLeft(mOrder);
            statePagerAdapter.notifyDataSetChanged();
            mAdapter.notifyDataSetChanged();

        }

        @Override
        public void onSubMenuClicked(Object itemData) {
            Menu menu = (Menu) itemData;
            mOrder.delMenu(menu);
            mRefreshLeftFragListener.changeDataToLeft(mOrder);
            statePagerAdapter.notifyDataSetChanged();
            mAdapter.notifyDataSetChanged();
        }

    }

}
