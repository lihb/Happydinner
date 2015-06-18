package com.happydinner.ui.listworker;

import java.text.SimpleDateFormat;
import java.util.*;

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

/**
 * Created by lihb on 15/5/17.
 */
public class MenuListWorker extends AbstractListWorker {
    public static final int MAX_MENUS_COUNT_PER_LINE = 2;
    public static final int SHOW_WIDTH = (MyAppContext.screenW - 20) / 2;
    public static final int SHOW_HEIGHT = (MyAppContext.screenW - 20) / 2;
    SortedMap<Integer, List<Menu>> mTitleMenuMap;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Context mContext;
    private OnListWorkerListener mOnListWorkerListener;

    public enum MenuType {
        RECOMMEND, SPECIAL, NEW
    }

    public MenuListWorker(Context context, SortedMap<Integer, List<Menu>> sortMap, OnListWorkerListener onListWorkerListener) {
        mContext = context;
        mTitleMenuMap = sortMap;
        mOnListWorkerListener = onListWorkerListener;
        rebuildDataList();
        rebuildWorkerMap();
    }

    public void setData(SortedMap<Integer, List<Menu>> sortMap) {
        mTitleMenuMap = sortMap;
        rebuildDataList();

    }

    @Override
    protected Map<Integer, ItemWorker> constructItemWorkerMap() {
        Map<Integer, ItemWorker> map = new HashMap<Integer, ItemWorker>();

        map.put(DataType.TYPE.ordinal(), new TitleItemWorker());
        map.put(DataType.MENU.ordinal(), new MenuItemWorker(mOnListWorkerListener));
        return map;
    }

    @Override
    protected List<ListData> constructItemDataList() {
        List<ListData> datas = new ArrayList<ListData>();
        if (mTitleMenuMap == null) {
            return datas;
        }
        // 添加菜品类型和细节
        for (Map.Entry<Integer, List<Menu>> entry : mTitleMenuMap.entrySet()) {
            ListData itemData = new ListData();
            itemData.type = DataType.TYPE.ordinal();
            itemData.obj = entry.getKey();
            datas.add(itemData);

            List<Menu> menuInType = entry.getValue();
            // 每2个menu添加一个ItemData
            for (int i = 0; i < menuInType.size(); i += MAX_MENUS_COUNT_PER_LINE) {
                int endIndex =
                        (i + MAX_MENUS_COUNT_PER_LINE <= menuInType.size()) ? (i + MAX_MENUS_COUNT_PER_LINE) : menuInType
                                .size();
                ListData itemMenus = new ListData();
                itemMenus.type = DataType.MENU.ordinal();
                itemMenus.obj = menuInType.subList(i, endIndex);
                datas.add(itemMenus);
            }
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
        TYPE, MENU
    }

    public class TitleItemWorker implements ItemWorker {
        @Override
        public View newView(int position, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View convertView = inflater.inflate(R.layout.menu_item_title, null, false);
            TypeViewHolder holder = new TypeViewHolder(convertView);
            convertView.setTag(holder);
            return convertView;
        }

        @Override
        public void updateItem(View convertView, Object itemData, ViewGroup parent, int positon) {
            int type = (Integer) itemData - 1;
            TypeViewHolder typeViewHolder = (TypeViewHolder) convertView.getTag();
            if (type == MenuType.RECOMMEND.ordinal()) {
                typeViewHolder.menuItemTypeTv.setText("主厨推荐");
            } else if (type == MenuType.SPECIAL.ordinal()) {
                typeViewHolder.menuItemTypeTv.setText("特色菜");
            } else if (type == MenuType.NEW.ordinal()) {
                typeViewHolder.menuItemTypeTv.setText("本月新菜");
            } else {
                typeViewHolder.menuItemTypeTv.setText("其他");
            }


        }

        @Override
        public void onItemClicked(int position, View itemView, ViewGroup parent, Object itemData) {

        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'menu_item_title.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
        class TypeViewHolder {
            @InjectView(R.id.menu_item_type_tv)
            TextView menuItemTypeTv;

            TypeViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
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
                menuViewHolder.getMenuMateriasTxt(i).setText("原料：" + menu.getInfo());
                menuViewHolder.getMenuPriceTxt(i).setText("售价：¥" + menu.getPrice());
                menuViewHolder.getMenuToOrderTxt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnListWorkerListener != null) {
                            mOnListWorkerListener.onAddToOrderClicked(menu);
                        }
                    }
                });
                menuViewHolder.getMenuToDescTxt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnListWorkerListener != null) {
                            mOnListWorkerListener.onGotoLookDesc(v, menu);
                        }
                    }
                });

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
            @InjectView(R.id.menu_name_tv1)
            TextView menuNameTv1;
            @InjectView(R.id.menu_materias_tv1)
            TextView menuMateriasTv1;
            @InjectView(R.id.menu_price_tv1)
            TextView menuPriceTv1;
            @InjectView(R.id.menu_add_to_order_tv1)
            TextView menuAddToOrderTv1;

            @InjectView(R.id.loc_menu_rl1)
            RelativeLayout locMenuRl1;

            @InjectView(R.id.menu_goto_desc_tv1)
            TextView menuGoToDescTv1;

            @InjectView(R.id.menu_goto_desc_tv2)
            TextView menuGoToDescTv2;

            @InjectView(R.id.menu_name_iv2)
            ImageView menuNameIv2;
            @InjectView(R.id.menu_name_tv2)
            TextView menuNameTv2;
            @InjectView(R.id.menu_materias_tv2)
            TextView menuMateriasTv2;
            @InjectView(R.id.menu_price_tv2)
            TextView menuPriceTv2;
            @InjectView(R.id.menu_add_to_order_tv2)
            TextView menuAddToOrderTv2;

            @InjectView(R.id.loc_menu_rl2)
            RelativeLayout locMenuRl2;

            MenuViewHolder(View view) {
                ButterKnife.inject(this, view);
            }

            RelativeLayout getRlLocMenu(int i) {
                switch (i) {
                    case 0:
                        return locMenuRl1;
                    case 1:
                        return locMenuRl2;
                }
                return  null;

            }

            ImageView getMenuNameIv(int i) {
                switch (i) {
                    case 0:
                        return menuNameIv1;
                    case 1:
                        return menuNameIv2;
                }
                return  null;

            }

            TextView getMenuMateriasTxt(int i) {
                switch (i) {
                    case 0:
                        return menuMateriasTv1;
                    case 1:
                        return menuMateriasTv2;
                }
                return  null;

            }

            TextView getMenuNameTxt(int i) {
                switch (i) {
                    case 0:
                        return menuNameTv1;
                    case 1:
                        return menuNameTv2;
                }
                return  null;

            }
            TextView getMenuPriceTxt(int i) {
                switch (i) {
                    case 0:
                        return menuPriceTv1;
                    case 1:
                        return menuPriceTv2;
                }
                return null;

            }

            TextView getMenuToOrderTxt(int i) {
                switch (i) {
                    case 0:
                        return menuAddToOrderTv1;
                    case 1:
                        return menuAddToOrderTv2;
                }
                return  null;

            }

            TextView getMenuToDescTxt(int i) {
                switch (i) {
                    case 0:
                        return menuGoToDescTv1;
                    case 1:
                        return menuGoToDescTv2;
                }
                return null;

            }

            /*TextView getMenuAddTxt(int i) {
                switch (i) {
                    case 0:
                        return menuCountAddTv1;
                    case 1:
                        return menuCountAddTv2;
                }
                return  null;

            }

            TextView getMenuSubTxt(int i) {
                switch (i) {
                    case 0:
                        return menuCountSubTv1;
                    case 1:
                        return menuCountSubTv2;
                }
                return  null;

            }

            TextView getMenuConfirmTxt(int i) {
                switch (i) {
                    case 0:
                        return menuCountConfirmTv1;
                    case 1:
                        return menuCountConfirmTv2;
                }
                return  null;

            }*/
        }
    }
}
