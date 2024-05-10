package com.moutamid.exercises.DataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class User {
    public int uid;
    public String gender;
    public String name;
    public int age;
    public String weight;
    public String weight_type;

    public User(String gender, String name, int age, String weight, String weight_type) {
        this.gender = gender;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.weight_type = weight_type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
