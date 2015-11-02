package com.handgold.pjdc.entitiy;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/11/2.
 */
public class GameInfo {
    private final String TAG = "GameInfo";
    /**
     * 游戏名字
     */
    private String name;

    /**
     * 游戏图片url
     */
    private String picUrl;

    public GameInfo(String name, String picUrl) {
        this.name = name;
        this.picUrl = picUrl;
    }


    public GameInfo(JSONObject jobj) {
        // TODO Auto-generated constructor stub
        try {
            name = jobj.optString("name");
            picUrl = jobj.optString("picUrl");
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
            str = jobj.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }
}
