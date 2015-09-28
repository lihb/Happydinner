package com.happydinner.ui.Pay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.happydinner.activity.R;

/**
 * 类说明：
 *
 * @author Administrator
 * @version 1.0
 * @date 2015/6/16
 */

public class PayLeftFragment extends Fragment {


    private final int ZHIFUBAO = 0;
    private final int WECHAT = 1;
    private final int UNIONPAY = 2;

    @InjectView(R.id.left_lv)
    ListView mListView;

    private SimpleAdapter mAdapter;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    private PayRightUnionPayFragment payRightUnionPayFragment;
    private PayRightZhiFuBaoFragment payRightZhiFuBaoFragment;
    private PayRightWeChatFragment payRightWeChatFragment;
    private PayLeftFragment payLeftFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.left_fragment, null);
        ButterKnife.inject(this, view);
        initContentView(view);
        return view;
    }


    private void initContentView(View contentView) {

        mListView = (ListView) contentView.findViewById(R.id.left_lv);
        mListView.setHeaderDividersEnabled(false);
        mListView.setFooterDividersEnabled(false);
        mListView.setOnItemClickListener(mOnItemClickListener);

    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mTransaction = mFragmentManager.beginTransaction();
            payLeftFragment = new PayLeftFragment();
            mTransaction.replace(R.id.left_frag, payLeftFragment, "pay_left_frag");
            Intent intent = getActivity().getIntent();
            float price = intent.getFloatExtra("totalPrice", 0f);
            Bundle bundle = new Bundle();
            bundle.putFloat("price", price);
            if (id == ZHIFUBAO) {

                payRightZhiFuBaoFragment = new PayRightZhiFuBaoFragment();
                payRightZhiFuBaoFragment.setArguments(bundle);
                mTransaction.replace(R.id.right_frag, payRightZhiFuBaoFragment, "pay_right_zhifubao_frag");
            } else if (id == WECHAT) {
                payRightWeChatFragment = new PayRightWeChatFragment();
                payRightWeChatFragment.setArguments(bundle);
                mTransaction.replace(R.id.right_frag, payRightWeChatFragment, "pay_right_wechat_frag");

            } else if (id == UNIONPAY) {
                payRightUnionPayFragment = new PayRightUnionPayFragment();
                payRightUnionPayFragment.setArguments(bundle);
                mTransaction.replace(R.id.right_frag, payRightUnionPayFragment, "pay_right_unionpay_frag");
            }
            mTransaction.commit();


        }
    };




    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
