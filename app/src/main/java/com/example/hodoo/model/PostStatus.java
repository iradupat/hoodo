package com.example.hodoo.model;

public enum PostStatus {
    SEEN("Wandering"),FOUND("Found"),DEAD("Dead"),LOST("Lost");
    private   String text;
    PostStatus(String textIn){
        text = textIn;
    }
    public  String getString(){

        return text;
    }
}
