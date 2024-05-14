//package com.moutamid.exercises.DataBase;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.provider.BaseColumns;
//import android.util.Log;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class ExerciseDbHelper extends SQLiteOpenHelper {
//    public static final int DATABASE_VERSION = 1;
//    public static final String DATABASE_NAME = "ExerciseTracker.db";
//
//    private static final String SQL_CREATE_ENTRIES =
//            "CREATE TABLE " + ExerciseContract.ExerciseEntry.TABLE_NAME + " (" +
//                    ExerciseContract.ExerciseEntry._ID + " INTEGER PRIMARY KEY," +
//                    ExerciseContract.ExerciseEntry.COLUMN_NAME_EXERCISE + " TEXT," +
//                    ExerciseContract.ExerciseEntry.COLUMN_NAME_MINUTES + " INTEGER," +
//                    ExerciseContract.ExerciseEntry.COLUMN_NAME_CALORIES_BURN + " REAL," +
//                    ExerciseContract.ExerciseEntry.COLUMN_NAME_DATE + " TEXT)";
//
//    private static final String SQL_DELETE_ENTRIES =
//            "DROP TABLE IF EXISTS " + ExerciseContract.ExerciseEntry.TABLE_NAME;
//
//    public ExerciseDbHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(SQL_CREATE_ENTRIES);
//    }
//
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(SQL_DELETE_ENTRIES);
//        onCreate(db);
//    }
//
//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        onUpgrade(db, oldVersion, newVersion);
//    }
//
//    public long addExercise(String exerciseName, int minutes, double caloriesBurned) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(ExerciseContract.ExerciseEntry.COLUMN_NAME_EXERCISE, exerciseName);
//        values.put(ExerciseContract.ExerciseEntry.COLUMN_NAME_MINUTES, minutes);
//        values.put(ExerciseContract.ExerciseEntry.COLUMN_NAME_CALORIES_BURN, caloriesBurned);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.ENGLISH);
//        String currentDate = sdf.format(new Date());
//        values.put(ExerciseContract.ExerciseEntry.COLUMN_NAME_DATE, currentDate);
//        Log.d("values", values + " parameterss");
//        return db.insert(ExerciseContract.ExerciseEntry.TABLE_NAME, null, values);
//    }
//
//    public List<Exercise> getAllExercises() {
//        List<Exercise> exerciseList = new ArrayList<>();
//        SQLiteDatabase db = getReadableDatabase();
//        String[] projection = {
//                BaseColumns._ID,
//                ExerciseContract.ExerciseEntry.COLUMN_NAME_EXERCISE,
//                ExerciseContract.ExerciseEntry.COLUMN_NAME_MINUTES,
//                ExerciseContract.ExerciseEntry.COLUMN_NAME_CALORIES_BURN,
//                ExerciseContract.ExerciseEntry.COLUMN_NAME_DATE
//        };
//        String sortOrder = ExerciseContract.ExerciseEntry.COLUMN_NAME_DATE + " DESC";
//        Cursor cursor = db.query(
//                ExerciseContract.ExerciseEntry.TABLE_NAME,
//                projection,
//                null,
//                null,
//                null,
//                null,
//                sortOrder
//        );
//        while (cursor.moveToNext()) {
//            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(ExerciseContract.ExerciseEntry._ID));
//            String exerciseName = cursor.getString(cursor.getColumnIndexOrThrow(ExerciseContract.ExerciseEntry.COLUMN_NAME_EXERCISE));
//            int minutes = cursor.getInt(cursor.getColumnIndexOrThrow(ExerciseContract.ExerciseEntry.COLUMN_NAME_MINUTES));
//            double caloriesBurned = cursor.getDouble(cursor.getColumnIndexOrThrow(ExerciseContract.ExerciseEntry.COLUMN_NAME_CALORIES_BURN));
//            String date = cursor.getString(cursor.getColumnIndexOrThrow(ExerciseContract.ExerciseEntry.COLUMN_NAME_DATE));
//            Exercise exercise = new Exercise(itemId, exerciseName, minutes, caloriesBurned, date);
//            exerciseList.add(exercise);
//        }
//        cursor.close();
//        return exerciseList;
//    }
//
//    public List<Exercise> getExercisesByDate(String date) {
//        List<Exercise> exerciseList = new ArrayList<>();
//        SQLiteDatabase db = getReadableDatabase();
//        String[] projection = {
//                BaseColumns._ID,
//                ExerciseContract.ExerciseEntry.COLUMN_NAME_EXERCISE,
//                ExerciseContract.ExerciseEntry.COLUMN_NAME_MINUTES,
//                ExerciseContract.ExerciseEntry.COLUMN_NAME_CALORIES_BURN,
//                ExerciseContract.ExerciseEntry.COLUMN_NAME_DATE
//        };
//        String selection = ExerciseContract.ExerciseEntry.COLUMN_NAME_DATE + " = ?";
//        String[] selectionArgs = {date};
//        String sortOrder = ExerciseContract.ExerciseEntry.COLUMN_NAME_DATE + " DESC";
//        Cursor cursor = db.query(
//                ExerciseContract.ExerciseEntry.TABLE_NAME,
//                projection,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                sortOrder
//        );
//        while (cursor.moveToNext()) {
//            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(ExerciseContract.ExerciseEntry._ID));
//            String exerciseName = cursor.getString(cursor.getColumnIndexOrThrow(ExerciseContract.ExerciseEntry.COLUMN_NAME_EXERCISE));
//            int minutes = cursor.getInt(cursor.getColumnIndexOrThrow(ExerciseContract.ExerciseEntry.COLUMN_NAME_MINUTES));
//            double caloriesBurned = cursor.getDouble(cursor.getColumnIndexOrThrow(ExerciseContract.ExerciseEntry.COLUMN_NAME_CALORIES_BURN));
//            String exerciseDate = cursor.getString(cursor.getColumnIndexOrThrow(ExerciseContract.ExerciseEntry.COLUMN_NAME_DATE));
//            Exercise exercise = new Exercise(itemId, exerciseName, minutes, caloriesBurned, exerciseDate);
//            exerciseList.add(exercise);
//        }
//        cursor.close();
//        return exerciseList;
//    }
//}
