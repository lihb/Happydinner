package com.happydinner.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happydinner.activity.R;

/**
 * 类说明：
 *
 * @author Administrator
 * @version 1.0
 * @date 2015/6/16
 */

public class PayLeftFragment extends Fragment{

    private OnPayLeftFragItemClickedListener mListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPayLeftFragItemClickedListener) activity;
        } catch (Exception e) {
            throw new ClassCastException(activity.toString() + "must implement ItemClickedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pay_left_fragment, null);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public interface OnPayLeftFragItemClickedListener {
        public void onItemClickListener();
    }
}
