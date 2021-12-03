package com.example.hodoo.controller;

import com.example.hodoo.model.Message;
import com.example.hodoo.model.User;

import java.util.List;

public interface MessageInterface {

    public void createMessage(Message message);
    public List<Message> getInboxMessages(User sender, User receiver);
    public int getMessageCount();

}
