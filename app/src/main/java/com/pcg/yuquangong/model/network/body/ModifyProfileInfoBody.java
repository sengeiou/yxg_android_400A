package com.pcg.yuquangong.model.network.body;

public class ModifyProfileInfoBody {

    private String avatar;
    private String name;
    private int gender;
    private String birthday;
    private String mobile;
    private int calendar;

    public ModifyProfileInfoBody(String mobile, String name, int gender, String birthday) {
        this.mobile = mobile;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    public int getCalendar() {
        return calendar;
    }

    public void setCalendar(int calendar) {
        this.calendar = calendar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}
