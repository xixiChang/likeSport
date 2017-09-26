package ccc.tcl.com.sprotappui.presenter.presenterimpl;


import android.util.Log;

import ccc.tcl.com.sprotappui.internet.RequestRecord;
import ccc.tcl.com.sprotappui.internet.requestImpl.RetrofitHelper;
import ccc.tcl.com.sprotappui.model.Record;
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
 * Created by user on 17-9-23.
 */

public class RecordPresenter implements Presenter {
    private RetrofitHelper helper = RetrofitHelper.getInstance();
    private RequestRecord requestRecord;
    private SportAppView<ResponseResult> appView;
    private Observable<ResponseResult> resultObservable;
    private CompositeSubscription mCompositeSubscription;

    private static final String TAG = "RecordPresenter";

    @Override
    public void onCreate() {
        requestRecord = helper.retrofit.create(RequestRecord.class);
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

    public void uploadRecord(Record record){
        resultObservable = requestRecord.upload(record);
        Log.d(TAG, "uploadRecord: " + record.toString());
        mCompositeSubscription.add(subscribeData());
    }

    public void getAllSum(){
        resultObservable = (Observable) requestRecord.getAllSum();
        mCompositeSubscription.add(subscribeData());
    }

    public void getByMonthly(){
        resultObservable = (Observable) requestRecord.getByMonthly();
        mCompositeSubscription.add(subscribeData());
    }

    public void getByWeekly(){
        resultObservable = (Observable) requestRecord.getByWeekly();
        mCompositeSubscription.add(subscribeData());
    }


    public void getMax(String type){
        resultObservable = (Observable) requestRecord.getMax(type);
        mCompositeSubscription.add(subscribeData());
    }

    public void getMaxForDay(String type){
        resultObservable = (Observable) requestRecord.getMaxForDay(type);
        mCompositeSubscription.add(subscribeData());
    }


    public void getDayAll(String date){
        resultObservable = (Observable) requestRecord.getDayAll(date);
        mCompositeSubscription.add(subscribeData());
    }

    public void getDetails(String id){
        resultObservable = (Observable) requestRecord.getDetails(id);
        mCompositeSubscription.add(subscribeData());
    }

    public void getRating(){
        resultObservable = (Observable) requestRecord.getRating();
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
                        }
                    }
                });
    }
}
