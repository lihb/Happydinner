package com.happydinner.ui.listworker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happydinner.activity.R;
import com.happydinner.base.MyAppContext;
import com.happydinner.common.list.AbstractListWorker;
import com.happydinner.entitiy.Menu;
import com.happydinner.ui.widget.MenuCountView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lihb on 15/5/17.
 */
public class MenuListWorker extends AbstractListWorker {
    public static final int MAX_MENUS_COUNT_PER_LINE = 3;
    public static final int SHOW_WIDTH = (MyAppContext.screenW - 20) / 2;
    public static final int SHOW_HEIGHT = (MyAppContext.screenW - 20) / 2;
    private ArrayList<Menu> mMenuList = new ArrayList<Menu>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Context mContext;
    private OnListWorkerListener mOnListWorkerListener;

    public enum MenuType {
        RECOMMEND, SPECIAL, NEW
    }

    public MenuListWorker(Context context, ArrayList dataList, OnListWorkerListener onListWorkerListener) {
        mContext = context;
        mMenuList = dataList;
        mOnListWorkerListener = onListWorkerListener;
        rebuildDataList();
        rebuildWorkerMap();
    }

    public void setData(ArrayList dataList) {
        mMenuList = dataList;
        rebuildDataList();

    }

    @Override
    protected Map<Integer, ItemWorker> constructItemWorkerMap() {
        Map<Integer, ItemWorker> map = new HashMap<Integer, ItemWorker>();
        map.put(DataType.MENU.ordinal(), new MenuItemWorker(mOnListWorkerListener));
        return map;
    }

    @Override
    protected List<ListData> constructItemDataList() {
        List<ListData> datas = new ArrayList<ListData>();
        for (int i = 0; i < mMenuList.size(); i += MAX_MENUS_COUNT_PER_LINE) {
            int endIndex =
                    (i + MAX_MENUS_COUNT_PER_LINE <= mMenuList.size()) ? (i + MAX_MENUS_COUNT_PER_LINE) : mMenuList
                            .size();
            ListData itemMenus = new ListData();
            itemMenus.type = DataType.MENU.ordinal();
            itemMenus.obj = mMenuList.subList(i, endIndex);
            datas.add(itemMenus);
        }

        return datas;
    }

    public static interface OnListWorkerListener {
        /**
         * 当条目选中状态改变时调用
         */
        void onItemClicked(Object itemData, int index);

        /**
         * 加菜
         */
        void onAddMenuClicked(Object itemData);

        /**
         * 减菜
         */
        void onSubMenuClicked(Object itemData);

        /**
         * 添加到订单
         *
         * @param itemData
         */
        void onAddToOrderClicked(Object itemData);

        /**
         * 查看详情
         */
        void onGotoLookDesc(View view, Object itemData);
    }

    public enum DataType {
        MENU
    }


    public class MenuItemWorker implements ItemWorker {

        OnListWorkerListener mListener;

        public MenuItemWorker(OnListWorkerListener mOnListWorkerListener) {
            mListener = mOnListWorkerListener;
        }

        @Override
        public View newView(int position, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View convertView = inflater.inflate(R.layout.menu_item_detail, null, false);
            MenuViewHolder holder = new MenuViewHolder(convertView);
            for (int i = 0; i < MAX_MENUS_COUNT_PER_LINE; i++) { //设置初始大小
//                holder.getRlLocMenu(i).setLayoutParams(new LinearLayout.LayoutParams(SHOW_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            convertView.setTag(holder);
            return convertView;
        }

        @Override
        public void updateItem(View convertView, final Object itemData, ViewGroup parent, int positon) {
            List<Menu> menuList = (List<Menu>) itemData;
            MenuViewHolder menuViewHolder = (MenuViewHolder) convertView.getTag();
            for (int i = 0; i < MAX_MENUS_COUNT_PER_LINE; i++) {
                menuViewHolder.getRlLocMenu(i).setVisibility(View.INVISIBLE);
            }
            for (int i = 0; i < menuList.size(); i++) {
                menuViewHolder.getRlLocMenu(i).setVisibility(View.VISIBLE);
                final Menu menu = menuList.get(i);
                menuViewHolder.getMenuNameTxt(i).setText(menu.getName());
                menuViewHolder.getMenuPriceTxt(i).setText("¥" + menu.getPrice());
                menuViewHolder.getMenuRestaurantName(i).setText(menu.getRestaurantName());


            }

        }

        @Override
        public void onItemClicked(int position, View itemView, ViewGroup parent, Object itemData) {

        }


        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'menu_item_detail.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
        class MenuViewHolder {
            @InjectView(R.id.menu_name_iv1)
            ImageView menuNameIv1;
            @InjectView(R.id.menu_name_title1)
            TextView menuNameTitle1;
            @InjectView(R.id.menu_name_price1)
            TextView menuNamePrice1;
            @InjectView(R.id.menu_name_restaurant1)
            TextView menuNameRestaurant1;
//            @InjectView(R.id.menu_count_view1)
//            MenuCountView menuCountView1;
            @InjectView(R.id.loc_menu_rl1)
            RelativeLayout locMenuRl1;
            @InjectView(R.id.menu_name_iv2)
            ImageView menuNameIv2;
            @InjectView(R.id.menu_name_title2)
            TextView menuNameTitle2;
            @InjectView(R.id.menu_name_price2)
            TextView menuNamePrice2;
            @InjectView(R.id.menu_name_restaurant2)
            TextView menuNameRestaurant2;
//            @InjectView(R.id.menu_count_view2)
//            MenuCountView menuCountView2;
            @InjectView(R.id.loc_menu_rl2)
            RelativeLayout locMenuRl2;
            @InjectView(R.id.menu_name_iv3)
            ImageView menuNameIv3;
            @InjectView(R.id.menu_name_title3)
            TextView menuNameTitle3;
            @InjectView(R.id.menu_name_price3)
            TextView menuNamePrice3;
            @InjectView(R.id.menu_name_restaurant3)
            TextView menuNameRestaurant3;
//            @InjectView(R.id.menu_count_view3)
//            MenuCountView menuCountView3;
            @InjectView(R.id.loc_menu_rl3)
            RelativeLayout locMenuRl3;

            MenuViewHolder(View view) {
                ButterKnife.inject(this, view);
            }

            public RelativeLayout getRlLocMenu(int index) {
                switch (index) {
                    case 0:
                        return locMenuRl1;
                    case 1:
                        return locMenuRl2;
                    case 2:
                        return locMenuRl3;
                }
                return null;
            }

            public TextView getMenuNameTxt(int index) {
                switch (index) {
                    case 0:
                        return menuNameTitle1;
                    case 1:
                        return menuNameTitle2;
                    case 2:
                        return menuNameTitle3;
                }
                return null;
            }

            public TextView getMenuPriceTxt(int index) {
                switch (index) {
                    case 0:
                        return menuNamePrice1;
                    case 1:
                        return menuNamePrice2;
                    case 2:
                        return menuNamePrice3;
                }
                return null;
            }

            public TextView getMenuRestaurantName(int index) {
                switch (index) {
                    case 0:
                        return menuNameRestaurant1;
                    case 1:
                        return menuNameRestaurant2;
                    case 2:
                        return menuNameRestaurant3;
                }
                return null;
            }
        }
    }
}
