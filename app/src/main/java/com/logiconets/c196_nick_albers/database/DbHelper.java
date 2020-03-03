package com.logiconets.c196_nick_albers.database;

import androidx.room.Dao;
import androidx.room.Database;

public class DbHelper {

    private TermDAO termDAO;
    private CourseDAO courseDAO;

    public DbHelper(AppDatabase appDatabase){
        termDAO = appDatabase.termDAO();
        courseDAO = appDatabase.courseDAO();
    }


}
