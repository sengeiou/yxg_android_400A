package com.pcg.yuquangong.model.network.entity;

import android.text.TextUtils;

import com.pcg.yuquangong.model.event.ExitLoginEvent;

import org.greenrobot.eventbus.EventBus;

public class BaseEntity<T> {

    private int flag;
    private String msg;
    private T data;
    private Pagination pagination;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public static class Pagination {
        //"current_page": 1,
        //"per_page": 15,
        //"total": 1

        private int current_page;
        private int per_page;
        private int total;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

    }

    public boolean isSuccess() {
        if (flag == 0 && TextUtils.equals(msg, "Success")) {
            return true;
        } else if (flag == -1 || flag == -2) {
            EventBus.getDefault().post(new ExitLoginEvent());
        }
        return false;
    }

}
