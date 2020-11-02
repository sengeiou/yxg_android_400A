package com.pcg.yuquangong.model.network.body;

//磁头异常 is_ct:1
//主板异常 is_zb:1
public class UploadAlarmBody {

    private int is_ct = -1;
    private int is_zb = -1;

    public UploadAlarmBody() {
    }

    public int getIs_ct() {
        return is_ct;
    }

    public void setIs_ct(int is_ct) {
        this.is_ct = is_ct;
    }

    public int getIs_zb() {
        return is_zb;
    }

    public void setIs_zb(int is_zb) {
        this.is_zb = is_zb;
    }

}
