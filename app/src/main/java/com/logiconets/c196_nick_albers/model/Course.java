package com.logiconets.c196_nick_albers.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Course {
    private String title;
    private Calendar startDate;
    private Calendar anticipatedEndDate;
    private String status;
    private String cmName;
    private String cmPhone;
    private String cmEmail;
    private List<String> assessments;
    private static List<Course> courses = new ArrayList<>();

    public Course() {
        assessments = new ArrayList<>();
        courses.add(this);
    }

    public Course(String title, Calendar startDate, Calendar anticipatedEndDate, String status, String cmName, String cmPhone, String cmEmail, List<String> assessments) {
        this.title = title;
        this.startDate = startDate;
        this.anticipatedEndDate = anticipatedEndDate;
        this.status = status;
        this.cmName = cmName;
        this.cmPhone = cmPhone;
        this.cmEmail = cmEmail;
        this.assessments = assessments;
    }

    public static List<Course> getCourses() {
        return courses;
    }

    public static void setCourses(List<Course> courses) {
        Course.courses = courses;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getAnticipatedEndDate() {
        return anticipatedEndDate;
    }

    public void setAnticipatedEndDate(Calendar anticipatedEndDate) {
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

    public String getAssessment(int index) {
        return assessments.get(index);
    }

    public void setAssessment(String assessment) {
        this.assessments.add(assessment);
    }

    public List<String> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<String> assessments) {
        this.assessments = assessments;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate.getTime().toString() +
                ", anticipatedEndDate=" + anticipatedEndDate.getTime().toString() +
                ", status='" + status + '\'' +
                ", cmName='" + cmName + '\'' +
                ", cmPhone='" + cmPhone + '\'' +
                ", cmEmail='" + cmEmail + '\'' +
                ", assessments=" + assessments +
                '}';
    }
}
