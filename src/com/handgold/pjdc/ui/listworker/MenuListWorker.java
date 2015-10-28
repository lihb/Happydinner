package com.handgold.pjdc.ui.listworker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.handgold.pjdc.R;
import com.handgold.pjdc.base.MyAppContext;
import com.handgold.pjdc.common.list.AbstractListWorker;
import com.handgold.pjdc.entitiy.Menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public final static int OPER_ADD = 0;
    public final static int OPER_SUB = 1;
    public final static int OPER_NONE = -1;

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

    public interface OnListWorkerListener {
        /**
         * 当条目选中状态改变时调用
         */
        void onItemClicked(Object itemData, int index);

        /**
         * 加菜
         */
        void onAddMenuClicked(Object itemData, View view);

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
        public void updateItem(final View convertView, final Object itemData, ViewGroup parent, int positon) {
            List<Menu> menuList = (List<Menu>) itemData;
            final MenuViewHolder menuViewHolder = (MenuViewHolder) convertView.getTag();
            for (int i = 0; i < MAX_MENUS_COUNT_PER_LINE; i++) {
                menuViewHolder.getRlLocMenu(i).setVisibility(View.INVISIBLE);
            }
            for (int i = 0; i < menuList.size(); i++) {
                RelativeLayout rlLocMenu = menuViewHolder.getRlLocMenu(i);
                rlLocMenu.setVisibility(View.VISIBLE);
                boolean animatedFlag = menuViewHolder.getAnimatedFlag(i);
                if (animatedFlag) {
                    setAnimation(rlLocMenu, i);
                    menuViewHolder.setAnimatedFlag(i, false);
                }
                final Menu menu = menuList.get(i);
                //是否显示减号操作按钮和背景
                showSubOper(menuViewHolder, menu.getCount(), i, OPER_NONE);
                menuViewHolder.getMenuNameTxt(i).setText(menu.getName());
                menuViewHolder.getMenuPriceTxt(i).setText("¥" + menu.getPrice());
//                menuViewHolder.getMenuRestaurantName(i).setText(menu.getRestaurantName());
                menuViewHolder.getMenuCount(i).setText("" + menu.getCount());
                final int temp = i;
                //增加数目
                menuViewHolder.getImageViewAdd(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnListWorkerListener != null) {
                            mOnListWorkerListener.onAddMenuClicked(menu, v);
                            showSubOper(menuViewHolder, menu.getCount(), temp, OPER_ADD);
                        }
                    }
                });
                // 减少数目
                menuViewHolder.getImageViewSub(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnListWorkerListener != null) {
                            mOnListWorkerListener.onSubMenuClicked(menu);
                            showSubOper(menuViewHolder, menu.getCount(), temp, OPER_SUB);
                            Log.i("wwwww", " menu.getCount() = " + menu.getCount());
                        }
                    }
                });
                // 查看详情
              /*  menuViewHolder.getMenuImage(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnListWorkerListener != null) {
                            mOnListWorkerListener.onGotoLookDesc(v, menu);
                        }
                    }
                });*/

                menuViewHolder.getMenuDetail(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnListWorkerListener != null) {
                            mOnListWorkerListener.onGotoLookDesc(v, menu);
                        }
                    }
                });

            }

        }

        private void showSubOper(final MenuViewHolder holder, int count, final int index, int oper) {
            if (count > 0) {
                holder.getImageViewSub(index).setVisibility(View.VISIBLE);
                if (count == 1 && oper == OPER_ADD) { // 减号出现动画
                    RotateAnimation rotateAnimation = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setDuration(300);
                    TranslateAnimation translateAnimation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 1.0f, TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                            TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0);
                    translateAnimation.setDuration(300);
                    AnimationSet set = new AnimationSet(false);
                    set.addAnimation(rotateAnimation);
                    set.addAnimation(translateAnimation);
                    holder.getImageViewSub(index).startAnimation(set);
                }

                holder.getRelativeLayoutOperation(index).setBackgroundResource(R.drawable.button_exclude_bg);
                holder.getMenuCount(index).setVisibility(View.VISIBLE);
                holder.getMenuCount(index).setText("" + count);
            } else {
                if (oper == OPER_SUB) {
                    // 减号消失动画
                    if (holder.getImageViewSub(index).getAnimation() != null) {
                        return;
                    }
                    RotateAnimation rotateAnimation = new RotateAnimation(360, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setDuration(300);
                    TranslateAnimation translateAnimation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0.0f, TranslateAnimation.RELATIVE_TO_SELF, 1.5f,
                            TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0);
                    translateAnimation.setDuration(300);
                    AnimationSet set = new AnimationSet(false);
                    set.addAnimation(rotateAnimation);
                    set.addAnimation(translateAnimation);
                    holder.getImageViewSub(index).startAnimation(set);
                }
                holder.getImageViewSub(index).setVisibility(View.GONE);
                holder.getRelativeLayoutOperation(index).setBackground(null);
                holder.getMenuCount(index).setVisibility(View.GONE);
            }
        }

        private void setAnimation(View view, int index) {
            TranslateAnimation transAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, 1.0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.0f, TranslateAnimation.RELATIVE_TO_SELF, 0.0f);
            transAnim.setDuration(600);
            //去掉弹回动画
         /*   CustomAnimation customAnimation = new CustomAnimation();
            customAnimation.setDuration(500);
            customAnimation.setStartOffset(600);*/

            AnimationSet set = new AnimationSet(false);
            set.addAnimation(transAnim);
//            set.addAnimation(customAnimation);
            set.setStartOffset(600 * index);
            view.startAnimation(set);
        }

        @Override
        public void onItemClicked(int position, View itemView, ViewGroup parent, Object itemData) {
           /* if (mOnListWorkerListener != null) {
                mOnListWorkerListener.onItemClicked(((List<Menu>) itemData).get(position), position);
            }*/
        }


        class MenuViewHolder {
            ImageView menuNameIv1;
            TextView menuNameTitle1;
            TextView menuNamePrice1;
//            TextView menuNameRestaurant1;
            RelativeLayout relativeLayoutOperation1;
            RelativeLayout locMenuRl1;
            ImageView menuNameIv2;
            TextView menuNameTitle2;
            TextView menuNamePrice2;
//            TextView menuNameRestaurant2;
            RelativeLayout relativeLayoutOperation2;
            RelativeLayout locMenuRl2;
            ImageView menuNameIv3;
            TextView menuNameTitle3;
            TextView menuNamePrice3;
//            TextView menuNameRestaurant3;
            RelativeLayout relativeLayoutOperation3;
            RelativeLayout locMenuRl3;

            ImageView imageViewAdd1;
            ImageView imageViewAdd2;
            ImageView imageViewAdd3;

            ImageView imageViewSub1;
            ImageView imageViewSub2;
            ImageView imageViewSub3;

            TextView menuCount1;
            TextView menuCount2;
            TextView menuCount3;

            TextView menuDetail1;
            TextView menuDetail2;
            TextView menuDetail3;

            boolean toAnimed1 = true;
            boolean toAnimed2 = true;
            boolean toAnimed3 = true;

            MenuViewHolder(View view) {
                menuNameIv1 = (ImageView) view.findViewById(R.id.menu_name_iv1);
                menuNameIv2 = (ImageView) view.findViewById(R.id.menu_name_iv2);
                menuNameIv3 = (ImageView) view.findViewById(R.id.menu_name_iv3);
                menuNameTitle1 = (TextView) view.findViewById(R.id.menu_name_title1);
                menuNameTitle2 = (TextView) view.findViewById(R.id.menu_name_title2);
                menuNameTitle3 = (TextView) view.findViewById(R.id.menu_name_title3);
                menuNamePrice1 = (TextView) view.findViewById(R.id.menu_name_price1);
                menuNamePrice2 = (TextView) view.findViewById(R.id.menu_name_price2);
                menuNamePrice3 = (TextView) view.findViewById(R.id.menu_name_price3);
             /*   menuNameRestaurant1 = (TextView) view.findViewById(R.id.menu_name_restaurant1);
                menuNameRestaurant2 = (TextView) view.findViewById(R.id.menu_name_restaurant2);
                menuNameRestaurant3 = (TextView) view.findViewById(R.id.menu_name_restaurant3);*/
                relativeLayoutOperation1 = (RelativeLayout) view.findViewById(R.id.relative_operation1);
                relativeLayoutOperation2 = (RelativeLayout) view.findViewById(R.id.relative_operation2);
                relativeLayoutOperation3 = (RelativeLayout) view.findViewById(R.id.relative_operation3);
                locMenuRl1 = (RelativeLayout) view.findViewById(R.id.loc_menu_rl1);
                locMenuRl2 = (RelativeLayout) view.findViewById(R.id.loc_menu_rl2);
                locMenuRl3 = (RelativeLayout) view.findViewById(R.id.loc_menu_rl3);
                imageViewAdd1 = (ImageView) view.findViewById(R.id.imagview_add1);
                imageViewAdd2 = (ImageView) view.findViewById(R.id.imagview_add2);
                imageViewAdd3 = (ImageView) view.findViewById(R.id.imagview_add3);
                imageViewSub1 = (ImageView) view.findViewById(R.id.imagview_sub1);
                imageViewSub2 = (ImageView) view.findViewById(R.id.imagview_sub2);
                imageViewSub3 = (ImageView) view.findViewById(R.id.imagview_sub3);
                menuCount1 = (TextView) view.findViewById(R.id.text_count1);
                menuCount2 = (TextView) view.findViewById(R.id.text_count2);
                menuCount3 = (TextView) view.findViewById(R.id.text_count3);
                menuDetail1 = (TextView) view.findViewById(R.id.menu_detail_1);
                menuDetail2 = (TextView) view.findViewById(R.id.menu_detail_2);
                menuDetail3 = (TextView) view.findViewById(R.id.menu_detail_3);
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

           /* public TextView getMenuRestaurantName(int index) {
                switch (index) {
                    case 0:
                        return menuNameRestaurant1;
                    case 1:
                        return menuNameRestaurant2;
                    case 2:
                        return menuNameRestaurant3;
                }
                return null;
            }*/

            public TextView getMenuCount(int index) {
                switch (index) {
                    case 0:
                        return menuCount1;
                    case 1:
                        return menuCount2;
                    case 2:
                        return menuCount3;
                }
                return null;
            }

            public ImageView getImageViewAdd(int index) {
                switch (index) {
                    case 0:
                        return imageViewAdd1;
                    case 1:
                        return imageViewAdd2;
                    case 2:
                        return imageViewAdd3;
                }
                return null;
            }

            public ImageView getImageViewSub(int index) {
                switch (index) {
                    case 0:
                        return imageViewSub1;
                    case 1:
                        return imageViewSub2;
                    case 2:
                        return imageViewSub3;
                }
                return null;
            }

            public RelativeLayout getRelativeLayoutOperation(int index) {
                switch (index) {
                    case 0:
                        return relativeLayoutOperation1;
                    case 1:
                        return relativeLayoutOperation2;
                    case 2:
                        return relativeLayoutOperation3;
                }
                return null;
            }

            public ImageView getMenuImage(int index) {
                switch (index) {
                    case 0:
                        return menuNameIv1;
                    case 1:
                        return menuNameIv2;
                    case 2:
                        return menuNameIv3;
                }
                return null;
            }

            public boolean getAnimatedFlag(int index) {
                switch (index) {
                    case 0:
                        return toAnimed1;
                    case 1:
                        return toAnimed2;
                    case 2:
                        return toAnimed3;
                }
                return false;
            }

            public void setAnimatedFlag(int index, boolean flag) {
                switch (index) {
                    case 0:
                        toAnimed1 = flag;
                        break;
                    case 1:
                        toAnimed2 = flag;
                        break;
                    case 2:
                        toAnimed3 = flag;
                        break;
                }
            }

            public TextView getMenuDetail(int index) {
                switch (index) {
                    case 0:
                        return menuDetail1;
                    case 1:
                        return menuDetail2;
                    case 2:
                        return menuDetail3;
                }
                return null;
            }
        }
    }

}
