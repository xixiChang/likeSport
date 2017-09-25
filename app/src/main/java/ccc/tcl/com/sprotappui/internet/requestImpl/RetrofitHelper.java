package ccc.tcl.com.sprotappui.internet.requestImpl;

import java.util.concurrent.TimeUnit;

import ccc.tcl.com.sprotappui.constant.URLConstant;
import ccc.tcl.com.sprotappui.internet.AuthInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static ccc.tcl.com.sprotappui.constant.URLConstant.DEFAULT_TIMEOUT;

/**
 * Created by user on 17-9-14.
 */

public class RetrofitHelper {
    private static final String TAG = "RetrofitHelper";
    public Retrofit retrofit;


    private RetrofitHelper() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new AuthInterceptor());

        this.retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(URLConstant.Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private static class SingleHolder {
        private static final RetrofitHelper helper = new RetrofitHelper();
    }

    public static RetrofitHelper getInstance() {
        return SingleHolder.helper;
    }

}
