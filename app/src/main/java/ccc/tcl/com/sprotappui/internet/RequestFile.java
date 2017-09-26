package ccc.tcl.com.sprotappui.internet;

import java.util.Map;

import ccc.tcl.com.sprotappui.data.UserInfo;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

import static ccc.tcl.com.sprotappui.constant.URLConstant.File_Upload;
import static ccc.tcl.com.sprotappui.constant.URLConstant.User_Auth_Code;
import static ccc.tcl.com.sprotappui.constant.URLConstant.User_Login;
import static ccc.tcl.com.sprotappui.constant.URLConstant.User_Register;


/**
 * Created by user on 17-9-4.
 */

public interface RequestFile {

    @POST(value = File_Upload)
    @Multipart
    Observable<ResponseResult> fileUpload(@Part() MultipartBody.Part file,
                                                      @Part("des") RequestBody des);
}
