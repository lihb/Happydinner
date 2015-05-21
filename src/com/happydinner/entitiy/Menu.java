package com.happydinner.entitiy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 菜品类
 * Created by lihb on 15/5/13.
 */
public class Menu implements Parcelable {

    // 菜品名称
    private String name;

    // 菜品介绍图片
    private String imgUrl;

    // 菜品介绍视频
    private String videoUrl;

    // 菜品价格
    private float price;

    // 菜品介绍
    private String info;

    // 菜品优惠信息
    private float discount = 1;

    // 菜品是否被厨房处理,未上菜和已上菜
    private int cooked; // 0--未上菜；1-－已上菜

    // 菜品所属分类
    private int type;

    // 该菜品被点了几份
    public int count = 0 ;

    public Menu(String name) {
        this.name = name;
    }

    public Menu(String name, String imgUrl, String videoUrl, float price, String info, float discount, int cooked, int type) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.videoUrl = videoUrl;
        this.price = price;
        this.info = info;
        this.discount = discount;
        this.cooked = cooked;
        this.type = type;
    }

    /**
     * 获取折扣后等真实价格
     * @return
     */
    public float getActualPrice() {
        return discount * price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int isCooked() {
        return cooked;
    }

    public void setCooked(int cooked) {
        this.cooked = cooked;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imgUrl);
        dest.writeString(videoUrl);
        dest.writeFloat(price);
        dest.writeString(info);
        dest.writeFloat(discount);
        dest.writeInt(cooked);
        dest.writeInt(type);
        dest.writeInt(count);
    }

    public static final Parcelable.Creator<Menu> MENU_CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel source) {
            Menu menu = new Menu(source.readString());
            menu.setImgUrl(source.readString());
            menu.setVideoUrl(source.readString());
            menu.setPrice(source.readFloat());
            menu.setInfo(source.readString());
            menu.setDiscount(source.readFloat());
            menu.setCooked(source.readInt());
            menu.setType(source.readInt());
            menu.setCount(source.readInt());
            return menu;
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };
}
