package com.logiconets.c196_nick_albers;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.logiconets.c196_nick_albers.database.CourseEntity;
import com.logiconets.c196_nick_albers.ui.CourseListAdapter;
import com.logiconets.c196_nick_albers.viewmodel.CourseViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.logiconets.c196_nick_albers.utility.Constants.TERM_ID_KEY;

public class CoursesActivity extends AppCompatActivity {

    private List<CourseEntity> courseData = new ArrayList<>();
    private CourseListAdapter mAdapter;
    private CourseViewModel mViewModel;
    private int termId;
    private String termTitle;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @OnClick(R.id.newCourseFab)
    void fabClickHandler() {
        Intent intent = new Intent(this, CourseEditorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Toolbar toolbar = findViewById(R.id.toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();

        Intent intent = getIntent();
        termId = intent.getIntExtra(TERM_ID_KEY,-1);
        termTitle = intent.getStringExtra("TermTitle");
        toolbar.setTitle(termId == -1 ? "Courses" : termTitle + " Courses");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_course, menu);
    }

    private void initViewModel() {
        final Observer<List<CourseEntity>> courseObserver =
                courseEntities -> {
                    courseData.clear();
                    if(termId != -1) {
                        for(CourseEntity course : courseEntities){
                            if(course.getTermId() == termId){
                                courseData.add(course);
                            }
                        }
                    }
                    else {
                        courseData.addAll(courseEntities);
                    }
                    if (mAdapter == null) {
                        mAdapter = new CourseListAdapter(courseData,CoursesActivity.this);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    else
                        mAdapter.notifyDataSetChanged();
                };

        mViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mViewModel.mCourses.observe(this,courseObserver);
    }

    private void initRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
