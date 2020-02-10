package com.logiconets.c196_nick_albers;

import android.content.Intent;
import android.os.Bundle;


import com.logiconets.c196_nick_albers.database.CourseEntity;
import com.logiconets.c196_nick_albers.ui.CourseListAdapter;
import com.logiconets.c196_nick_albers.viewmodel.CourseViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoursesActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @OnClick(R.id.newCourseFab)
    void fabClickHandler(){
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    private List<CourseEntity> courseData = new ArrayList<>();
    private CourseListAdapter mAdapter;
    private CourseViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initViewModel() {
        final Observer<List<CourseEntity>> courseObserver =
                courseEntities -> {
                    courseData.clear();
                    courseData.addAll(courseEntities);

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
