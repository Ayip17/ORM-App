package com.arif.ormmahasiswa;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Student.class}, version = 1)
public abstract class StudentDatabase extends RoomDatabase {
    public abstract StudentDao studentDao();

    private static StudentDatabase instance;

    public static synchronized StudentDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            StudentDatabase.class, "student_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}