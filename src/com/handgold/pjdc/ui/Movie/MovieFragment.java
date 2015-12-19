package com.handgold.pjdc.ui.Movie;

import android.content.Intent;
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
import com.handgold.pjdc.activity.VideoPlayer2Activity;
import com.handgold.pjdc.entitiy.MovieInfo;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

/**
 * Created by lihb on 15/12/19.
 */
public class MovieFragment extends Fragment{

    private GridView mGridView = null;

    private ArrayList<MovieInfo> mDataList;

    private MovieAdapter mAdapter;


    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            ((ViewGroup)view).getChildAt(0).setBackgroundResource(R.drawable.movie_item_selector);
            ((ViewGroup)view).getChildAt(0).setSelected(true);
            MovieInfo movie = (MovieInfo) parent.getAdapter().getItem(position);
            Intent intent = new Intent(getActivity(), VideoPlayer2Activity.class);
            movie.setMovieUrl("http://download.cloud.189.cn/v5/downloadFile.action?downloadRequest=1_266BEB5F2F53474145C6EBE33E9A75D592251F2581CFE66ED934BC80674F070BA6790DA91C37DD2867779B6A435B6E040ED7928D6EFEB456A463C8E6238E8DA431473E7443FCC8025B64223A6700BF64EDD9FFDFEEA7447A59FC024F4CE7979319CFCCF6F79641E0E10945F7D23B60F7557901BF94E0BF88DFACD44EF40A4A4D0E77B882");
            intent.putExtra("videoUrl", movie.getMovieUrl());
            intent.putExtra("fileName", movie.getName());
            getActivity().startActivity(intent);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_game_fragment, null);
        mGridView = (GridView) view.findViewById(R.id.movie_game_gv);
        mGridView.setOnItemClickListener(mOnItemClickListener);
//        setGridViewAnimation(mGridView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        mDataList = bundle.getParcelableArrayList("dataList");

        if (mAdapter == null) {
            mAdapter = new MovieAdapter(mDataList, getActivity());
            mGridView.setAdapter(mAdapter);
        } else {
            mAdapter.setData(mDataList);
            mAdapter.notifyDataSetChanged();
        }

    }


    /**
     * 设置电影进入动画
     *
     * @param gridView
     */
    private void setGridViewAnimation(GridView gridView) {
        TranslateAnimation transAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, 1.0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f, TranslateAnimation.RELATIVE_TO_SELF, 0.0f);
        transAnim.setDuration(600);
        LayoutAnimationController lac = new LayoutAnimationController(transAnim, 0.5f);
        gridView.setLayoutAnimation(lac);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MovieFragment"); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MovieFragment");
    }
}
