package com.pcg.yuquangong.model.event;

public class ChangeFragmentEvent {
    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_WATCH = 1;
    public static final int FRAGMENT_SETTINGS = 2;
    public ChangeFragmentEvent(int which) {
        this.which = which;
    }

    private int which;

    public int getWhich() {
        return which;
    }

    public void setWhich(int which) {
        this.which = which;
    }
}
