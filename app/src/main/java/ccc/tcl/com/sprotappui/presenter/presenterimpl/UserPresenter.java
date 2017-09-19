package ccc.tcl.com.sprotappui.presenter.presenterimpl;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;


import ccc.tcl.com.sprotappui.data.UserInfo;
import ccc.tcl.com.sprotappui.internet.RequestUser;
import ccc.tcl.com.sprotappui.internet.requestImpl.RetrofitHelper;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.Presenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by user on 17-9-13.
 */

public class UserPresenter implements Presenter {
    private RetrofitHelper helper = RetrofitHelper.getInstance();
    private RequestUser requestUser;
    private SportAppView appView;
    private Observable<ResponseResult<UserInfo>> resultObservable;
    private CompositeSubscription mCompositeSubscription;
    private static final String TAG = "UserPresenter";

    @Override
    public void onCreate() {
        requestUser = helper.retrofit.create(RequestUser.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(SportAppView view) {
        appView = view;
    }

    public void userRegister(String phone, String name, String pwd, String code) {
        final Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("name", name);
        map.put("password", pwd);
        map.put("code", code);
        resultObservable = requestUser.userRegister(map);
        mCompositeSubscription.add(subscribeData());
    }

    public void userGetAuthCode(@NonNull String phone) {
        resultObservable = requestUser.userAuthCode(phone);
        mCompositeSubscription.add(subscribeData());
    }

    public void userLogin(String phone, String pwd, int method) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", pwd);
        map.put("method", String.valueOf(method));
        resultObservable = requestUser.userLogin(map);
        mCompositeSubscription.add(subscribeData());
    }

    private Subscription subscribeData() {
        return resultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        appView.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseResult responseResult) {
                        if (responseResult != null) {
                            appView.onSuccess(responseResult);
                            Log.d(TAG, "onNext: " + responseResult.getStatus());
                        }
                    }
                });
    }
}
