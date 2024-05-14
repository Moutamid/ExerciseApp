package com.moutamid.exercises.DataBase;


    public class Exercise {
        public  String exerciseName;
        public  int minutes;
        public  double caloriesBurned;
        public  String date;
        public  String user_name;

        public Exercise() {
            // Required empty constructor for Firebase
        }

        public Exercise(String exerciseName, int minutes, double caloriesBurned, String date, String user_name) {
            this.exerciseName = exerciseName;
            this.minutes = minutes;
            this.caloriesBurned = caloriesBurned;
            this.date = date;
            this.user_name = user_name;
        }

        public String getExerciseName() {
            return exerciseName;
        }

        public void setExerciseName(String exerciseName) {
            this.exerciseName = exerciseName;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public double getCaloriesBurned() {
            return caloriesBurned;
        }

        public void setCaloriesBurned(double caloriesBurned) {
            this.caloriesBurned = caloriesBurned;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
