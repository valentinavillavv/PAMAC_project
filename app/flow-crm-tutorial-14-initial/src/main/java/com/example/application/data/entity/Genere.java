package com.example.application.data.entity;

public enum Genere {
    M,
    F;

    public String getName() {
        if(this==Genere.F)return "F";
        else{return "M";}
    }

}
