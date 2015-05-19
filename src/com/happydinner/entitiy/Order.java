package com.happydinner.entitiy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 订单类
 * Created by lihb on 15/5/13.
 */
public class Order {

    // 4种状态，未提交， 已提交，未付款，已付款
    enum  OrderStatus {
        NOTSUBMIT, SUBMITED, NOTPAY, PAYED
    }

    private String orderId;

    private List<Menu> menuList;

    private OrderStatus  status;

    private float totalPrice;

    // 订单提交时间
    private Date orderTime;



    // 订单桌号或者房间号码
    private String DeskId;

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


    public String getDeskId() {
        return DeskId;
    }

    public void setDeskId(String deskId) {
        DeskId = deskId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(orderTime);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", menuList=" + menuList +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", orderTime=" + orderTime +
                '}';
    }
}
