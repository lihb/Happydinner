package com.handgold.pjdc.entitiy;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/11/2.
 */
public class GameInfo implements Parcelable {
    private final String TAG = "GameInfo";
    /**
     * 游戏名字
     */
    private String name;

    /**
     * 游戏图片url
     */
    private String picUrl;

    /**
     * 游戏所属分类
     */
    private int type;

    public void setName(String name) {
        this.name = name;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public GameInfo(String name, String picUrl, int type) {
        this.name = name;
        this.picUrl = picUrl;
        this.type = type;
    }


    public GameInfo(JSONObject jobj) {
        // TODO Auto-generated constructor stub
        try {
            name = jobj.optString("name");
            picUrl = jobj.optString("picUrl");
            type = jobj.optInt("type");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "LiveSessionInfo json init exception!");
        }
    }

    public GameInfo(String jsonInfo) {
        // TODO Auto-generated constructor stub
        try {
            JSONObject jobj = new JSONObject(jsonInfo);
            name = jobj.optString("name");
            picUrl = jobj.optString("picUrl");
            type = jobj.optInt("type");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "LiveSessionInfo json init exception!");
        }
    }

    public String toJsonString() {
        String str = "";
        try {
            JSONObject jobj = new JSONObject();
            jobj.put("name", name);
            jobj.put("picUrl", picUrl);
            jobj.put("type", type);
            str = jobj.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(picUrl);
        dest.writeInt(type);

    }
    public static final Parcelable.Creator<GameInfo> CREATOR = new Creator<GameInfo>() {
        @Override
        public GameInfo createFromParcel(Parcel source) {
            GameInfo gameInfo = new GameInfo(source.readString());
            gameInfo.setName(source.readString());
            gameInfo.setPicUrl(source.readString());
            gameInfo.setType(source.readInt());

            return gameInfo;
        }

        @Override
        public GameInfo[] newArray(int size) {
            return new GameInfo[size];
        }
    };
}
