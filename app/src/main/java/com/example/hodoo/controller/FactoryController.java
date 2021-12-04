package com.example.hodoo.controller;

import android.content.Context;

import com.example.hodoo.controller.firebase.FireBaseController;
import com.example.hodoo.controller.room.RoomController;


public abstract class FactoryController {

        public static MessageInterface createMessageController(String storage){
            if(storage.equals("FIREBASE_DB")){
                return new FireBaseController();
            }else if(storage.equals("ROOM_DB")){
                return null;
            }
            return null;
        }

        public static PostInterface createPostController(String storage){
            if(storage.equals("FIREBASE_DB")){
                return new FireBaseController();
            }else if(storage.equals("ROOM_DB")){
                return null;
            }
            return null;
        }

        public static CommentInterface createCommentController(String storage){
            if(storage.equals("FIREBASE_DB")){
                return new FireBaseController();
            }else if(storage.equals("ROOM_DB")){
                return null;
            }
            return null;
        }

    public static PostSuggestionInterface createPostSuggestionController(String storage){
        if(storage.equals("FIREBASE_DB")){
            return new FireBaseController();
        }else if(storage.equals("ROOM_DB")){
            return null;
        }
        return null;
    }

    public static StoreUserInterface createStoreUserController(String storage){
        if(storage.equals("FIREBASE_DB")){
            return null;
        }else if(storage.equals("ROOM_DB")){
            return  new RoomController();
        }
        return null;
    }



}
