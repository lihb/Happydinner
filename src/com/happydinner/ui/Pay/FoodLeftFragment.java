package com.happydinner.ui.Pay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happydinner.R;
import com.happydinner.base.ApplicationEx;
import com.happydinner.base.TypeEnum;
import com.happydinner.entitiy.Menu;
import com.happydinner.ui.widget.CustomAnimation;

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
    @InjectView(R.id.relative_food_left)
    RelativeLayout relativeFoodLeft;
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
        linearlayoutDrink.setSelected(true);

        TranslateAnimation transAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, -1.0f, TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,TranslateAnimation.RELATIVE_TO_SELF, 0.0f);
        transAnim.setDuration(400);

        CustomAnimation customAnimation = new CustomAnimation();
        customAnimation.setDuration(400);
        customAnimation.setStartOffset(400);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(transAnim);
        set.addAnimation(customAnimation);
//        set.setInterpolator(new OvershootInterpolator());
        LayoutAnimationController lac = new LayoutAnimationController(set, 0.5f);
        relativeFoodLeft.setLayoutAnimation(lac);
    }

    private View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mTransaction = mFragmentManager.beginTransaction();
            ArrayList<Menu> dataList = new ArrayList<>();
            if (v == linearlayoutDrink) {
                dataList.clear();
                List<Menu> collection = sortedMap.get(TypeEnum.DRINK.ordinal());
                dataList.addAll(collection);
                linearlayoutSnack.setSelected(false);
                linearlayoutFood.setSelected(false);
                linearlayoutSetmeal.setSelected(false);
                linearlayoutDrink.setSelected(true);
            } else if (v == linearlayoutSnack) {
                dataList.clear();
                List<Menu> collection = sortedMap.get(TypeEnum.SNACK.ordinal());
                dataList.addAll(collection);
                linearlayoutSnack.setSelected(true);
                linearlayoutFood.setSelected(false);
                linearlayoutSetmeal.setSelected(false);
                linearlayoutDrink.setSelected(false);
            } else if (v == linearlayoutFood) {
                dataList.clear();
                List<Menu> collection = sortedMap.get(TypeEnum.PRI_FOOD.ordinal());
                dataList.addAll(collection);
                linearlayoutSnack.setSelected(false);
                linearlayoutFood.setSelected(true);
                linearlayoutSetmeal.setSelected(false);
                linearlayoutDrink.setSelected(false);
            } else if (v == linearlayoutSetmeal) {
                dataList.clear();
                List<Menu> collection = sortedMap.get(TypeEnum.MEALSET.ordinal());
                dataList.addAll(collection);
                linearlayoutSnack.setSelected(false);
                linearlayoutFood.setSelected(false);
                linearlayoutSetmeal.setSelected(true);
                linearlayoutDrink.setSelected(false);
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
