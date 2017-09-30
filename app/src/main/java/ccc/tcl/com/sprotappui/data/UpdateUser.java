package ccc.tcl.com.sprotappui.data;

import ccc.tcl.com.sprotappui.App;

/**
 * Created by user on 17-9-30.
 */

public class UpdateUser {
    private String user_id = App.userInfo.getId();

    private String image_url;

    private String retain;

    private String name;


    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getRetain() {
        return retain;
    }

    public void setRetain(String retain) {
        this.retain = retain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
