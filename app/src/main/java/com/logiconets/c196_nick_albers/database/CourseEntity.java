package com.logiconets.c196_nick_albers.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "course")
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="id")
    private int courseId;

    @ColumnInfo(name="title")
    private String title;

    @ColumnInfo(name="startDate")
    private Date startDate;

    @ColumnInfo(name="hasStartAlarm")
    private Boolean hasStartAlarm;

    @ColumnInfo(name="endDate")
    private Date anticipatedEndDate;

    @ColumnInfo(name="hasEndAlarm")
    private Boolean hasEndAlarm;

    @ColumnInfo(name="status")
    private String status;

    @ColumnInfo(name="cmName")
    private String cmName;

    @ColumnInfo(name="cmPhone")
    private String cmPhone;

    @ColumnInfo(name="cmEmail")
    private String cmEmail;

    //implementing this as new Entity with a foreign key
    // private List<String> assessments;

    @ColumnInfo(name="notes")
    private String notes;

    @ForeignKey(entity = TermEntity.class, parentColumns = "id", childColumns = "termId")
    @NonNull
    @ColumnInfo(name = "termId")
    private int termId;

    public CourseEntity(int courseId, String title, Date startDate, Date anticipatedEndDate
            , String status, String cmName, String cmPhone, String cmEmail, String notes,int termId
            , boolean hasStartAlarm, boolean hasEndAlarm) {
        this.courseId = courseId;
        this.title = title;
        this.startDate = startDate;
        this.anticipatedEndDate = anticipatedEndDate;
        this.status = status;
        this.cmName = cmName;
        this.cmPhone = cmPhone;
        this.cmEmail = cmEmail;
        this.notes = notes;
        this.termId = termId;
        this.hasStartAlarm = hasStartAlarm;
        this.hasEndAlarm = hasEndAlarm;
    }

    @Ignore
    public CourseEntity(String title, Date startDate, Date anticipatedEndDate, String status
            , String cmName, String cmPhone, String cmEmail, String notes, int termId
            , boolean hasStartAlarm, boolean hasEndAlarm) {
        this.title = title;
        this.startDate = startDate;
        this.anticipatedEndDate = anticipatedEndDate;
        this.status = status;
        this.cmName = cmName;
        this.cmPhone = cmPhone;
        this.cmEmail = cmEmail;
        this.notes = notes;
        this.termId = termId;
        this.hasStartAlarm = hasStartAlarm;
        this.hasEndAlarm = hasEndAlarm;
    }
    @Ignore
    public CourseEntity() {
    }

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

    public Date getAnticipatedEndDate() {
        return anticipatedEndDate;
    }

    public void setAnticipatedEndDate(Date anticipatedEndDate) {
        this.anticipatedEndDate = anticipatedEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCmName() {
        return cmName;
    }

    public void setCmName(String cmName) {
        this.cmName = cmName;
    }

    public String getCmPhone() {
        return cmPhone;
    }

    public void setCmPhone(String cmPhone) {
        this.cmPhone = cmPhone;
    }

    public String getCmEmail() {
        return cmEmail;
    }

    public void setCmEmail(String cmEmail) {
        this.cmEmail = cmEmail;
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate.getTime() +
                ", anticipatedEndDate=" + anticipatedEndDate.getTime() +
                ", status='" + status + '\'' +
                ", cmName='" + cmName + '\'' +
                ", cmPhone='" + cmPhone + '\'' +
                ", cmEmail='" + cmEmail + '\'' +
                ", notes=" + notes + '}';
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public Boolean getHasStartAlarm() {
        return hasStartAlarm;
    }

    public void setHasStartAlarm(Boolean hasStartAlarm) {
        this.hasStartAlarm = hasStartAlarm;
    }

    public Boolean getHasEndAlarm() {
        return hasEndAlarm;
    }

    public void setHasEndAlarm(Boolean hasEndAlarm) {
        this.hasEndAlarm = hasEndAlarm;
    }
}
