package com.happydinner.activity;

import com.happydinner.base.BaseActivity;

/**
 * Created by lihb on 15/5/16.
 */
public class FoodShowActivity extends BaseActivity {
   /* @InjectView(R.id.menu_listview)
    ListView mMenuListview;

    private MenuListWorker mListWorker;


    private  SortedMap sortedMap = new TreeMap<Integer, List<Menu>>();

    private SimpleListWorkerAdapter mListAdapter;

    private HeadView headView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        ButterKnife.inject(this);
        initView();
        initData();
    }

    private void initView() {
        headView = new HeadView(this);
        headView.h_left_tv.setText("返回");
        headView.h_title.setText("");
        headView.h_right_tv.setText("自助下单");
        headView.h_left.setOnClickListener(mOnClickListener);
        headView.h_right_tv.setOnClickListener(mOnClickListener);

    }

    private void initData() {
        Menu meatMenu = new Menu("红烧肉", null, null, 15.8f, "好吃看到见－meat", 1, false, 1);
        Menu lurouMenu = new Menu("卤肉", null, null, 15.8f, "好吃看到见－lurou", 1, false, 1);
        Menu luosiMenu = new Menu("田螺", null, null, 15.8f, "好吃看到见－tianluo", 1, false, 2);
        Menu fishMenu = new Menu("鱼", null, null, 15.8f, "好吃看到见-fish", 1, false, 1);
        Menu chickMenu = new Menu("鸡", null, null, 20f, "好吃看到见-chick", 1, false, 2);
        Menu duckMenu = new Menu("鸭", null, null, 19f, "好吃看到见-duck", 0.8f, false, 3);
        List<Menu> menuList = new ArrayList<Menu>();
        menuList.add(meatMenu);
        menuList.add(lurouMenu);
        menuList.add(luosiMenu);
        menuList.add(fishMenu);
        menuList.add(chickMenu);
        menuList.add(duckMenu);

        *//**
         * 比较器：给menu按照type排序用
         *//*
        Comparator<Menu> comparator = new Comparator<Menu>() {
            @Override
            public int compare(Menu lhs, Menu rhs) {

                    return lhs.getType() - rhs.getType();
            }
        };
        Collections.sort(menuList, comparator);

        List<Menu> tmpList = new ArrayList<Menu>();

        int oldKey = menuList.get(0).getType();

        for (int i = 0; i < menuList.size(); i++) {
            Menu menuItemData = menuList.get(i);
            int newKey = menuItemData.getType();
            if(newKey == oldKey) {
                tmpList.add(menuItemData);
            }else {
                sortedMap.put(oldKey, tmpList);
                tmpList = new ArrayList<Menu>();
                tmpList.add(menuItemData);
                oldKey = newKey;
            }
        }
        sortedMap.put(oldKey, tmpList); // 处理最后一组数据

        if (mListWorker == null) {
            mListWorker = new MenuListWorker(FoodShowActivity.this, sortedMap, new MenuListWorker.OnListWorkerListener(){
                @Override
                public void onItemClicked(Object iteamdata, int index) {

                }

                @Override
                public void onAddMenuClicked(Object itemData) {

                }

                @Override
                public void onSubMenuClicked(Object itemData) {

                }
            });
            mListAdapter = new SimpleListWorkerAdapter(mListWorker);
            mMenuListview.setAdapter(mListAdapter);
            // ListView的 ItemClick 由 ListWorker 转发
            mMenuListview.setOnItemClickListener(mListWorker);
        } else {
            mListWorker.setData(sortedMap);
            mListAdapter.notifyDataSetChanged();
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.head_left:
                    finish();
                    break;
                case R.id.head_right_tv:
                case R.id.head_right_tv_layout:
                    //todo 跳转到自主下单activity
                    break;
                default:
                    break;
            }
        }
    };*/



}
