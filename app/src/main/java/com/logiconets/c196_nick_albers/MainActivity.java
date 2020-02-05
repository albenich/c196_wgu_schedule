package com.logiconets.c196_nick_albers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.logiconets.c196_nick_albers.model.Course;
import com.logiconets.c196_nick_albers.model.Term;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private void testTerms(){
        Term t1 = new Term();
        t1.setTitle("Term 1");
        t1.setStartDate(Calendar.getInstance());
        t1.setEndDate(Calendar.getInstance());
        t1.getEndDate().set(2020,7,1);

        Term t2 = new Term();
        t2.setTitle("Term 2");
        t2.setStartDate(Calendar.getInstance());
        t2.setEndDate(Calendar.getInstance());
        t2.getStartDate().add(Calendar.MONTH,4);
        t2.getEndDate().set(2020,11,1);

        Toast.makeText(this,t1.toString(),Toast.LENGTH_LONG).show(); //Testing Start Date
        Toast.makeText(this,t2.toString(),Toast.LENGTH_LONG).show(); //Testing End Date
    }

    private void testCourses(){
        Course c1 = new Course("Course 1",Calendar.getInstance(),Calendar.getInstance(),"In Progress"
                ,"Dana","615-808-3843","dana.cobbs@wgu.edu",new ArrayList<String>());
        c1.setAssessment("Objective Assessment 1");
        c1.setAssessment("Performance Assessment 1");
        c1.getAnticipatedEndDate().add(Calendar.MONTH,3);

        Toast.makeText(this,c1.toString(),Toast.LENGTH_LONG).show();
    }

    public void onClickDisplayTerms(View view) {

     //   testTerms();

        startActivity(new Intent(MainActivity.this, TermsActivity.class));
    }

    public void onClickDisplayCourses(View view) {
        testCourses();

        startActivity(new Intent(MainActivity.this, CoursesActivity.class));
    }
}
