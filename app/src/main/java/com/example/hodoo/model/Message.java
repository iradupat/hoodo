package com.example.hodoo.model;

import java.util.Date;

public class Message {
    private User sender;
    private User receiver;
    private String message;
    private Date timestamp;


    public Message(User sender, User receiver, String message){
        timestamp = new Date();
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver(){
        return receiver;
    }

    public void setReceiver(User user){
        receiver = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString(){
        return message;
    }
}
