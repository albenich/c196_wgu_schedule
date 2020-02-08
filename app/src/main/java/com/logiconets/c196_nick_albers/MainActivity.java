package com.logiconets.c196_nick_albers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClickDisplayTerms(View view) {
        startActivity(new Intent(MainActivity.this, TermsActivity.class));
    }

    public void onClickDisplayCourses(View view) {
        startActivity(new Intent(MainActivity.this, CoursesActivity.class));
    }
}
