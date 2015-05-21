package com.happydinner.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happydinner.activity.R;
import com.happydinner.entitiy.Menu;

import java.util.ArrayList;
import java.util.List;

public class OrderRightFragAdapter extends BaseAdapter {

    private List<Menu> mData = new ArrayList<Menu>(0);
    private Context mContext;

    public OrderRightFragAdapter(Context context) {
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
            rowView = inflater.inflate(R.layout.order_right_item, null);

            ViewHolder viewHolder = new ViewHolder(rowView);
           /* viewHolder.orderMenuNameTv = (TextView) rowView.findViewById(R.id.order_menu_name_tv);
            viewHolder.orderMenuPriceTv = (TextView) rowView.findViewById(R.id.order_menu_price_tv);
            viewHolder.orderMenuCountTv = (TextView) rowView
                    .findViewById(R.id.order_menu_count_tv);*/
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.orderMenuNameTv.setText(mData.get(position).getName());
        //todo 设置菜品图片
        holder.orderMenuMateriasTv.setText(mData.get(position).getInfo());
        holder.orderMenuPriceTv1.setText("" + mData.get(position).getPrice());
        holder.menuCountSubTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.menuCountAddTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return rowView;
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
