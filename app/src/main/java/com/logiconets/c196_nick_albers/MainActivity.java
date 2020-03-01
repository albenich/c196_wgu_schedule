package com.logiconets.c196_nick_albers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.logiconets.c196_nick_albers.database.AppRepository;

public class MainActivity extends AppCompatActivity {

    private AppRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRepository = AppRepository.getInstance(getApplicationContext().getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sample_data:
                addSampleData();
                return true;
            case R.id.action_clear_data:
                clearSampleData();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
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
        Toast.makeText(this.getBaseContext(),"Sample Data Entered",Toast.LENGTH_LONG).show();
    }

    public void onClickAddSampleData(View view) {
        addSampleData();
        Log.i("MainActivity", "Clicking Fab button");
    }

    public void clearSampleData() {
        mRepository.deleteData();
        Toast.makeText(this.getBaseContext(),"Sample Data Cleared",Toast.LENGTH_LONG).show();

    }


}
