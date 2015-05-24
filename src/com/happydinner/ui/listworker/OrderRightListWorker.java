package com.happydinner.ui.listworker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happydinner.activity.R;
import com.happydinner.common.list.AbstractListWorker;
import com.happydinner.entitiy.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明：
 *
 * @author Administrator
 * @version 1.0
 * @date 2015/3/19
 */

public class OrderRightListWorker extends AbstractListWorker {

    private Context mContext;

    private List<Menu> mDataList;
    private OnListWorkerListener mOnListWorkerListener;

    public OrderRightListWorker(Context mContext, List<Menu> dataList, OnListWorkerListener mOnListWorkerListener) {
        this.mContext = mContext;
        this.mDataList = dataList;
        this.mOnListWorkerListener = mOnListWorkerListener;
        rebuildDataList();
        rebuildWorkerMap();
    }

    public void setData(List<Menu> dataList) {
        mDataList = dataList;
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

        for (int i = 0; i < mDataList.size(); ++i) {
            ListData itemData = new ListData();
            itemData.type = DataType.MENU.ordinal();
            itemData.obj = mDataList.get(i);
            datas.add(itemData);
        }

        return datas;
    }


    public enum DataType {
        MENU
    }

    public static interface OnListWorkerListener {

        void onItemClick(int index);

        /**
         * 加菜
         */
        void onAddMenuClicked(Object itemData);

        /**
         * 减菜
         */
        void onSubMenuClicked(Object itemData);
    }

    class MenuItemWorker implements ItemWorker {
        OnListWorkerListener mOnListWorkerListener;

        public MenuItemWorker(OnListWorkerListener onListWorkerListener) {
            mOnListWorkerListener = onListWorkerListener;
        }

        @Override
        public View newView(int position, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View convertView = inflater.inflate(R.layout.order_right_item, null, false);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            return convertView;
        }

        @Override
        public void updateItem(View convertView, Object itemData, ViewGroup parent, int position) {
            final Menu menu = (Menu) itemData;
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.orderMenuNameTv.setText(menu.getName());
            //todo 设置菜品图片
            holder.orderMenuMateriasTv.setText(menu.getInfo());
            holder.orderMenuPriceTv1.setText("" + menu.getPrice());
            holder.menuCountConfirmTv1.setText("" + menu.count);

            holder.menuCountSubTv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnListWorkerListener != null) {
                        mOnListWorkerListener.onSubMenuClicked(menu);
                    }

                }
            });

            holder.menuCountAddTv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnListWorkerListener != null) {
                        mOnListWorkerListener.onAddMenuClicked(menu);
                    }
                }
            });
        }

        @Override
        public void onItemClicked(int position, View itemView, ViewGroup parent, Object itemData) {
            if (mOnListWorkerListener != null) {
                mOnListWorkerListener.onItemClick(position);
            }

        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'order_right_item.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
        class ViewHolder {
            @InjectView(R.id.order_menu_name_iv)
            ImageView orderMenuNameIv;
            @InjectView(R.id.order_menu_name_tv)
            TextView orderMenuNameTv;
            @InjectView(R.id.order_menu_materias_tv)
            TextView orderMenuMateriasTv;
            @InjectView(R.id.order_menu_price_tv1)
            TextView orderMenuPriceTv1;
            @InjectView(R.id.order_right_rl)
            RelativeLayout orderRightRl;
            @InjectView(R.id.menu_count_sub_tv1)
            TextView menuCountSubTv1;
            @InjectView(R.id.menu_count_confirm_tv1)
            TextView menuCountConfirmTv1;
            @InjectView(R.id.menu_count_add_tv1)
            TextView menuCountAddTv1;
            @InjectView(R.id.order_menu_count_ll)
            LinearLayout orderMenuCountLl;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }
}
