package com.pcg.yuquangong.model.network.body;

public class AddMemberBody {

    private String avatar;
    private String name;
    private String mobile;
    private int calendar;
    private String birthday;
    private int gender;
    private String zcode = "86";

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getCalendar() {
        return calendar;
    }

    public void setCalendar(int calendar) {
        this.calendar = calendar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

}
