package com.example.hodoo.util;

import java.util.Random;

public class NameBuilder {
    private String generatedName;

    private NameBuilder(String name){
        generatedName = name;
    }
    public static NameBuilder createRandomName(){
        String names = "Milou, Nounou, DOOGO LOVER, Mavos, HOOMAN LOVER, HOOMA DOGO, Alpha, Papy";
        int randomNumber = new Random().nextInt(8);
        String name = names.split(",")[randomNumber];
        NameBuilder builder = new NameBuilder(name);

        return builder;

    }

    public NameBuilder allUpperCase(){
        generatedName = generatedName.toUpperCase();
        return this;
    }

    public NameBuilder allLowerCase(){
        generatedName = generatedName.toLowerCase();
        return this;
    }

    public String buildName(){
        return generatedName;
    }

}
