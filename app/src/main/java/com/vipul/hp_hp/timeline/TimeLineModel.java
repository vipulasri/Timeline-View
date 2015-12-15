package com.vipul.hp_hp.timeline;

import java.io.Serializable;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class TimeLineModel implements Serializable{
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
