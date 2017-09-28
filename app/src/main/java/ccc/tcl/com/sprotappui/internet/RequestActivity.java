package ccc.tcl.com.sprotappui.internet;

import java.util.List;
import java.util.Map;

import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_All;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_Cancel;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_Delay;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_Details;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_GetJoinerInfo;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_Join;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_My;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_Post;
import static ccc.tcl.com.sprotappui.constant.URLConstant.File_Upload;


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
    Observable<ResponseResult<List<PlatFormActivity>>> myActivity();


    @POST(value = Activity_Join)
    @FormUrlEncoded
    Observable<ResponseResult> joinActivity(@Field("at_server_id") String at_server_id);


    @POST(value = Activity_All)
    Observable<ResponseResult<List<PlatFormActivity>>> getAll();

    @POST(value = Activity_GetJoinerInfo)
    @FormUrlEncoded
    Observable<ResponseResult> getJoinerInfo(@Field("userIdList") String users);
}
