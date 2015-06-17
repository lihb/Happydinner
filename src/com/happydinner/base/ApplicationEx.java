package com.happydinner.base;

import android.app.Application;
import android.os.Handler;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ApplicationEx extends Application {

    public static Application app = null;

    public ApplicationEx(){

    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 设置添加全局变量
     *
     * @param key
     * @param object
     */
    public void setInternalActivityParam(String key, Object object) {
        mActivityParamsMap.put(key, object);
    }

    /**
     * 清除全局变量
     */
    public void clearInternalActivityParam() {
        mActivityParamsMap.clear();
    }

    /**
     * 获取全局变量
     * @param key
     * @return
     */
    public Object receiveInternalActivityParam(String key) {
        return mActivityParamsMap.get(key);
    }

    public Executor getMainExecutor() {
        return mMainExecutor;
    }

    public Executor getSerialExecutor() {
        return mSerialExecutor;
    }

    public Executor getTransferExecutor() {
        return mTransferExecutor;
    }

    public Executor getNoTransferExcutor() {
        return mNoTransferExcutor;
    }

    public Executor getPicExcutor() {
        return mPicExecutor;
    }

    /**
     * 实时操作，立即需要有响应的顺序执行器
     */
    public Executor getJITExcutor() {
        return mJITExecutor;
    }

    /**
     * 图片大图浏览任务执行器
     */
    public Executor getPictureDisplayExecutor() {
        return mPictureDisplayExecutor;
    }
    
    /**
     * 用户行为记录的Handler
     */
    public Handler getUserActionHandler() {
        return mUserActionHanler;
    }

    private HashMap<String, Object> mActivityParamsMap = new HashMap<String, Object>();

    private final static Executor mMainExecutor = Executors.newFixedThreadPool(2);

    private final static Executor mSerialExecutor = Executors.newFixedThreadPool(1);

    private final static Executor mTransferExecutor = Executors.newFixedThreadPool(1);

    private final static Executor mNoTransferExcutor = Executors.newFixedThreadPool(1);

    private final static Executor mPicExecutor = Executors.newFixedThreadPool(5);

    /**
     * 图片大图浏览任务执行器
     */
    private final static Executor mPictureDisplayExecutor = Executors.newFixedThreadPool(3);

    /**
     * 实时操作，立即需要有响应的顺序执行器
     */
    private final static Executor mJITExecutor = Executors.newFixedThreadPool(1);
    
    /*
     * 用户行为记录的Handler
     */
    private static Handler mUserActionHanler = null;


}
