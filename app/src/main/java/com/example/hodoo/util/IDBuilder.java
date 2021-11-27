package com.example.hodoo.util;

public class IDBuilder{
    private  String ID;
    private  ModelName modelName;
    private  int length;
    
    private IDBuilder(ModelName modelName){
        this.modelName = modelName;
        length = 10;
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

        }else if(modelName.equals(ModelName.COMMENT)){

        }else if(modelName.equals(ModelName.POST)){

        }else if(modelName.equals(ModelName.POST_STATUS)){

        }else if(modelName.equals(ModelName.USER)){

        }
        return "";
    }
    
    
    
}
