package com.happydinner.ui.Pay;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happydinner.activity.R;
import com.happydinner.base.ApplicationEx;
import com.happydinner.entitiy.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 * 类说明：
 *
 * @author Administrator
 * @version 1.0
 * @date 2015/6/16
 */

public class FoodLeftFragment extends Fragment {


    @InjectView(R.id.linearlayout_drink)
    LinearLayout linearlayoutDrink;
    @InjectView(R.id.linearlayout_snack)
    LinearLayout linearlayoutSnack;
    @InjectView(R.id.linearlayout_food)
    LinearLayout linearlayoutFood;
    @InjectView(R.id.linearlayout_setmeal)
    LinearLayout linearlayoutSetmeal;
    private SortedMap<Integer, List<Menu>> sortedMap;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    private FoodRightFragment mFoodRightFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.food_left_fragment, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragmentManager = getFragmentManager();
        sortedMap = (SortedMap) ((ApplicationEx) getActivity().getApplication()).receiveInternalActivityParam("allMenuList");
        linearlayoutDrink.setOnClickListener(mOnclickListener);
        linearlayoutSnack.setOnClickListener(mOnclickListener);
        linearlayoutFood.setOnClickListener(mOnclickListener);
        linearlayoutSetmeal.setOnClickListener(mOnclickListener);
    }

    private View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mTransaction = mFragmentManager.beginTransaction();
            ArrayList<Menu> dataList = new ArrayList<>();
            if (v == linearlayoutDrink) {
                dataList.clear();
                List<Menu> collection = sortedMap.get(0);
                dataList.addAll(collection);
                linearlayoutDrink.setBackgroundResource(R.drawable.focus);
                linearlayoutSnack.setBackground(null);
                linearlayoutFood.setBackground(null);
                linearlayoutSetmeal.setBackground(null);
            } else if (v == linearlayoutSnack) {
                dataList.clear();
                List<Menu> collection = sortedMap.get(1);
                dataList.addAll(collection);
                linearlayoutSnack.setBackgroundResource(R.drawable.focus);
                linearlayoutDrink.setBackground(null);
                linearlayoutFood.setBackground(null);
                linearlayoutSetmeal.setBackground(null);
            } else if (v == linearlayoutFood) {
                dataList.clear();
                List<Menu> collection = sortedMap.get(2);
                dataList.addAll(collection);
                linearlayoutFood.setBackgroundResource(R.drawable.focus);
                linearlayoutSnack.setBackground(null);
                linearlayoutDrink.setBackground(null);
                linearlayoutSetmeal.setBackground(null);
            } else if (v == linearlayoutSetmeal) {
                dataList.clear();
                List<Menu> collection = sortedMap.get(3);
                dataList.addAll(collection);
                linearlayoutSetmeal.setBackgroundResource(R.drawable.focus);
                linearlayoutSnack.setBackground(null);
                linearlayoutFood.setBackground(null);
                linearlayoutDrink.setBackground(null);
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("dataList", dataList);
            mFoodRightFragment = new FoodRightFragment();
            mFoodRightFragment.setArguments(bundle);
            mTransaction.replace(R.id.right_frag, mFoodRightFragment, "food_right_frag");
            mTransaction.commit();

        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
