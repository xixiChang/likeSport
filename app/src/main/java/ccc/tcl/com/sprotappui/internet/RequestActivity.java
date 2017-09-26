package ccc.tcl.com.sprotappui.internet;

import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

import static ccc.tcl.com.sprotappui.constant.URLConstant.Activity_Post;
import static ccc.tcl.com.sprotappui.constant.URLConstant.File_Upload;


/**
 * Created by user on 17-9-4.
 */

public interface RequestActivity {

    @POST(value = Activity_Post)
    Observable<ResponseResult> uploadActivity(@Body PlatFormActivity activity);


}
