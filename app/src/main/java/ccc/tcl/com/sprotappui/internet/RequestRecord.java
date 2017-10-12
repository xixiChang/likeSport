package ccc.tcl.com.sprotappui.internet;

import java.util.List;
import java.util.Map;

import ccc.tcl.com.sprotappui.model.DayRate;
import ccc.tcl.com.sprotappui.model.RateItem;
import ccc.tcl.com.sprotappui.model.Record;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static ccc.tcl.com.sprotappui.constant.URLConstant.Record_Query_Day_ALL;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Record_Query_Details;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Record_Query_MAX;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Record_Query_MaxForDay;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Record_Query_Month;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Record_Query_Sum;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Record_Query_TypeSumAll;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Record_Query_TypeSumDay;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Record_Query_Week;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Record_Rating;
import static ccc.tcl.com.sprotappui.constant.URLConstant.Record_Upload;


/**
 * Created by user on 17-9-4.
 */

public interface RequestRecord {

    @POST(value = Record_Upload)
    Observable<ResponseResult> upload(@Body Record record);

/*    @POST(value = Record_Insert)
    @FormUrlEncoded
    Observable<ResponseResult> insertRecord(@FieldMap Map<String, String> recordInfo);*/

    /**
     * 获取用户所有历史记录的总值
     * map key:step, distance, spent_time, calorie
     * @return map
     */
    @POST(value = Record_Query_Sum)
    @FormUrlEncoded
    Observable<ResponseResult<Map<String, String>>> getAllSum(@Field("u_id") String user_id);


    /**
     * 获取用户历史记录的步数,按月分组
     * map key:month, step
     * @return list
     */
    @POST(value = Record_Query_Month)
    Observable<ResponseResult<List<Map<String, String>>>> getByMonthly();


    /**
     * 获取用户历史记录的步数,按周分组
     * map key:week, step
     * @return list
     */
    @POST(value = Record_Query_Week)
    Observable<ResponseResult<List<Map<String, String>>>> getByWeekly();


    /**
     * 获取用户一天中所有记录
     * @param date (yyyy-MM-dd)
     * @return list
     */
    @POST(value = Record_Query_Day_ALL)
    @FormUrlEncoded
    Observable<ResponseResult<List<Record>>> getDayAll(@Field("date") String date);


    /**
     * 获取记录详情
     * @param id:服务端记录ｉd,非本地记录ｉd
     * @return record
     */
    @POST(value = Record_Query_Details)
    @FormUrlEncoded
    Observable<ResponseResult<Record>> getDetails(@Field("id") String id);



    /**
     * 获取某个type的单次最大值
     * @param type 0:步行, 1:跑步, 3:骑行
     * @return map
     * map key:id, user_id, step, spent_time, distance, calorie
     */
    @POST(value = Record_Query_MAX)
    @FormUrlEncoded
    Observable<ResponseResult<Map<String, String>>> getMax(@Field("type") String type);


    /**
     * 获取某个type的一天记录和最大值
     * @param type 0:步行, 1:跑步, 3:骑行
     * @return map
     * map key:user_id, date, step
     */
    @POST(value = Record_Query_MaxForDay)
    @FormUrlEncoded
    Observable<ResponseResult<Map<String, String>>> getMaxForDay(@Field("type") String type);


    /**
     * 获取排名(当天)
     * @return list<RateItem>
     */
    @POST(value = Record_Rating)
    Observable<ResponseResult<List<RateItem>>> getRating();

    /**
     * 获取某一用户每个类型的distance和(一天)
     * @return map<key, value>
     * key1: 0:步行, 1:跑步, 3:骑行
     * key2: spent_time
     */
    @POST(value = Record_Query_TypeSumDay)
    @FormUrlEncoded
    Observable<ResponseResult<List<Map<String, String>>>> getTypeSumDay(@Field("user_id") String user_id);

    /**
     * 获取某一用户每个类型的distance和(历史)
     * @return map<key, value>
     * key1: 0:步行, 1:跑步, 3:骑行
     */
    @POST(value = Record_Query_TypeSumAll)
    @FormUrlEncoded
    Observable<ResponseResult<Map<String, String>>> getTypeSumAll(@Field("user_id") String user_id);
}
