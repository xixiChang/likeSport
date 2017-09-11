package ccc.tcl.com.sprotappui.model;

import android.widget.ImageView;

/**
 * Created by user on 17-9-7.
 */

public class Sport {
    private int id;
    private boolean isNew;
    private String image_url;
    private String name;
    private String hot_value;
    private String left_time;

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
}
