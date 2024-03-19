package com.example.application.data.entity;

import javax.persistence.Entity;

import org.hibernate.annotations.Formula;

@Entity
public class Status extends AbstractEntity {
    private String name;

    public Status() {
    }
    public Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Formula("(select count(c.id) from Contact c where c.status_id = id)") 
    private int statusCount;

    public int getStatusCount(){
        return statusCount;
    }


    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
