package com.logiconets.c196_nick_albers.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseEntity course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CourseEntity> course);

    @Delete
    void deleteCourse(CourseEntity course);

    @Query("DELETE FROM course")
    void deleteAll();

    @Query("SELECT * from course ORDER BY title DESC")
    LiveData<List<CourseEntity>> getAll();

    @Query("SELECT * from course WHERE id = :id")
    CourseEntity getCourseById(int id);

    @Query("SELECT COUNT(*) FROM course")
    int getCount();

}
