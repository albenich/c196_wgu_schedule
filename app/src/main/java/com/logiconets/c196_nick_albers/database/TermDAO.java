package com.logiconets.c196_nick_albers.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TermEntity term);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TermEntity> terms);

    @Delete
    void deleteTerm(TermEntity term);

    @Query("DELETE FROM term")
    void deleteAll();

    @Query("SELECT * from term ORDER BY title DESC")
    LiveData<List<TermEntity>> getAll();

    @Query("SELECT * from term WHERE id = :id")
    TermEntity getTermById(int id);

    @Query("SELECT COUNT(*) FROM term")
    int getCount();
}
