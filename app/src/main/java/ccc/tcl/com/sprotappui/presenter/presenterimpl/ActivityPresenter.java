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

    public void getActivity(String activity_id){
        resultObservable = requestActivity.getActivity(activity_id);
        mCompositeSubscription.add(subscribeData());
    }

    public void getAll(){
        resultObservable = (Observable) requestActivity.getAll();
        mCompositeSubscription.add(subscribeData());
    }

}
