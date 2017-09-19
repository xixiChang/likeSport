package ccc.tcl.com.sprotappui.internet;

import java.util.Map;


import ccc.tcl.com.sprotappui.data.UserInfo;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static ccc.tcl.com.sprotappui.constant.URLConstant.User_Auth_Code;
import static ccc.tcl.com.sprotappui.constant.URLConstant.User_Login;
import static ccc.tcl.com.sprotappui.constant.URLConstant.User_Register;


/**
 * Created by user on 17-9-4.
 */

public interface RequestUser {

    @POST(value = User_Register)
    @FormUrlEncoded
    Observable<ResponseResult<UserInfo>> userRegister(@FieldMap Map<String, String> userInfo);

    @POST(value = User_Login)
    @FormUrlEncoded
    Observable<ResponseResult<UserInfo>> userLogin(@FieldMap Map<String, String> loginMap);

    @POST(value = User_Auth_Code)
    @FormUrlEncoded
    Observable<ResponseResult<UserInfo>> userAuthCode(@Field("phone") String phoneNum);
}
