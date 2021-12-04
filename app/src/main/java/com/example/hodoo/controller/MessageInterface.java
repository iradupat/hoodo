package com.example.hodoo.controller;

import com.example.hodoo.model.Message;
import com.example.hodoo.model.User;

import java.util.List;
import java.util.Map;

public interface MessageInterface {

    public void createMessage(Message message);
    public void getInboxMessages(MapMessageCallback callback, User sender, User receiver);
    public void getMessageCount(IntCallback callback);

}
