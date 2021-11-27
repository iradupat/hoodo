package com.example.hodoo.model;

public class Message {
    private User sender;
    private User receiver;
    private String message;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }


}
