package ccc.tcl.com.sprotappui.internet;

import android.support.annotation.VisibleForTesting;
import android.util.Log;

import java.io.IOException;

import ccc.tcl.com.sprotappui.App;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by user on 17-9-23.
 */

/**
 * ADD UserID and Session to each request
 * ps:login,register api isn't include
 */
public class AuthInterceptor implements Interceptor {

    private static final String TAG = "AuthInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("Connection", "keep-alive")
                .header("user_id", App.userInfo.getId())
                .header("session", App.userInfo.getSession());


        if (original.body() instanceof FormBody ) {
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oldFormBody = (FormBody) original.body();
            for (int i = 0; i < oldFormBody.size(); i++) {
                newFormBody.addEncoded(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
            }
            if (App.userInfo.getId() != null)
                newFormBody.add("user_id", App.userInfo.getId());
            requestBuilder.method(original.method(), newFormBody.build());
        }

        if (original.body().contentLength() == 0){
            FormBody.Builder newFormBody = new FormBody.Builder();
            newFormBody.add("user_id", App.userInfo.getId());
            requestBuilder.method(original.method(), newFormBody.build());
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

    @VisibleForTesting
    @Deprecated
    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

}
