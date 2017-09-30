package ccc.tcl.com.sprotappui.presenter.presenterimpl;


import java.util.HashMap;
import java.util.Map;

import ccc.tcl.com.sprotappui.internet.RequestActivity;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.presenter.BasePresenter;
import rx.Observable;

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


    public void getMyActivity(String user_id){
        resultObservable = (Observable) requestActivity.myActivity(user_id);
        mCompositeSubscription.add(subscribeData());
    }


    public void joinActivity(String at_server_id){
        resultObservable = (Observable) requestActivity.joinActivity(at_server_id);
        mCompositeSubscription.add(subscribeData());
    }


    @Deprecated
    public void getAll(){
        resultObservable = (Observable) requestActivity.getAll();
        mCompositeSubscription.add(subscribeData());
    }


    public void getAllByPage(int current_size){
        resultObservable = (Observable) requestActivity.getAllByPage(current_size);
        mCompositeSubscription.add(subscribeData());
    }

    /**
     * users = platformActivity.getJoiner
     * @param users
     * @return List<UserInfo> userinfo 仅有id,image_url,name,im_uid字段，　其余为空
     */
    public void getJoinerInfo(String users){
        resultObservable = (Observable) requestActivity.getJoinerInfo(users);
        mCompositeSubscription.add(subscribeData());
    }



}
