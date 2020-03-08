package com.logiconets.c196_nick_albers.utility;

import android.util.Log;

import com.logiconets.c196_nick_albers.database.AssessmentEntity;
import com.logiconets.c196_nick_albers.database.CourseEntity;
import com.logiconets.c196_nick_albers.database.TermEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class PopulateData {

    private static List<TermEntity> terms = new ArrayList<>();
    private static List<CourseEntity> courses = new ArrayList<>();
    private static List<AssessmentEntity> assessments = new ArrayList<>();

    private static void initTerms(){

        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();

        startDate.set(2020,2,1);
        endDate.set(2020,8,1);
        TermEntity t1 = new TermEntity(1,"Term 1", startDate.getTime(),endDate.getTime());

        startDate.add(GregorianCalendar.DATE,3);
        endDate.add(GregorianCalendar.WEEK_OF_YEAR,1);
        TermEntity t2 = new TermEntity(2,"Term 2", startDate.getTime(),endDate.getTime());

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
        CourseEntity c1 = new CourseEntity(1,"C856 User Experience Design",startCourse.getTime(),endCourse.getTime(),"In Progress"
                ,"Dana","615-808-3843","dana.cobbs@wgu.edu","",1,false,false);
        CourseEntity c2 = new CourseEntity(2,"C196 Mobile Applications",startCourse.getTime(),endCourse.getTime(),"In Progress"
                ,"Wanda","385-428-8723","wanda.burwick@wgu.edu","",2,false,false);
        CourseEntity c3 = new CourseEntity(3,"C195 Software II",startCourse.getTime(),endCourse.getTime(),"In Progress"
                ,"Wanda","385-428-8723","wanda.burwick@wgu.edu","",2,false,false);

        if(courses.isEmpty())
            courses.add(c1);
        courses.add(c2);
        courses.add(c3);
    }

    private static void initAssessments(){
        Calendar dueDate = GregorianCalendar.getInstance();

        dueDate.set(2020,6,25);
        AssessmentEntity a1 = new AssessmentEntity("C856 Project","Performance",dueDate.getTime(),1,false);
        AssessmentEntity a2 = new AssessmentEntity("C196 Mobile App","Objective",dueDate.getTime(),2,false);
        AssessmentEntity a3 = new AssessmentEntity("Java Application","Performance",dueDate.getTime(),3,false);
        AssessmentEntity a4 = new AssessmentEntity("Oracle Java Developer","Objective",dueDate.getTime(),3,false);

        if(assessments.isEmpty())
            assessments.add(a1);
            assessments.add(a2);
            assessments.add(a3);
            assessments.add(a4);
    }


    public static List<TermEntity> getTerms(){
        initTerms();
        return terms;
    }

    public static List<CourseEntity> getCourses(){
        initCourses();
        return courses;
    }

    public static List<AssessmentEntity> getAssessments() {
        initAssessments();
        return assessments;
    }
}
