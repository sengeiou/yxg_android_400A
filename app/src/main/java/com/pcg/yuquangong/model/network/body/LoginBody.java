package com.pcg.yuquangong.model.network.body;

public class LoginBody {

    private String mobile;
    private String password;
    private String zcode;

    public LoginBody(String username, String password) {
        this.mobile = username;
        this.password = password;
    }

    public String getUsername() {
        return mobile;
    }

    public void setUsername(String username) {
        this.mobile = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getZcode() {
        return zcode;
    }

    public void setZcode(String zcode) {
        this.zcode = zcode;
    }
}
