package ccc.tcl.com.sprotappui.presenter.presenterimpl;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import ccc.tcl.com.sprotappui.internet.RequestUser;
import ccc.tcl.com.sprotappui.presenter.BasePresenter;
import rx.Observable;


/**
 * Created by user on 17-9-13.
 */

public class UserPresenter extends BasePresenter {
    private RequestUser requestUser;

    private static final String TAG = "UserPresenter";

    @Override
    public void onCreate() {
        super.onCreate();
        requestUser = helper.retrofit.create(RequestUser.class);
    }

    public void userRegister(String phone, String name, String pwd, String code) {
        final Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("name", name);
        map.put("password", pwd);
        map.put("code", code);
        resultObservable = (Observable) requestUser.userRegister(map);
        mCompositeSubscription.add(subscribeData());
    }

    public void userGetAuthCode(@NonNull String phone) {
        resultObservable = (Observable) requestUser.userAuthCode(phone);
        mCompositeSubscription.add(subscribeData());
    }

    public void userLogin(String phone, String pwd, int method) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", pwd);
        map.put("method", String.valueOf(method));
        resultObservable = (Observable) requestUser.userLogin(map);
        mCompositeSubscription.add(subscribeData());
    }

}
