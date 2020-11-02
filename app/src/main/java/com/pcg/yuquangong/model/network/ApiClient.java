
package com.pcg.yuquangong.model.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gupengcheng on 18/12/30.
 */

public class ApiClient {

    private static Retrofit sRetrofit;
    private static ApiService sApiService;

//    private static final String API_HOST_DEBUG = "http://47.112.15.82/api/v1/"; // 测试地址
    private static final String API_HOST_DEBUG = "https://fsyxgkj.com/api/v1/"; // 测试地址
    private static final String API_HOST_RELEASE = "https://fsyxgkj.com/api/v1/"; // 正式地址

    private static String sToken = "";

    public static void setsToken(String token) {
        sToken = token;
    }

    public static void init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 添加统一的头信息
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .header("Content-Type", "application/json")
//                        .addHeader("Device-Code", PhoneUtils.getIMEI())
                        .header("Device-Code", App.getInstance().getDeviceId())
                        .header("Device-Area", App.getInstance().getMapAdcode())
//                        .header("cookie", "__ancc_token=vyZKDy/ED1MVa0KuDPiwDg==")
//                        .addHeader("Device-Area",App.getInstance().getMapAdcode())
                        .build();
                return chain.proceed(request);
            }
        });
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        OkHttpClient okHttpClient = builder.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        sRetrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static String getBaseUrl() {
        return BuildConfig.DEBUG ? API_HOST_DEBUG : API_HOST_RELEASE;
    }

    public static ApiService getApiService() {
        if (sApiService == null) {
            synchronized (ApiClient.class) {
                if (sApiService == null) {
                    sApiService = sRetrofit.create(ApiService.class);
                }
                return sApiService;
            }
        }
        return sApiService;
    }

    public static void refreshApiClient() {
        init();
        sApiService = null;
    }

}
