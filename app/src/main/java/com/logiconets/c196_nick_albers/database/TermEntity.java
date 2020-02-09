package com.logiconets.c196_nick_albers.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

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

    @Ignore
    public TermEntity() {
    }

    public TermEntity(int titleId, String title, Date startDate, Date endDate) {
        this.titleId = titleId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Ignore
    public TermEntity(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

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
