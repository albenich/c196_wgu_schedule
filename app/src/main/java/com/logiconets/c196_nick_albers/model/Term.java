package com.logiconets.c196_nick_albers.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Term {
    private String title;
    private Calendar startDate;
    private Calendar endDate;
    private static List<Term> terms = new ArrayList<>();

    public static List<Term> getTerms() {
        return terms;
    }

    public static void setTerms(List<Term> terms) {
        Term.terms = terms;
    }

    public Term() {
        terms.add(this);
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

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Term{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate.getTime().toString() +
                ", endDate=" + endDate.getTime().toString() +
                '}';
    }
}
