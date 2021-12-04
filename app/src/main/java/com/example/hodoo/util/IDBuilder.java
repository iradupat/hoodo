package com.example.hodoo.util;

import com.example.hodoo.controller.firebase.FireBaseController;
import com.example.hodoo.controller.IntCallback;

import java.util.Random;

public class IDBuilder{
    private  String ID;
    private  int lastCount;
    private  int length;
    private FireBaseController fireBaseController;
    Random random ;


    private IDBuilder(int count){
        lastCount = count;
        length = 10;
        fireBaseController = new FireBaseController();
        random = new Random();
    }


    public static IDBuilder createID(int  lastCount){
        IDBuilder builder = new IDBuilder(lastCount);
        return  builder;
    }



    public IDBuilder setIDLength(int lengthIn){
        this.length = lengthIn;
        return this;
    }

    public String buildID(){
        ID = generateRandomString()+"-"+lastCount;

        return ID;
    }


    public String generateRandomString(){
        // a
        int leftLimit = 97;
        // z
        int rightLimit = 122;
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
