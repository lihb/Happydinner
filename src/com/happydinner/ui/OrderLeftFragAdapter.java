package com.happydinner.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happydinner.activity.R;
import com.happydinner.entitiy.Menu;

import java.util.ArrayList;
import java.util.List;

public class OrderLeftFragAdapter extends BaseAdapter {

    private List<Menu> mData = new ArrayList<Menu>(0);
    private Context mContext;

    public OrderLeftFragAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Menu> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int pos) {
        return mData.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.order_left_item, null);

            ViewHolder viewHolder = new ViewHolder(rowView);
            viewHolder.orderMenuNameTv = (TextView) rowView.findViewById(R.id.order_menu_name_tv);
            viewHolder.orderMenuPriceTv = (TextView) rowView.findViewById(R.id.order_menu_price_tv);
            viewHolder.orderMenuCountTv = (TextView) rowView
                    .findViewById(R.id.order_menu_count_tv);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.orderMenuNameTv.setText(mData.get(position).getName());
        holder.orderMenuCountTv.setText("X " + mData.get(position).getCount());
        holder.orderMenuPriceTv.setText("" + mData.get(position).getPrice() * mData.get(position).getCount());

        return rowView;
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
