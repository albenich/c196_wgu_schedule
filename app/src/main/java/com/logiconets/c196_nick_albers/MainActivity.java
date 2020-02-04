package com.logiconets.c196_nick_albers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.logiconets.c196_nick_albers.model.Course;
import com.logiconets.c196_nick_albers.model.Term;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        String t1String = t1.getTitle() + ":\nStart Date: " + t1.getStartDate().getTime().toString()
                + "\nEnd Date: " + t1.getEndDate().getTime().toString();
        String t2String = t2.getTitle() + ":\nStart Date: " + t2.getStartDate().getTime().toString()
                + "\nEnd Date: " + t2.getEndDate().getTime().toString();
        Toast.makeText(this,t1String,Toast.LENGTH_LONG).show(); //Testing Start Date
        Toast.makeText(this,t2String,Toast.LENGTH_LONG).show(); //Testing End Date



    }
}
