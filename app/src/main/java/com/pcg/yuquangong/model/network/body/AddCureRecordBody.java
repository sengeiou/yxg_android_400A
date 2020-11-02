package com.pcg.yuquangong.model.network.body;

import java.util.List;

public class AddCureRecordBody {

    private String device_id;
    private int symptom;
    private int comb_mpa;
    private int magnetic;
    private int acupoint;
    private int duration;
    private List<CombHeadBean> comb_head;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<CombHeadBean> getComb_head() {
        return comb_head;
    }

    public void setComb_head(List<CombHeadBean> comb_head) {
        this.comb_head = comb_head;
    }

    public static class CombHeadBean {
        private int id;
        private String name;
        private int is_used;

        public CombHeadBean(int id, String name, int is_used) {
            this.id = id;
            this.name = name;
            this.is_used = is_used;
        }

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
    }
}
