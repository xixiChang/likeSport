package ccc.tcl.com.sprotappui.presenter.presenterimpl;


import ccc.tcl.com.sprotappui.internet.RequestActivity;
import ccc.tcl.com.sprotappui.internet.RequestRecord;
import ccc.tcl.com.sprotappui.internet.requestImpl.RetrofitHelper;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.BasePresenter;
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

public class ActivityPresenter extends BasePresenter {

    private RequestActivity requestActivity;
    private static final String TAG = "ActivityPresenter";

    @Override
    public void onCreate() {
        super.onCreate();
        requestActivity = helper.retrofit.create(RequestActivity.class);
    }



    public void uploadActivity(PlatFormActivity activity){
        resultObservable = requestActivity.uploadActivity(activity);
        mCompositeSubscription.add(subscribeData());
    }


    public void delayActivity(String at_server_id,
                              String reason,
                              String start_time,
                              String end_time){
        Map<String, String> map = new HashMap();
        map.put("at_server_id", at_server_id);
        map.put("reason", reason);
        map.put("start_time", start_time);
        map.put("end_time", end_time);
        resultObservable = requestActivity.delayActivity(map);
        mCompositeSubscription.add(subscribeData());
    }

    public void cancelActivity(String at_server_id,
                               String reason){
        resultObservable = requestActivity.cancelActivity(at_server_id, reason);
        mCompositeSubscription.add(subscribeData());
    }


    public void getActivity(String activity_id){
        resultObservable = (Observable) requestActivity.getActivity(activity_id);
        mCompositeSubscription.add(subscribeData());
    }


    public void getMyActivity(){
        resultObservable = (Observable) requestActivity.myActivity();
        mCompositeSubscription.add(subscribeData());
    }


    public void joinActivity(String at_server_id){
        resultObservable = (Observable) requestActivity.joinActivity(at_server_id);
        mCompositeSubscription.add(subscribeData());
    }



    public void getAll(){
        resultObservable = (Observable) requestActivity.getAll();
        mCompositeSubscription.add(subscribeData());
    }


    /**
     * users = platformActivity.getJoiner
     * @param users
     */
    public void getJoinerInfo(String users){
        resultObservable = (Observable) requestActivity.getJoinerInfo(users);
        mCompositeSubscription.add(subscribeData());
    }



}
