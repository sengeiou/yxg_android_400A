package com.pcg.yuquangong.model.network.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class MemberListItemEntity implements Parcelable {

    private int id;
    private int member_no;
    private String avatar;
    private String name;
    private int gender;
    private int calendar;
    private String birthday;
    private String avatar_link;
    private String mobile;

    //是否已经被选中
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMember_no() {
        return member_no;
    }

    public void setMember_no(int member_no) {
        this.member_no = member_no;
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

    public String getAvatar_link() {
        return avatar_link;
    }

    public void setAvatar_link(String avatar_link) {
        this.avatar_link = avatar_link;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public MemberListItemEntity() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof MemberListItemEntity) {
            MemberListItemEntity entity = (MemberListItemEntity) obj;
            if (entity != null && id == entity.getId()) {
                return true;
            }
        }
        return super.equals(obj);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.member_no);
        dest.writeString(this.avatar);
        dest.writeString(this.name);
        dest.writeInt(this.gender);
        dest.writeInt(this.calendar);
        dest.writeString(this.birthday);
        dest.writeString(this.avatar_link);
        dest.writeString(this.mobile);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected MemberListItemEntity(Parcel in) {
        this.id = in.readInt();
        this.member_no = in.readInt();
        this.avatar = in.readString();
        this.name = in.readString();
        this.gender = in.readInt();
        this.calendar = in.readInt();
        this.birthday = in.readString();
        this.avatar_link = in.readString();
        this.mobile = in.readString();
        this.checked = in.readByte() != 0;
    }

    public static final Creator<MemberListItemEntity> CREATOR = new Creator<MemberListItemEntity>() {
        @Override
        public MemberListItemEntity createFromParcel(Parcel source) {
            return new MemberListItemEntity(source);
        }

        @Override
        public MemberListItemEntity[] newArray(int size) {
            return new MemberListItemEntity[size];
        }
    };
}
