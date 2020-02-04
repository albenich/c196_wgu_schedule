package com.logiconets.c196_nick_albers.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course {
    private String title;
    private Date startDate;
    private Date anticipatedEndDate;
    private String status;
    private String cmName;
    private String cmPhone;
    private String cmEmail;
    private List<String> assessments;

    public Course() {
        assessments = new ArrayList<>();
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
}
