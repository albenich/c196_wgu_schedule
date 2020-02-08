package com.logiconets.c196_nick_albers.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity(tableName = "term")
public class TermEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="id")
    private int titleId;

    @ColumnInfo(name="title")
    private String title;

    @ColumnInfo(name="startDate")
    private Date startDate;

    @ColumnInfo(name="endDate")
    private Date endDate;


/*
    private static List<TermEntity> terms = new ArrayList<>();

    public static List<TermEntity> getTerms() {
        return terms;
    }

    public static void setTerms(List<TermEntity> terms) {
        TermEntity.terms = terms;
    }


    public TermEntity() {
        terms.add(this);
    }
*/
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "TermEntity{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate.toString() +
                ", endDate=" + endDate.toString() +
                '}';
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }
}
