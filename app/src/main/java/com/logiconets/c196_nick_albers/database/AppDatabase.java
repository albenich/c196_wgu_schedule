package com.logiconets.c196_nick_albers.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version=1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "Database.db";
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile AppDatabase INSTANCE;
    //private static final int NUMBER_OF_THREADS = 4;
    private static final Object LOCK = new Object();

    //static final ExecutorService databaseWriteExecutor =
    //        Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (LOCK){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
/*
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
        }
    };*/
}
