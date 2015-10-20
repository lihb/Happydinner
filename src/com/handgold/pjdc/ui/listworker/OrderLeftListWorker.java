package com.handgold.pjdc.ui.listworker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.handgold.pjdc.R;
import com.handgold.pjdc.common.list.AbstractListWorker;
import com.handgold.pjdc.entitiy.Menu;
import com.handgold.pjdc.util.CommonUtils;

import java.math.BigDecimal;
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

public class OrderLeftListWorker extends AbstractListWorker {

    private Context mContext;

    private List<Menu> mDataList;
    private OnListWorkerListener mOnListWorkerListener;

    public OrderLeftListWorker(Context mContext, List<Menu> dataList, OnListWorkerListener mOnListWorkerListener) {
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
    protected Map<Integer, AbstractListWorker.ItemWorker> constructItemWorkerMap() {
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

    public interface OnListWorkerListener {

        void onItemClick(int index);
    }

    class MenuItemWorker implements ItemWorker {
        OnListWorkerListener mOnListWorkerListener;

        public MenuItemWorker(OnListWorkerListener onListWorkerListener) {
            mOnListWorkerListener = onListWorkerListener;
        }

        @Override
        public View newView(int position, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View convertView = inflater.inflate(R.layout.order_left_item, null, false);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            return convertView;
        }

        @Override
        public void updateItem(View convertView, Object itemData, ViewGroup parent, int position) {
            Menu menu = (Menu) itemData;
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.orderMenuNameTv.setText(menu.getName());
            holder.orderMenuCountTv.setText("X " + menu.getCount());
            float temp = CommonUtils.round(menu.getPrice() * menu.getCount(), 2, BigDecimal.ROUND_HALF_UP);
            String totalPrice = String.valueOf(temp);
            holder.orderMenuPriceTv.setText("" + totalPrice);
        }

        @Override
        public void onItemClicked(int position, View itemView, ViewGroup parent, Object itemData) {
            if (mOnListWorkerListener != null) {
                mOnListWorkerListener.onItemClick(position);
            }

        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'order_left_item.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
        class ViewHolder {
            @InjectView(R.id.order_menu_name_tv)
            TextView orderMenuNameTv;
            @InjectView(R.id.order_menu_count_tv)
            TextView orderMenuCountTv;
            @InjectView(R.id.order_menu_price_tv)
            TextView orderMenuPriceTv;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }
}
