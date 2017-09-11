package ccc.tcl.com.sprotappui.model;

/**
 * Created by user on 17-9-11.
 */

public class DayRate {
    private int userID;
    private String rating;
    private String image_url;
    private String userName;
    private String userDesc;
    private String userDist;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getUserDist() {
        return userDist;
    }

    public void setUserDist(String userDist) {
        this.userDist = userDist;
    }
}
