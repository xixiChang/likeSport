package ccc.tcl.com.sprotappui.presenter.presenterimpl;


import android.util.Log;

import ccc.tcl.com.sprotappui.internet.RequestRecord;
import ccc.tcl.com.sprotappui.model.Record;
import ccc.tcl.com.sprotappui.presenter.BasePresenter;
import rx.Observable;

/**
 * Created by user on 17-9-23.
 */

public class RecordPresenter extends BasePresenter {
    private RequestRecord requestRecord;

    private static final String TAG = "RecordPresenter";

    @Override
    public void onCreate() {
        super.onCreate();
        requestRecord = helper.retrofit.create(RequestRecord.class);
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
}
