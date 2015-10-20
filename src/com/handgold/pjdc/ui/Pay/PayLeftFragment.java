package com.handgold.pjdc.ui.Pay;

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
import com.handgold.pjdc.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @InjectView(R.id.pay_left_lv)
    ListView mListView;

    private SimpleAdapter mAdapter;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
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

        View view = inflater.inflate(R.layout.pay_left_fragment, null);
        ButterKnife.inject(this, view);
        initContentView(view);
        initList();
        return view;
    }


    private void initContentView(View contentView) {

        mListView = (ListView) contentView.findViewById(R.id.pay_left_lv);
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
            view.setSelected(true);
            if (id == ZHIFUBAO) {
                payRightZhiFuBaoFragment = new PayRightZhiFuBaoFragment();
                payRightZhiFuBaoFragment.setArguments(bundle);
                mTransaction.replace(R.id.right_frag, payRightZhiFuBaoFragment, "pay_right_zhifubao_frag");
            } else if (id == WECHAT) {
                payRightWeChatFragment = new PayRightWeChatFragment();
                payRightWeChatFragment.setArguments(bundle);
                mTransaction.replace(R.id.right_frag, payRightWeChatFragment, "pay_right_wechat_frag");

            }
            mTransaction.commit();
        }
    };

    private void initList() {
        String[] strings = {"icon", "txt"};// image
        int[] ids = {R.id.pay_icon_iv, R.id.pay_desc_tv};// 对应布局文件的id
        mAdapter = new SimpleAdapter(getActivity(), getData(),
                R.layout.pay_left_item, strings, ids);
        mListView.setAdapter(mAdapter);// 绑定适配器

    }

    private List<HashMap<String, Object>> getData() {
        // 新建一个集合类，用于存放多条数据
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map;
        map = new HashMap<String, Object>();
        map.put("icon", R.drawable.icon_zhifubao);
        map.put("txt", "支付宝");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("icon", R.drawable.icon_wechat);
        map.put("txt", "微信");
        list.add(map);

        return list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
