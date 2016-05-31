package com.example.perfectmenu;

import java.io.Serializable;

/**
 * Created by dkflf on 2016-05-30.
 */
public class Info implements Serializable{
    private long id;
    private String name;
    private int priority;

    public Info(){}

    public Info(long id, String name, int priority){
        this.id=id;
        this.name=name;
        this.priority=priority;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority){
        this.priority=priority;
    }

    @Override
    public String toString(){
        return String.format("%s%d", name, priority);
    }
}