package ccc.tcl.com.sprotappui.presenter;

import android.util.Log;
import android.widget.Toast;

import ccc.tcl.com.sprotappui.internet.requestImpl.RetrofitHelper;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by user on 17-9-26.
 */

public class BasePresenter implements Presenter {

    private static final String TAG = "BasePresenter";

    protected RetrofitHelper helper = RetrofitHelper.getInstance();
    protected SportAppView appView;
    protected Observable<ResponseResult> resultObservable;
    protected CompositeSubscription mCompositeSubscription;


    @Override
    public void onCreate() {
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


    protected Subscription subscribeData() {
        return resultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e);
                        appView.onRequestError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseResult responseResult) {
                        if (responseResult != null) {
                            appView.onSuccess(responseResult);
                        }
                    }
                });
    }
}
