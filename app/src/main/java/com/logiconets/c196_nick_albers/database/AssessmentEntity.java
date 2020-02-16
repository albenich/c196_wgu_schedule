package com.logiconets.c196_nick_albers.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessment")
public class AssessmentEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int assessmentId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "assessType")
    private String assessType;

    @ColumnInfo(name = "dueDate")
    private Date dueDate;

    @ForeignKey(entity = CourseEntity.class, parentColumns = "id", childColumns = "courseId")
    @NonNull
    @ColumnInfo(name = "courseId")
    private int courseId;

    public AssessmentEntity(int assessmentId, String title, String assessType, Date dueDate, int courseId) {
        this.assessmentId = assessmentId;
        this.title = title;
        this.assessType = assessType;
        this.dueDate = dueDate;
        this.courseId = courseId;
    }

    @Ignore
    public AssessmentEntity(String title, String assessType, Date dueDate, int courseId) {
        this.title = title;
        this.assessType = assessType;
        this.dueDate = dueDate;
        this.courseId = courseId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAssessType() {
        return assessType;
    }

    public void setAssessType(String assessType) {
        this.assessType = assessType;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

}
