package com.snarfapps.aibuddy;

public class Message {
    public enum SENT_BY { ME, AI}
    String message;
    SENT_BY sentBy;

    public Message(String message, SENT_BY sentBy) {
        this.message = message;
        this.sentBy = sentBy;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSentBy(SENT_BY sentBy) {
        this.sentBy = sentBy;
    }
}
