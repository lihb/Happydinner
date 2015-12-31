package com.handgold.pjdc.action;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * 所有服务器协议的默认的统一的生成类。
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "http://your.api-base.url";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .client(SingleOkHttpClient.getInstance())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <T> T createService(Class<T> serviceClass) {

        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}
