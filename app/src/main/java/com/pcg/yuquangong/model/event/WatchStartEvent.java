package com.pcg.yuquangong.model.event;

public class WatchStartEvent {
    private boolean isStarted;

    public WatchStartEvent(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
