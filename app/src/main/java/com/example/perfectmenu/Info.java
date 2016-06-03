package com.example.perfectmenu;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;

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

    public static final Comparator<Info> NAMECOMPARATOR = new Comparator<Info>() {
        private final Collator sCollator = Collator.getInstance();
        @Override
        public int compare(Info lhs, Info rhs) {
            return lhs.name.compareTo(rhs.name);
        }
    };
}