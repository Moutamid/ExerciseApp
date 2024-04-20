package com.moutamid.exercises.Model;

public class SliderData {
    String level_no, level_type, progress;

    public SliderData() {
    }

    public SliderData(String level_no, String level_type, String progress) {
        this.level_no = level_no;
        this.level_type = level_type;
        this.progress = progress;
    }

    public String getLevel_no() {
        return level_no;
    }

    public void setLevel_no(String level_no) {
        this.level_no = level_no;
    }

    public String getLevel_type() {
        return level_type;
    }

    public void setLevel_type(String level_type) {
        this.level_type = level_type;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
