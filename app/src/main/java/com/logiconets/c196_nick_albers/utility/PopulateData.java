package com.logiconets.c196_nick_albers.utility;

import android.util.Log;

import com.logiconets.c196_nick_albers.database.CourseEntity;
import com.logiconets.c196_nick_albers.database.TermEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class PopulateData {

    private static List<TermEntity> terms = new ArrayList<>();
    private static List<CourseEntity> courses = new ArrayList<>();

    private static void initTerms(){
        TermEntity t1 = new TermEntity();
        TermEntity t2 = new TermEntity();

        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();

        t1.setTitle("TermEntity 1");
        startDate.set(2020,2,1);
        t1.setStartDate(startDate.getTime());
        endDate.set(2020,8,1);
        t1.setEndDate(endDate.getTime());

        t2.setTitle("TermEntity 2");
        startDate.add(GregorianCalendar.DATE,3);
        t2.setStartDate(startDate.getTime());
        endDate.add(GregorianCalendar.WEEK_OF_YEAR,1);
        t2.setEndDate(endDate.getTime());

        if(terms.isEmpty()) {
            terms.add(t1);
            terms.add(t2);
        }
    //    Log.i("PopulateDate", "Added t1 " + t1.getTitle());
    //    Log.i("PopulateDate", "Added t2 " + t2.getTitle());
    }

    private static void initCourses(){
        Calendar startCourse = GregorianCalendar.getInstance();
        Calendar endCourse = GregorianCalendar.getInstance();

        startCourse.set(2020,3,1);
        endCourse.set(2020,6,25);
        CourseEntity c1 = new CourseEntity(1,"CourseEntity 1",startCourse.getTime(),endCourse.getTime(),"In Progress"
                ,"Dana","615-808-3843","dana.cobbs@wgu.edu","");

        if(courses.isEmpty())
            courses.add(c1);
    }

    public static List<TermEntity> getTerms(){
        initTerms();
        return terms;
    }

    public static List<CourseEntity> getCourses(){
        initCourses();
        return courses;
    }
}
