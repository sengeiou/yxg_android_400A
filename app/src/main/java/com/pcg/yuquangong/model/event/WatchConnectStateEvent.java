package com.pcg.yuquangong.model.event;

public class WatchConnectStateEvent {

    private boolean mIsWatchConnected;

    private String mWatchName;

    public WatchConnectStateEvent(boolean isWatchConnected, String watchName) {
        mIsWatchConnected = isWatchConnected;
        mWatchName = watchName;
    }

    public boolean isWatchConnected() {
        return mIsWatchConnected;
    }

    public void setWatchConnected(boolean watchConnected) {
        mIsWatchConnected = watchConnected;
    }

    public String getWatchName() {
        return mWatchName;
    }

    public void setWatchName(String watchName) {
        mWatchName = watchName;
    }

}
