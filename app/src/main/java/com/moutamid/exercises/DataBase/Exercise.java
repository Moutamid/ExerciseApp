package com.moutamid.exercises.DataBase;

public class Exercise {
    private long id;
    private String exerciseName;
    private int minutes;
    private double caloriesBurned;
    private String date;

    public Exercise(long id, String exerciseName, int minutes, double caloriesBurned, String date) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.minutes = minutes;
        this.caloriesBurned = caloriesBurned;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getMinutes() {
        return minutes;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", exerciseName='" + exerciseName + '\'' +
                ", minutes=" + minutes +
                ", caloriesBurned=" + caloriesBurned +
                ", date='" + date + '\'' +
                '}';
    }
}
