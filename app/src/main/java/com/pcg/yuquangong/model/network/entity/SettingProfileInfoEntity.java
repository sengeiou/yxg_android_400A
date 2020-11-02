package com.pcg.yuquangong.model.network.entity;

public class SettingProfileInfoEntity {

    private String avatar;
    private String name;
    private int gender;
    private String birthday;
    private int calendar;
    private String mobile;
    private String avatar_link;

    public int getCalendar() {
        return calendar;
    }

    public void setCalendar(int calendar) {
        this.calendar = calendar;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar_link() {
        return avatar_link;
    }

    public void setAvatar_link(String avatar_link) {
        this.avatar_link = avatar_link;
    }

}
