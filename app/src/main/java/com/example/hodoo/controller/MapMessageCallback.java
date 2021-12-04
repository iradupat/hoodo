package com.example.hodoo.controller;

import com.example.hodoo.model.Message;

import java.util.List;
import java.util.Map;

public interface MapMessageCallback {

    public void onComplete(Map<String, List<Message> > messages);
}
