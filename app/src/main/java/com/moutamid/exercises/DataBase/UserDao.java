package com.moutamid.exercises.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User users);

    @Insert
    void insertReminder(Reminder reminder);

     @Query("DELETE FROM Reminder")
    public void deleteReminder();
    @Query("DELETE FROM User")
    public void deleteUser();

    @Query("SELECT * FROM Reminder")
    List<Reminder> getReminders();

    @Query("SELECT * FROM User")
    List<User> getUser();


}
