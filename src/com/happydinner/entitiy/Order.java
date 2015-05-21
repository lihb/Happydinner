package com.happydinner.entitiy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 订单类
 * Created by lihb on 15/5/13.
 */
public class Order implements Parcelable {



    // 4种状态，未提交， 已提交，未付款，已付款
    enum  OrderStatus {
        NOTSUBMIT, SUBMITED, NOTPAY, PAYED
    }

    private String orderId;

    private ArrayList<Menu> menuList;

    private OrderStatus  status;

    private float totalPrice;

    // 订单提交时间
    private String orderTime;

    // 订单桌号或者房间号码
    private String deskId;

    public Order() {
        this.orderId = UUID.randomUUID().toString();
        this.menuList = new ArrayList<Menu>();
        this.status = OrderStatus.NOTSUBMIT;
    }

    /**
     *  增加一个菜品
     * @param menu
     */
    public void addMenu(Menu menu) {
        menuList.add(menu);
        totalPrice += menu.getActualPrice();
        menu.count++;
    }

    /**
     * 减少一个菜品
     * @param menu
     */
    public void delMenu(Menu menu) {
        menuList.remove(menu);
        totalPrice -= menu.getActualPrice();
        menu.count--;
    }

    /**
     * 返回订单总价格
     * @return
     */
    public float getTotalPrice() {
        return  totalPrice;
    }

    /**
     * 返回订单总等菜品数量
     * @return
     */
    public int getSize() {
        return menuList.size();
    }

    /**
     * 设置订单状态
     * @param status
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setMenuList(ArrayList<Menu> menuList) {
        this.menuList = menuList;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }


    public String getDeskId() {
        return deskId;
    }

    public void setDeskId(String deskId) {
        this.deskId = deskId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderTime() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return orderTime;
    }

    /**
     * 返回菜品信息
     *
     * @return menuList
     */
    public List<Menu> getMenuList() {
        return menuList;
    }


    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", menuList=" + menuList +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", orderTime=" + orderTime +
                ", deskId=" + deskId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderTime);
        dest.writeString(deskId);
        dest.writeList(menuList);
        dest.writeFloat(totalPrice);
        dest.writeValue(status);
    }

    public static final Parcelable.Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            Order order = new Order();
            order.setOrderTime(source.readString());
            order.setDeskId(source.readString());
            order.setMenuList(source.readArrayList(Menu.class.getClassLoader()));
            order.setTotalPrice(source.readFloat());
            order.setStatus((OrderStatus) source.readValue(null));

            return null;
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
