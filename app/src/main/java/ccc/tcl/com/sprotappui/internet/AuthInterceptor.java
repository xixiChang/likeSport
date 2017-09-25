package ccc.tcl.com.sprotappui.internet;

import java.io.IOException;

import ccc.tcl.com.sprotappui.App;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by user on 17-9-23.
 */

/**
 * ADD UserID and Session to each request
 * ps:login,register api isn't include
 */
public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request_0 = chain.request();
        HttpUrl httpUrl = request_0.url().newBuilder().
                addQueryParameter("user_id", App.userInfo.getId())
                .addQueryParameter("session", App.userInfo.getSession())
                .build();
        Request request_1 = request_0.newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Connection", "keep-alive")
                .method(request_0.method(), request_0.body())
                .url(httpUrl)
                .build();

        return chain.proceed(request_1);
    }


}
