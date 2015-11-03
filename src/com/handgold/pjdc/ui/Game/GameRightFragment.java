package com.handgold.pjdc.ui.Game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import com.handgold.pjdc.R;
import com.handgold.pjdc.entitiy.GameInfo;
import com.handgold.pjdc.util.CommonUtils;

import java.util.ArrayList;

/**
 * 类说明：
 *
 * @author Administrator
 * @version 1.0
 * @date 2015/6/16
 */

public class GameRightFragment extends Fragment {

    private GridView mGridView;

    private ArrayList<GameInfo> mDataList;

    private GameRightAdapter mAdapter;
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CommonUtils.toastText(getActivity(), "你点击了" + position);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.food_right_fragment, null);
        mGridView = (GridView) view.findViewById(R.id.food_right_lv);
        mGridView.setOnItemClickListener(mOnItemClickListener);
        setGridViewAnimation(mGridView);

        return view;
    }

    /**
     * 设置菜品进入动画
     * @param gridView
     */
    private void setGridViewAnimation(GridView gridView) {
        TranslateAnimation transAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, 1.0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f, TranslateAnimation.RELATIVE_TO_SELF, 0.0f);
        transAnim.setDuration(600);
        LayoutAnimationController lac = new LayoutAnimationController(transAnim, 0.5f);
        gridView.setLayoutAnimation(lac);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        mDataList = bundle.getParcelableArrayList("dataList");
        // 配置listworker
        if (mAdapter == null) {
            mAdapter = new GameRightAdapter(mDataList, getActivity());
            mGridView.setAdapter(mAdapter);
        } else {
            mAdapter.setData(mDataList);
            mAdapter.notifyDataSetChanged();
        }

    }


}
