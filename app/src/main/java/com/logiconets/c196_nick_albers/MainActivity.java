package com.logiconets.c196_nick_albers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.logiconets.c196_nick_albers.database.AppRepository;

public class MainActivity extends AppCompatActivity {

    private AppRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRepository = AppRepository.getInstance(getApplicationContext().getApplicationContext());
    }


    public void onClickDisplayTerms(View view) {
        startActivity(new Intent(MainActivity.this, TermsActivity.class));
    }

    public void onClickDisplayCourses(View view) {
        startActivity(new Intent(MainActivity.this, CoursesActivity.class));
    }

    public void onClickDisplayAssessments(View view) {
        startActivity(new Intent(MainActivity.this,AssessmentActivity.class));
    }
    private void addSampleData() {
        mRepository.addSampleData();
    }

    public void onClickAddSampleData(View view) {
        addSampleData();
        Toast.makeText(this.getBaseContext(),"Sample Data Entered",Toast.LENGTH_LONG).show();
        Log.i("MainActivity", "Clicking Fab button");
    }

    public void onClickDeleteData(View view) {
        mRepository.deleteData();
    }


}
