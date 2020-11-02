package com.pcg.yuquangong.model.network.body;

public class ModifyPswBody {

    private String zcode;
    private String mobile;
    private String code;
    private String password;

    public ModifyPswBody(String mobile, String code, String password) {
        this.mobile = mobile;
        this.code = code;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
