package com.pcg.yuquangong.model.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 梳理方式 symptom
 * 梳理头：comb_head
 * 梳理强度：comb_mpa
 * 磁场强度：mangetic
 * 1、2、3，低、中、高
 * ocupoint 穴位，据叶总说是 1到60
 */
public class CureRecordItemEntity implements Parcelable {

    private int cure_id;
    private int symptom;
    private int comb_mpa;
    private int magnetic;
    private int acupoint;
    private String created_at;
    private int duration;
    private int member_id;
    private String avatar;
    private String name;
    private int member_no;
    private int calendar;
    private String birthday;
    private String avatar_link;
    private List<CombHeadBean> comb_head;

    //是否已经被选中
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getCure_id() {
        return cure_id;
    }

    public void setCure_id(int cure_id) {
        this.cure_id = cure_id;
    }

    public int getSymptom() {
        return symptom;
    }

    public void setSymptom(int symptom) {
        this.symptom = symptom;
    }

    public int getComb_mpa() {
        return comb_mpa;
    }

    public void setComb_mpa(int comb_mpa) {
        this.comb_mpa = comb_mpa;
    }

    public int getMagnetic() {
        return magnetic;
    }

    public void setMagnetic(int magnetic) {
        this.magnetic = magnetic;
    }

    public int getAcupoint() {
        return acupoint;
    }

    public void setAcupoint(int acupoint) {
        this.acupoint = acupoint;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
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

    public int getMember_no() {
        return member_no;
    }

    public void setMember_no(int member_no) {
        this.member_no = member_no;
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

    public List<CombHeadBean> getComb_head() {
        return comb_head;
    }

    public void setComb_head(List<CombHeadBean> comb_head) {
        this.comb_head = comb_head;
    }

    public static class CombHeadBean implements Parcelable {
        private int id;
        private String name;
        private int is_used;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIs_used() {
            return is_used;
        }

        public void setIs_used(int is_used) {
            this.is_used = is_used;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeInt(this.is_used);
        }

        public CombHeadBean() {
        }

        public CombHeadBean(int id, String name, int is_used) {
            this.id = id;
            this.name = name;
            this.is_used = is_used;
        }

        protected CombHeadBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.is_used = in.readInt();
        }

        public static final Creator<CombHeadBean> CREATOR = new Creator<CombHeadBean>() {
            @Override
            public CombHeadBean createFromParcel(Parcel source) {
                return new CombHeadBean(source);
            }

            @Override
            public CombHeadBean[] newArray(int size) {
                return new CombHeadBean[size];
            }
        };
    }

    public CureRecordItemEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cure_id);
        dest.writeInt(this.symptom);
        dest.writeInt(this.comb_mpa);
        dest.writeInt(this.magnetic);
        dest.writeInt(this.acupoint);
        dest.writeString(this.created_at);
        dest.writeInt(this.duration);
        dest.writeInt(this.member_id);
        dest.writeString(this.avatar);
        dest.writeString(this.name);
        dest.writeInt(this.member_no);
        dest.writeInt(this.calendar);
        dest.writeString(this.birthday);
        dest.writeString(this.avatar_link);
        dest.writeTypedList(this.comb_head);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected CureRecordItemEntity(Parcel in) {
        this.cure_id = in.readInt();
        this.symptom = in.readInt();
        this.comb_mpa = in.readInt();
        this.magnetic = in.readInt();
        this.acupoint = in.readInt();
        this.created_at = in.readString();
        this.duration = in.readInt();
        this.member_id = in.readInt();
        this.avatar = in.readString();
        this.name = in.readString();
        this.member_no = in.readInt();
        this.calendar = in.readInt();
        this.birthday = in.readString();
        this.avatar_link = in.readString();
        this.comb_head = in.createTypedArrayList(CombHeadBean.CREATOR);
        this.checked = in.readByte() != 0;
    }

    public static final Creator<CureRecordItemEntity> CREATOR = new Creator<CureRecordItemEntity>() {
        @Override
        public CureRecordItemEntity createFromParcel(Parcel source) {
            return new CureRecordItemEntity(source);
        }

        @Override
        public CureRecordItemEntity[] newArray(int size) {
            return new CureRecordItemEntity[size];
        }
    };
}
