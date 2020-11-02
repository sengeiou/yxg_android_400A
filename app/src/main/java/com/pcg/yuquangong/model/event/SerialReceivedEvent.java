package com.pcg.yuquangong.model.event;

public class SerialReceivedEvent {

    private String mReceivedMsg;

    public SerialReceivedEvent(String receivedMsg) {
        mReceivedMsg = receivedMsg;
    }

    public String getReceivedMsg() {
        return mReceivedMsg;
    }

    public void setReceivedMsg(String receivedMsg) {
        mReceivedMsg = receivedMsg;
    }

}
