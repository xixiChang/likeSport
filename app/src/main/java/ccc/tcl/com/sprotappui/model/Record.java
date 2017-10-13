package ccc.tcl.com.sprotappui.model;

import android.util.JsonReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import ccc.tcl.com.sprotappui.App;

/**
 * Created by user on 17-9-21.
 */

public class Record {
    private String user_id = App.userInfo.getId();

    private int id;

    /**
     * (yyyy-MM-dd)
     */
    private String date;

    /**
     *(HH:mm:ss)
     */
    private String time;

    /**
     * 0:步行, 1:跑步, 3:骑行
     */
    private int type;
    private int step;
    private int distance; //单位：ｍ
    private int calorie; //单位：c
    /*    private float mean_speed; //单位：m/s*/
    private double mean_speed;//单位：m/s
    private int spent_time; //s
    private String start_time; //HH:mm:ss
    private String end_time; //HH:mm:ss


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

/*    public float getMean_speed() {
        return mean_speed;
    }

    public void setMean_speed(float mean_speed) {
        this.mean_speed = mean_speed;
    }*/

    public double getMean_speed() {
        return mean_speed;
    }
    public void setMean_speed(double mean_speed) {
        this.mean_speed = mean_speed;
    }


    public int getSpent_time() {
        return spent_time;
    }

    public void setSpent_time(int spent_time) {
        this.spent_time = spent_time;
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

    public String getUser_id() {
        return App.userInfo.getId();
    }

    @Override
    public String toString() {

        return "Record:[ date=" + date + ", time=" + time + ", type=" + type
                +", step=" + step + ", start_time=" + start_time + ", end_time=" + end_time
                +", spent_time=" + spent_time + ", calorie=" + calorie + ", mean_speed=" + mean_speed
                +", distance=" + distance +"]";
    }
}