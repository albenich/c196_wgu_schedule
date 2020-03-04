package com.logiconets.c196_nick_albers.database;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.logiconets.c196_nick_albers.database.CourseEntity;
import com.logiconets.c196_nick_albers.database.TermEntity;

import java.util.Date;
import java.util.List;

public class TermsAndCourses {
    @Embedded
    private TermEntity term;
    @Relation(parentColumn = "id", entityColumn = "id")
    private List<CourseEntity> courses;

    public TermsAndCourses(TermEntity term, List<CourseEntity> courses) {
        this.term = term;
        this.courses = courses;
    }

    public TermEntity getTerm() {
        return term;
    }

    public void setTerm(TermEntity term) {
        this.term = term;
    }

    public List<CourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseEntity> courses) {
        this.courses = courses;
    }
}
