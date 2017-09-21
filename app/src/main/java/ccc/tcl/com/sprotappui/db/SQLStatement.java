package ccc.tcl.com.sprotappui.db;

import ccc.tcl.com.sprotappui.App;

/**
 * Created by user on 17-9-19.
 */

public final class SQLStatement {

    public static final String DBName = "ls.db";
    public static final String RECORD_TABLE_NAME = "sr_" + App.userInfo.getId();
    public static final String ACTIVITY_TABLE_NAME = "ac_" + App.userInfo.getId();

    /**
     * create tables(user, sport_record, activity)
     */
    public static final String CREATE_TABLE_USER = 
            "create table if not exists user(" +
            "id integer primary key," +
            "pwd text," +
            "phone text," +
            "name text," +
            "session text," +
            "image_url text," +
            "im_uid text," +
            "login_method int" +
            ")";


    public static final String CREATE_TABLE_SPORT_RECORD = 
            "create table if not exists "+ RECORD_TABLE_NAME +"(" +
            "id integer primary key autoincrement," +
            "date date," +
            "time time," +
            "type integer," +
            "step integer," +
            "distance integer," +
            "calorie integer," +
            "mean_speed real," +
            "spent_time time," +
            "start_time time," +
            "end_time time" +
            ")";


    public static final String CREATE_TABLE_ACTIVITY =
            "create table if not exists "+ ACTIVITY_TABLE_NAME +"(" +
            "id integer primary key autoincrement," +
            "name text," +
            "post_user_id integer," +
            "image_url text," +
            "content text," +
            "address text," +
            "post_time datetime," +
            "start_time date," +
            "end_time date," +
            "goal_dis integer," +
            "joiner text," +
            "status integer," +
            "reason text," +
            "hot_value text," +
            "at_server_id" +
            ")";

    /**
     * add data
     */
    /**
     * 新增用户信息，登录方式：0:QQ, 1:WeXin, 2:Platform
     */
    public static final String AddUser = 
            "insert into user(" +
            "id,pwd,phone,name,session,image_url,im_uid,login_method" +
            ")" +
            "values(" +
            "?,?,?,?,?,?,?,?)";

    public static final String AddSportRecord = 
            "insert into " +RECORD_TABLE_NAME +"(" +
            "date,time,type,step,distance,calorie,mean_speed,spent_time,start_time,end_time"+
            ")" +
            "values(" +
            "date('now'),time('now'),?,?,?,?,?,?,?,?" +
            ")";

    public static final String AddActivity = 
            "insert into " + ACTIVITY_TABLE_NAME +"(" +
            "name ,post_user_id ,image_url ,content ,address ,post_time ,start_time ," +
            "end_time ,goal_dis ,joiner ,status ,reason, hot_value, at_server_id)" +
            "values(" +
            "?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * select data
     */
    public static final String GetUserByID =
            "select " +
            "* " +
            "from user " +
            "where " +
            "id = ?";

    /**
     * 获取所有记录中以一天为单位的步数最大值
     * 需要一个运动类型参数type=[0|1|2]
     * 返回id,date,最大记录
     */
    public static final String GetRecordMAXForDay = 
            "select " +
            "id, date, max(s) " +
            "from(" +
                " select " +
                " id, date, sum(step) as s" +
                " from " +RECORD_TABLE_NAME +
                " where type = ? " +
                " group by " +
                " date) as t ";

    /**
     * 获取所有记录的一天中单次记录步数最大值
     * 需要一个运动类型参数type=[0|1|2]
     * 返回id,date,最大步数,用时,距离,卡路里
     */
    public static final String GetRecordMAX = 
            "select " +
            " id, max(step), spent_time, distance, calorie" +
            " from" +
             DBName + "." + RECORD_TABLE_NAME  +
            " where " +
            " type = ?";

    /**
     * 通过id查看记录详情
     */
    public static final String GetRecordDetails = 
            "select * from " +RECORD_TABLE_NAME +" where id = ?";

    public static final String GetRecordDayAll = 
            "select * from " +RECORD_TABLE_NAME +" where date = ?";

    public static final String GetRecordDaily = 
            "select " +
            " date, sum(step)" +
            " from " +RECORD_TABLE_NAME  +
            " group by" +
            " date";

    public static final String GetRecordHistoryWeekly = 
            "select " +
            " week(date), sum(step)" +
            " from" +
             DBName + "." + RECORD_TABLE_NAME  +
            " group by week(date)";

    public static final String GetRecordHistoryMonthly = 
            "select " +
            " month(date), sum(step)" +
            " from" +
             DBName + "." + RECORD_TABLE_NAME  +
            " group by month(date)";

    public static final String GetActivityMineAll = 
            "select * from " + ACTIVITY_TABLE_NAME ;

    /**
     * update date
     */

    public static final String UpdateUser =
            "update user " +
            " set image_url = ? " +
            " where id = ?";

    public static final String UpdateActivityTime =
            "update " + ACTIVITY_TABLE_NAME  +
            "set " +
            "end_time = ?," +
            "reason = ?," +
            "status = 2" +
            "where id = ?";

    public static final String UpdateActivityForCancel =
            "update " + ACTIVITY_TABLE_NAME  +
            "set " +
            "reason = ?," +
            "status = 3" +
            "where id = ?";

    public static final String UpdateActivity =
            "update " + ACTIVITY_TABLE_NAME  +
            "set " +
            "joiner = ?," +
            "hot_value = ?," +
            "at_server_id = ?" +
            "where id = ?";
}
