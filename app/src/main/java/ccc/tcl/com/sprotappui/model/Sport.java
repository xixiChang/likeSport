package ccc.tcl.com.sprotappui.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 17-9-7.
 */

public class Sport  implements  Parcelable {
    private int id;
    private boolean isNew;
    private String image_url;
    private String name;
    private String hot_value;
    private String left_time;
    private String start_time;
    private String end_time;
    private int distance;
    private String location;
    private int sponsor;
    private int[] participant;
    private String details;
    private String notes;


    public Sport() {

    }

    protected Sport(Parcel in) {
        id = in.readInt();
        isNew = in.readByte() != 0;
        image_url = in.readString();
        name = in.readString();
        hot_value = in.readString();
        start_time = in.readString();
        end_time = in.readString();
        left_time = in.readString();
        distance = in.readInt();
        location = in.readString();
        sponsor = in.readInt();
        participant = in.createIntArray();
        details = in.readString();
        notes = in.readString();
    }


    public static final Creator<Sport> CREATOR = new Creator<Sport>() {
        @Override
        public Sport createFromParcel(Parcel in) {
            return new Sport(in);
        }

        @Override
        public Sport[] newArray(int size) {
            return new Sport[size];
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
        return left_time;
    }

    public void setLeft_time(String left_time) {
        this.left_time = left_time;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSponsor() {
        return sponsor;
    }

    public void setSponsor(int sponsor) {
        this.sponsor = sponsor;
    }

    public int[] getParticipant() {
        return participant;
    }

    public void setParticipant(int[] participant) {
        this.participant = participant;
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
        dest.writeString(location);
        dest.writeInt(sponsor);
        dest.writeIntArray(participant);
        dest.writeString(details);
        dest.writeString(notes);
    }
}
