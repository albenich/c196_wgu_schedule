package com.logiconets.c196_nick_albers.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentEntity assessment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssessmentEntity> assessment);

    @Delete
    void deleteAssessment(AssessmentEntity assessment);

    @Query("DELETE FROM assessment")
    void deleteAll();

    @Query("SELECT * from assessment ORDER BY title DESC")
    LiveData<List<AssessmentEntity>> getAll();

    @Query("SELECT * from assessment WHERE id = :id")
    AssessmentEntity getAssessmentById(int id);

    @Query("SELECT * FROM assessment WHERE courseId = :courseId")
    LiveData<List<AssessmentEntity>> getAssessmentsByCourseId(int courseId);

    @Query("SELECT COUNT(*) FROM assessment")
    int getCount();

}
