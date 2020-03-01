package com.logiconets.c196_nick_albers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.logiconets.c196_nick_albers.database.AssessmentEntity;
import com.logiconets.c196_nick_albers.ui.AssessmentListAdapter;
import com.logiconets.c196_nick_albers.viewmodel.AssessmentViewModel;

import androidx.annotation.NonNull;
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

public class AssessmentActivity extends AppCompatActivity {

    @BindView(R.id.assessmentRecyclerView)
    RecyclerView mRecyclerView;

    @OnClick(R.id.newAssessmentFab)
    void fabClickHandler(){
        Intent intent = new Intent(this, AssessmentEditorActivity.class);
        startActivity(intent);
    }

    private List<AssessmentEntity> assessmentData = new ArrayList<>();
    private AssessmentListAdapter mAdapter;
    private AssessmentViewModel mViewModel;
    private int courseId;
    String courseTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        Toolbar toolbar = findViewById(R.id.toolbar);

        Intent intent = getIntent();
        courseId = intent.getIntExtra("CourseId",-1);
        courseTitle = intent.getStringExtra("CourseTitle");
        toolbar.setTitle(courseId == -1 ? "Assessments" : courseTitle + " Assessments");

        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();

        Log.i("CourseId", "CourseId is getting sent as " + courseTitle + " " + courseId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void initViewModel() {
        final Observer<List<AssessmentEntity>> assessmentObserver =
                assessmentEntities -> {
                    assessmentData.clear();
                    if(courseId != -1){
                        for(AssessmentEntity assessment:assessmentEntities){
                            if(assessment.getCourseId() == courseId){
                                assessmentData.add(assessment);
                            }
                        }
                    } else{
                        assessmentData.addAll(assessmentEntities);
                    }
                    if (mAdapter == null) {
                        mAdapter = new AssessmentListAdapter(assessmentData,AssessmentActivity.this);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    else
                        mAdapter.notifyDataSetChanged();
                };

        mViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        mViewModel.mAssessments.observe(this,assessmentObserver);
    }

    private void initRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            Log.i("Assess", "CourseId = " + courseId);
            this.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
