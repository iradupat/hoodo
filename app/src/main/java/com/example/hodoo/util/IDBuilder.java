package com.example.hodoo.util;

import com.example.hodoo.controller.FireBaseController;
import com.example.hodoo.controller.UserAuthInterface;

import java.util.List;
import java.util.Random;

public class IDBuilder{
    private  String ID;
    private  ModelName modelName;
    private  int length;
    private FireBaseController fireBaseController;
    Random random ;


    private IDBuilder(ModelName modelName){
        this.modelName = modelName;
        length = 10;
        fireBaseController = new FireBaseController();
        random = new Random();
    }


    public static IDBuilder createID(ModelName modelNameIn){
        IDBuilder builder = new IDBuilder(modelNameIn);
        return  builder;
    }



    public IDBuilder setIDLength(int lengthIn){
        this.length = lengthIn;
        return this;
    }

    public String buildID(){

        if(modelName.equals(ModelName.MESSAGE)){
            ID = generateRandomString()+"-"+fireBaseController.getMessageCount();
        }else if(modelName.equals(ModelName.COMMENT)){

        }else if(modelName.equals(ModelName.POST)){
            ID = generateRandomString()+"-"+fireBaseController.getAllPosts().size();
        } else if(modelName.equals(ModelName.USER)){
            ID = generateRandomString()+"-"+fireBaseController.getUsers().size();
        }
        return ID;
    }


    public String generateRandomString(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }
    
    
    
}
