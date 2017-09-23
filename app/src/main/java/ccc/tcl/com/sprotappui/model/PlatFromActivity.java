package ccc.tcl.com.sprotappui.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import ccc.tcl.com.sprotappui.utils.TimeTranslator;

/**
 * Created by user on 17-9-7.
 */

public class PlatFromActivity implements  Parcelable {
    private int id;

    private String image_url;
    private String name;
    private String hot_value;
    private String start_time;
    private String end_time;
    private int distance;
    private String address;
    private int sponsor;
    private String joiner;//int to string
    private String details;
    private String notes;
    private String post_time;
    private int at_server_id;
    private String status;


    public PlatFromActivity() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJoiner() {
        return joiner;
    }

    public void setJoiner(String joiner) {
        this.joiner = joiner;
    }

    public String getPost_time() {
        return post_time;
    }


    public int getAt_server_id() {
        return at_server_id;
    }

    public void setAt_server_id(int at_server_id) {
        this.at_server_id = at_server_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private boolean isNew;
    private String left_time;

    protected PlatFromActivity(Parcel in) {
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
        sponsor = in.readInt();
        joiner = in.readString();
        details = in.readString();
        notes = in.readString();
    }


    public static final Creator<PlatFromActivity> CREATOR = new Creator<PlatFromActivity>() {
        @Override
        public PlatFromActivity createFromParcel(Parcel in) {
            return new PlatFromActivity(in);
        }

        @Override
        public PlatFromActivity[] newArray(int size) {
            return new PlatFromActivity[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHot_value() {
        return hot_value;
    }

    public void setHot_value(String hot_value) {
        this.hot_value = hot_value;
    }

    public String getLeft_time() {
        return String.valueOf((TimeTranslator.stringToDate(end_time).getTime()
                - new Date().getTime()/(1000*60*60*24)));
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }


    public int getSponsor() {
        return sponsor;
    }

    public void setSponsor(int sponsor) {
        this.sponsor = sponsor;
    }


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (isNew ? 1 : 0));
        dest.writeString(image_url);
        dest.writeString(name);
        dest.writeString(hot_value);
        dest.writeString(start_time);
        dest.writeString(end_time);
        dest.writeString(left_time);
        dest.writeInt(distance);
        dest.writeString(address);
        dest.writeInt(sponsor);
        dest.writeString(joiner);
        dest.writeString(details);
        dest.writeString(notes);
    }
}
