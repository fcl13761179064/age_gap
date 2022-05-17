package com.supersweet.luck.data.net;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.bean.BaseResult;
import com.supersweet.luck.bean.User;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.ui.AbountActivity;
import com.supersweet.luck.ui.GuideActivity;
import com.supersweet.luck.ui.SPlashActivity;
import com.supersweet.luck.ui.SignInActivity;
import com.supersweet.luck.utils.AppManager;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.CustomToast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;
import static com.supersweet.luck.application.MyApplication.getContext;

/**
 * @描述 Retrofit 帮助类
 * @创建 fanchunlei
 * @时间 2020/6/3
 */

/**
 * 动态切换BaseUrl 有四种方案
 * 1.创建配置一样只是BaseUrl 不一样的 Retrofit对象
 * <p>
 * 2 @GET
 * Call<List<Client>> getClientList(@Url String anEmptyString);
 * 给每个接口传全路径作为参数
 * <p>
 * 3 拦截器方案 进行替换
 */
public class RetrofitHelper {
    private static ApiService apiService;
    private static ApiService currentpiAService;

    private RetrofitHelper() {
    }

    public static ApiService getApiService() {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .addConverterFactory(CustomGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constance.getBaseUrl())
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    public static ApiService getLocationApiService() {
        if (currentpiAService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .addConverterFactory(CustomGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constance.GET_LOCATION())
                    .build();
            currentpiAService = retrofit.create(ApiService.class);
        }
        return currentpiAService;
    }

    public static void reset() {
        apiService = null;
    }

    /**
     * 获取 OkHttpClient * * @return OkHttpClient
     */
    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //HttpLoggingInterceptor打印网络日志的方法 默认日志拦截器普通版:OkHttp：
        //自定义拦截器，小写日志
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (MyApplication.getInstance() == null) {
                    System.out.println(message);
                }
                Log.d("okhttp", message);
            }
        });
        builder.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        builder.readTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS);
        builder.retryOnConnectionFailure(true);
        //添加请求头
        builder.addInterceptor(CommonParameterInterceptor);
        //登录失败 重新登录
        builder.addInterceptor(ReloginInterceptor);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);

        return builder.build();
    }

    /**
     * Okhttp3 拦截器
     * 添加公共请求头参数
     */
    private static Interceptor CommonParameterInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            if (MyApplication.getInstance() != null) {
                final String sava_token = SharePreferenceUtils.getString(MyApplication.getInstance(), Constance.SP_Login_Token, null);
                if (sava_token != null) {
                    LogUtils.d("token_1111", sava_token);
                    Request request = chain.request().newBuilder()
                            .header("token", sava_token).build();
                    return chain.proceed(request);
                }
            }
            return chain.proceed(chain.request());
        }
    };

    /**
     * 重新登录拦截器
     * 当code 为5003 或者为 5004 时重新登录
     */
    private static Interceptor ReloginInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //原始接口请求
            Request originalRequest = chain.request();
            //原始接口结果
            Response originalResponse = chain.proceed(originalRequest);

            MediaType mediaType = originalResponse.body().contentType();
            String content = originalResponse.body().string();
            if (originalResponse.isSuccessful()) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.optInt("code");
                    switch (code) {
                        case -1: //authToken过期
                        {//authToken过期
                            jump2Main();

                        }
                        break;
                        case 121002://authToken不正确
                        {
                            LogUtils.d("token_22222", "2222222xxxxxxx");
                            jump2Main();
                        }
                        break;
                    }
                } catch (Exception e) {
                    jump2Main();
                    Log.e("token_refresh", "intercept: ", e);
                }
            }
            return originalResponse.newBuilder().body(ResponseBody.create(mediaType, content)).build();
        }

        private void jump2Main() {
            SharePreferenceUtils.saveString(getContext(), Constance.SP_Login_Token,"");
            SharePreferenceUtils.saveString(getContext(), Constance.SP_Refresh_Token,"");
            SharePreferenceUtils.saveString(getContext(), Constance.SP_HEADER,"");
            //跳转到首页
            Intent intent = new Intent(getContext(), GuideActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getContext().startActivity(intent);
        }
    };

}
