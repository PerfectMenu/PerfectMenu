package com.example.perfectmenu;

/**
 * Created by dkflf on 2016-05-30.
 */
public class Info {
    private long id;
    private String name;
    private int priority;

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
