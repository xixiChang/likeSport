package ccc.tcl.com.sprotappui.internet;

import java.util.List;
import java.util.Map;

import ccc.tcl.com.sprotappui.data.UserInfo;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_All;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_AllByPage;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_Cancel;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_Delay;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_Details;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_GetJoinerInfo;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_Join;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_My;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_Post;


/**
 * Created by user on 17-9-4.
 */

public interface RequestActivity {

    @POST(value = Activity_Post)
    Observable<ResponseResult> uploadActivity(@Body PlatFormActivity activity);

    @POST(value = Activity_Delay)
    @FormUrlEncoded
    Observable<ResponseResult> delayActivity(@FieldMap Map<String, String> delayInfo);


    @POST(value = Activity_Cancel)
    @FormUrlEncoded
    Observable<ResponseResult> cancelActivity(@Field("at_server_id") String at_server_id,
                                              @Field("reason") String reason);

    @POST(value = Activity_Details)
    @FormUrlEncoded
    Observable<ResponseResult<PlatFormActivity>> getActivity(@Field("at_server_id") String at_server_id);

    @POST(value = Activity_My)
    @FormUrlEncoded
    Observable<ResponseResult<List<PlatFormActivity>>> myActivity(@Field("user_id") String user_id);


    @POST(value = Activity_Join)
    @FormUrlEncoded
    Observable<ResponseResult> joinActivity(@Field("at_server_id") String at_server_id);


    @POST(value = Activity_All)
    @Deprecated
    Observable<ResponseResult<List<PlatFormActivity>>> getAll();

    @POST(value = Activity_AllByPage)
    @FormUrlEncoded
    Observable<ResponseResult<List<PlatFormActivity>>> getAllByPage(@Field("current_size") int current_size);

    @POST(value = Activity_GetJoinerInfo)
    Observable<ResponseResult<List<UserInfo>>> getJoinerInfo(@Body List<String> users);
}
