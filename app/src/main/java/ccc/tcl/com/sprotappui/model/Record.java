package ccc.tcl.com.sprotappui.model;

import java.util.Date;

/**
 * Created by user on 17-9-21.
 */

public class Record {
    private int id;

    /**
     * (yyyy-MM-dd)
     */
    private Date date;

    /**
     *(HH:mm:ss)
     */
    private String time;

    /**
     * 0:步行, 1:跑步, 3:骑行
     */
    private String type;
    private String step;
    private int distance; //单位：ｍ
    private int calorie; //单位：c
    private float mean_speed; //单位：m/s
    private String spent_time; //HH:mm:ss
    private String start_time; //HH:mm:ss
    private String end_time; //HH:mm:ss

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
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

    public float getMean_speed() {
        return mean_speed;
    }

    public void setMean_speed(float mean_speed) {
        this.mean_speed = mean_speed;
    }

    public String getSpent_time() {
        return spent_time;
    }

    public void setSpent_time(String spent_time) {
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
}
