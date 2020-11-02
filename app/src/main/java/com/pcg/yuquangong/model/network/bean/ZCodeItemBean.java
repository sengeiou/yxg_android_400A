package com.pcg.yuquangong.model.network.bean;

public class ZCodeItemBean {

    private String zname;
    private int zcode;

    public ZCodeItemBean(String zname, int zcode) {
        this.zname = zname;
        this.zcode = zcode;
    }

    public String getZname() {
        return zname;
    }

    public void setZname(String zname) {
        this.zname = zname;
    }

    public int getZcode() {
        return zcode;
    }

    public void setZcode(int zcode) {
        this.zcode = zcode;
    }

    @Override
    public String toString() {
        return zname;
    }
}
