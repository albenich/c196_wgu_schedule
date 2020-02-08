package com.logiconets.c196_nick_albers.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TermEntity term);

    @Query("DELETE FROM term")
    void deleteAll();

    @Query("SELECT * from term ORDER BY title")
    LiveData<List<TermEntity>> getTerms();
}
