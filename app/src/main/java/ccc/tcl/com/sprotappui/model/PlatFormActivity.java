package ccc.tcl.com.sprotappui.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.utils.TimeTranslator;
import ccc.tcl.com.sprotappui.utils.Util;

/**
 * Created by user on 17-9-7.
 */

public class PlatFormActivity implements  Parcelable {

    private String user_id = App.userInfo.getId();

    /**
     * 本地生成
     */
    private int id;//本地数据库id，仅用于客户端
    private String image_url;//主题图片url
    private String publish_user_id;//发布者id
    private String name;
    private String details;
    private String start_time;
    private String end_time;
    private String address;
    private int distance;
    private int join_num_all;//设置参与总人数
    private String notes;

    /**
     * 服务端生成
     */
    /**
     Normal = 0;
     Delay = 1;
     Done = 2;
     Cancel = 3;
     */
    private String status;
    private int at_server_id;
    private int join_num;
    private String hot_value;
    private String joiner;
    private String post_time;

    /**
     * 客户端二次生成
     */
    private String reason;

    /**
     * 计算所得
     */
    private boolean isNew;
    private String left_time;


    public PlatFormActivity() {
    }


    protected PlatFormActivity(Parcel in) {
        user_id = in.readString();
        at_server_id = in.readInt();
        id = in.readInt();
        isNew = in.readByte() != 0;
        image_url = in.readString();
        name = in.readString();
        hot_value = in.readString();
        start_time = in.readString();
        end_time = in.readString();
        left_time = in.readString();
        distance = in.readInt();
        address = in.readString();
        publish_user_id = in.readString();
        joiner = in.readString();
        join_num_all = in.readInt();
        details = in.readString();
        notes = in.readString();
    }


    public static final Creator<PlatFormActivity> CREATOR = new Creator<PlatFormActivity>() {
        @Override
        public PlatFormActivity createFromParcel(Parcel in) {
            return new PlatFormActivity(in);
        }

        @Override
        public PlatFormActivity[] newArray(int size) {
            return new PlatFormActivity[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeInt(at_server_id);
        dest.writeInt(id);//
        dest.writeByte((byte) (isNew ? 1 : 0));//
        dest.writeString(image_url);
        dest.writeString(name);
        dest.writeString(hot_value);//
        dest.writeString(start_time);
        dest.writeString(end_time);
        dest.writeString(left_time);//
        dest.writeInt(distance);
        dest.writeString(address);
        dest.writeString(publish_user_id);
        dest.writeString(joiner);//
        dest.writeInt(join_num_all);
        dest.writeString(details);
        dest.writeString(notes);
    }

    public String getUser_id() {
        return App.userInfo.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPublish_user_id() {
        return publish_user_id;
    }

    public void setPublish_user_id(String publish_user_id) {
        this.publish_user_id = publish_user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }


    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public int getJoin_num_all() {
        return join_num_all;
    }

    public void setJoin_num_all(int join_num_all) {
        this.join_num_all = join_num_all;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAt_server_id() {
        return at_server_id;
    }

    public void setAt_server_id(int at_server_id) {
        this.at_server_id = at_server_id;
    }

    public int getJoin_num() {
        return join_num;
    }

    public void setJoin_num(int join_num) {
        this.join_num = join_num;
    }

    public String getHot_value() {
        return hot_value;
    }

    public void setHot_value(String hot_value) {
        this.hot_value = hot_value;
    }

    public String getJoiner() {
        return joiner;
    }

    public void setJoiner(String joiner) {
        this.joiner = joiner;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    /**
     * postTime 小于７２小时
     * @return boolean
     */
    public boolean isNew() {
        Date date = TimeTranslator.stringToDateTime(post_time);
        return (new Date().getTime() - date.getTime())/(1000*60*60*24) <= 72 ;
    }

    /**
     *
     * @return left time
     * time unit:hour
     */
    public String getLeft_time() {
        return String.valueOf((TimeTranslator.stringToDate(end_time).getTime()
                - new Date().getTime()/(1000*60*60*24)));
    }

    @Deprecated
    public String[] getJoinerIdArray(){
        return Util.isEmpty(joiner) ? null : joiner.split("|");
    }

}
