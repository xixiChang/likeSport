package ccc.tcl.com.sprotappui.constant;

/**
 * Created by user on 17-9-4.
 */

public final class URLConstant {

    public final static int DEFAULT_TIMEOUT = 5;

    //   public final static String Base_Url = "http://119.29.95.86:8080/";
    public final static String Base_Url = "http://10.92.34.142:8080/";


    public final static String User_Register = "/user/register";
    public final static String User_Login = "/user/login";
    public final static String User_Auth_Code = "/user/getauthcode";
    public final static String User_Get_Info = "/user/getuserinfo";
    public final static String User_Update = "/user/modifyInfo";


    /**
     * 活动记录
     */
    public static final String Activity_Post = "/activity/post";
    public static final String Activity_Delay = "/activity/delay";//
    public static final String Activity_Cancel = "/activity/cancel";//
    public static final String Activity_Details = "/activity/details";//
    public static final String Activity_My = "/activity/getMyActivity";
    public static final String Activity_Join = "/activity/join";//
    public static final String Activity_All = "/activity/all";
    public static final String Activity_AllByPage = "/activity/allByPage";
    public static final String Activity_GetJoinerInfo = "/activity/getJoinerInfo";//

    /**
     * 文件上传
     */

    public static final String File_Upload = "/file/image";

    /**
     * 运动记录
     */
    public final static String Record_Upload = "/record/upload";

    public final static String Record_Query_Sum = "/record/query/sum";
    public final static String Record_Query_Month = "/record/query/month";
    public final static String Record_Query_Week = "/record/query/week";
    public final static String Record_Query_Day_ALL = "/record/query/dayall";
    public final static String Record_Query_Details = "/record/query/details";
    public final static String Record_Query_MAX = "/record/query/max";
    public final static String Record_Query_MaxForDay = "/record/query/maxforday";
    public final static String Record_Query_TypeSumDay = "/record/query/gettypesumforday";
    public final static String Record_Query_TypeSumAll = "/record/query/gettypesumall";

    public final static String Record_Rating = "/record/rating";

    public static final String BAICHUAN_APP_KEY = "24624522";
    public static final String BAICHUAN_APP_SECRET = "2992e9309ef7f81e7d845376bd6010a7";
}
