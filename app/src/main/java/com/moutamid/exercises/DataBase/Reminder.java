package com.moutamid.exercises.DataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "time")
    public String time;
    @ColumnInfo(name = "repeat")
    public String repeat;

    @ColumnInfo(name = "ison")
    public Boolean ison;

    public Reminder() {
    }

    public Reminder(String time, String repeat, Boolean ison) {
        this.time = time;
        this.repeat = repeat;
        this.ison = ison;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public Boolean getIson() {
        return ison;
    }

    public void setIson(Boolean ison) {
        this.ison = ison;
    }
}
