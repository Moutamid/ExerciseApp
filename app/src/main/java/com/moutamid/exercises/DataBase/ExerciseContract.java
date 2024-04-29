package com.moutamid.exercises.DataBase;
import android.provider.BaseColumns;

public final class ExerciseContract {
    private ExerciseContract() {}

    public static class ExerciseEntry implements BaseColumns {
        public static final String TABLE_NAME = "exercise";
        public static final String COLUMN_NAME_EXERCISE = "exercise_name";
        public static final String COLUMN_NAME_MINUTES = "minutes";
        public static final String COLUMN_NAME_CALORIES_BURN = "calories_burn";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
