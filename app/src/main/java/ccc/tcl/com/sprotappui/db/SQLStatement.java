package ccc.tcl.com.sprotappui.db;

/**
 * Created by user on 17-9-19.
 */

public class SQLStatement {

    public static final String DBName = "lsdb";

    /**
     * create tables(user, sport_record, activity)
     */
    public static final String CREATE_TABLE_USER = "create table user(" +
            "id integer primary key," +
            "pwd text," +
            "phone text," +
            "name text," +
            "session text," +
            "image_url text," +
            "im_uid text" +
            ")";


    public static final String CREATE_TABLE_SPORT_RECORD = "create table sport_record(" +
            "id integer primary key autoincrement," +
            "date date," +
            "time time," +
            "type integer," +
            "step integer," +
            "distance integer," +
            "calorie integer," +
            "mean_speed float," +
            "spent_time time," +
            "start_time time," +
            "end_time time" +
            ")";


    public static final String CREATE_TABLE_ACTIVITY = "create table activity(" +
            "id integer primary key autoincrement," +
            "name text," +
            "post_user_id integer," +
            "image_url text," +
            "content text," +
            "address text," +
            "post_time timestamp," +
            "start_time date," +
            "end_time date," +
            "goal_dis integer," +
            "joiner text," +
            "status integer," +
            "reason text" +
            ")";

    /**
     * add data
     */
    public static final String addUser = "insert into lsdb.user(" +
            "id,"+
            "pwd,"+
            "phone,"+
            "name,"+
            "session,"+
            "image_url,"+
            "im_uid"+
            ")" +
            "values(" +
            ")";

    public static final String addSportRecord = "insert into lsdb.sport_record(" +
            ")" +
            "values(" +
            ")";
    public static final String addActivity = "insert into lsdb.activity(" +
            ")" +
            "values(" +
            ")";

}
