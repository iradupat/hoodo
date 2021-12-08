package com.example.hodoo.model;


public enum PostStatus {
    SEEN("Wandering"),FOUND("Found"),DEAD("Dead"),LOST("Lost"),RETURNED("Returned");
    private   String text;
    PostStatus(String textIn){
        text = textIn;
    }
    public  String getString(){

        return text;
    }

    public String getTranslatedText(String lng){
        if(lng.equals("fr")){
            if(text.equals("Wandering")){
                return "Chien vu";
            }else if(text.equals("Found")){
                return "Retrouvé";
            }else if(text.equals("Lost")){
                return "Disparu";
            }else if(text.equals("Returned")){
                return "Rendu";
            }
        }
        return text;
    }
}
