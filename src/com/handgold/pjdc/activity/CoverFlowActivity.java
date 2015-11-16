package com.handgold.pjdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import com.handgold.pjdc.R;
import com.handgold.pjdc.base.ApplicationEx;
import com.handgold.pjdc.base.GameTypeEnum;
import com.handgold.pjdc.base.MenuTypeEnum;
import com.handgold.pjdc.entitiy.GameInfo;
import com.handgold.pjdc.entitiy.Menu;
import com.handgold.pjdc.ui.CoverFlowAdapterNew;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

import java.util.*;


public class CoverFlowActivity extends ActionBarActivity {

    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapterNew mAdapter;
    private HashMap<String, ArrayList> mData = new HashMap<>();
//    private TextSwitcher mTitle;
    private SortedMap<Integer, List<Menu>> sortedMenuMap;
    private SortedMap<Integer, List<GameInfo>> sortedGameMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去除title
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coverflow);

       /* mTitle = (TextSwitcher) findViewById(R.id.title);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(CoverFlowActivity.this);
                TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                return textView;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);*/

        mAdapter = new CoverFlowAdapterNew(this);
        mAdapter.setData(mData);
        mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("position===", position + "");
                int index = position % mAdapter.getCount();
                if (index == 2) {
                    Intent intent = new Intent(CoverFlowActivity.this, com.handgold.pjdc.activity.FoodShowActivity.class);
                    startActivity(intent);
                } else if (index == 4) {
                    Intent intent = new Intent(CoverFlowActivity.this, com.handgold.pjdc.activity.VideoListActivity.class);
                    startActivity(intent);
                } else if (index == 3) {
                    Intent intent = new Intent(CoverFlowActivity.this, com.handgold.pjdc.activity.GameShowActivity.class);
                    startActivity(intent);
                }

            }
        });

        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
//                mTitle.setText(getResources().getString(mData.get(position).titleResId));
            }

            @Override
            public void onScrolling() {
//                mTitle.setText("");
            }
        });

        // 获取菜品数据
        sortedMenuMap = (SortedMap) ((ApplicationEx) getApplication()).receiveInternalActivityParam("allMenuList");
        if (sortedMenuMap == null) {
            sortedMenuMap = new TreeMap<Integer, List<Menu>>();
            initMenuData();
        }
        // 获取游戏数据
        sortedGameMap = (SortedMap) ((ApplicationEx) getApplication()).receiveInternalActivityParam("allGameList");
        if (sortedGameMap == null) {
            sortedGameMap = new TreeMap<Integer, List<GameInfo>>();
            initGameData();
        }

        mData.put("FoodData", (ArrayList) sortedMenuMap.get(MenuTypeEnum.RECOMMEND.ordinal()));
        mData.put("GameData", (ArrayList) sortedGameMap.get(GameTypeEnum.COOLRUN.ordinal()));
        mData.put("PhotoData", (ArrayList) sortedGameMap.get(GameTypeEnum.SHOOT.ordinal()));
        mData.put("MovieData", (ArrayList) sortedGameMap.get(GameTypeEnum.RELAX_PUZZLE.ordinal()));
        mAdapter.setData(mData);
    }

    private void initMenuData() {
       /* Menu meatMenu1 = new Menu("乌冬面", null, null, 15.67f, "好吃看的见－nuddles", 1, 0, MenuTypeEnum.RECOMMEND.ordinal(), "九毛九");
        Menu meatMenu = new Menu("红烧肉", null, null, 15.67f, "好吃看的见－meat", 1, 0, MenuTypeEnum.DRINK.ordinal(), "九毛九");
        Menu lurouMenu = new Menu("卤肉", null, null, 14.59f, "好吃看的见－lurou", 1, 0, MenuTypeEnum.DRINK.ordinal(), "土菜馆");
        Menu luosiMenu = new Menu("田螺", null, null, 18.7f, "好吃看的见－tianluo", 1, 0, MenuTypeEnum.SNACK.ordinal(), "九毛九");
        Menu fishMenu = new Menu("鱼", null, null, 29f, "好吃看的见-fish", 1, 0, MenuTypeEnum.SNACK.ordinal(),"黄鹤天厨");
        Menu chickMenu = new Menu("鸡", null, null, 20f, "好吃看的见-chick", 1, 0, MenuTypeEnum.PRI_FOOD.ordinal(), "老知青");
        Menu duckMenu = new Menu("鸭", null, null, 19f, "好吃看的见-duck", 1f, 0, MenuTypeEnum.PRI_FOOD.ordinal(), "大丰收");
        Menu duckMenu2 = new Menu("血鸭", null, null, 35f, "好吃看的见-duck", 1f, 0, MenuTypeEnum.MEALSET.ordinal(), "大丰收");
        Menu fishMenu2 = new Menu("黄骨鱼", null, null, 30f, "好吃看的见-duck", 1f, 0, MenuTypeEnum.DRINK.ordinal(), "土菜馆");
        List<Menu> menuList = new ArrayList<Menu>();
        menuList.add(meatMenu);
        menuList.add(meatMenu1);
        menuList.add(lurouMenu);
        menuList.add(luosiMenu);
        menuList.add(fishMenu);
        menuList.add(chickMenu);
        menuList.add(duckMenu);
        menuList.add(duckMenu2);
        menuList.add(fishMenu2);*/
        List<Menu> menuList = new ArrayList<Menu>();
        for (int i = 0; i < 30; i++) {
            Menu menu = new Menu("菜品" + (i+1), null, null, 15.0f + i, "菜品简介" + (i+1), 1, 0, (i / 6) + 1, "大丰收");
            menuList.add(menu);

        }

        /**
         * 比较器：给menu按照type排序用
         */
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
            if (newKey == oldKey) {
                tmpList.add(menuItemData);
            } else {
                sortedMenuMap.put(oldKey, tmpList);
                tmpList = new ArrayList<Menu>();
                tmpList.add(menuItemData);
                oldKey = newKey;
            }
        }
        sortedMenuMap.put(oldKey, tmpList); // 处理最后一组数据

        ((ApplicationEx) getApplication()).setInternalActivityParam("allMenuList", sortedMenuMap);
    }

    private void initGameData() {
        List<GameInfo> gameInfoList = new ArrayList<GameInfo>();
        for (int i = 0; i < 30; i++) {
            GameInfo gameInfo = new GameInfo("游戏" + (i+1), "http://www.dddd.com", (i / 6 + 1));
            gameInfoList.add(gameInfo);

        }
        /**
         * 比较器：给menu按照type排序用
         */
        Comparator<GameInfo> comparator = new Comparator<GameInfo>() {
            @Override
            public int compare(GameInfo lhs, GameInfo rhs) {

                return lhs.getType() - rhs.getType();
            }
        };
        Collections.sort(gameInfoList, comparator);

        List<GameInfo> tmpList = new ArrayList<GameInfo>();

        int oldKey = gameInfoList.get(0).getType();

        for (int i = 0; i < gameInfoList.size(); i++) {
            GameInfo gameItemData = gameInfoList.get(i);
            int newKey = gameItemData.getType();
            if (newKey == oldKey) {
                tmpList.add(gameItemData);
            } else {
                sortedGameMap.put(oldKey, tmpList);
                tmpList = new ArrayList<GameInfo>();
                tmpList.add(gameItemData);
                oldKey = newKey;
            }
        }
        sortedGameMap.put(oldKey, tmpList); // 处理最后一组数据

        ((ApplicationEx) getApplication()).setInternalActivityParam("allGameList", sortedGameMap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁首页时，清除全局变量
        ((ApplicationEx) getApplication()).clearInternalActivityParam();
    }
}
