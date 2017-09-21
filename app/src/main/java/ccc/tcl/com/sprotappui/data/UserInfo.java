package ccc.tcl.com.sprotappui.data;

/**
 * Created by user on 17-9-14.
 */

public class UserInfo {
    private String id;
    private String name;
    private String phone;
    private String password;
    private String session;
    private String image_url;
    private String im_uid;
    private int login_method = 2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getIm_uid() {
        return im_uid;
    }

    public void setIm_uid(String im_uid) {
        this.im_uid = im_uid;
    }

    public int getLogin_method() {
        return login_method;
    }

    public void setLogin_method(int login_method) {
        this.login_method = login_method;
    }
}
