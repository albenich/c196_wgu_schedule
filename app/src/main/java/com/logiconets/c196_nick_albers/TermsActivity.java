package com.logiconets.c196_nick_albers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.logiconets.c196_nick_albers.database.CourseEntity;
import com.logiconets.c196_nick_albers.database.TermsAndCourses;
import com.logiconets.c196_nick_albers.ui.TermListAdapter;
import com.logiconets.c196_nick_albers.viewmodel.CourseViewModel;
import com.logiconets.c196_nick_albers.viewmodel.TermViewModel;

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

public class TermsActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @OnClick(R.id.newTermFab)
    void fabClickHandler(){
        Intent intent = new Intent(this, TermEditorActivity.class);
        startActivity(intent);
    }

    private List<TermsAndCourses> termData = new ArrayList<>();
 //   private List<CourseEntity> courseData = new ArrayList<>();

    private TermListAdapter mAdapter;
    private TermViewModel mViewModel;
 //   private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        initRecyclerView();
        initViewModel();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
   //     Toast.makeText(this,"Size is " + courseViewModel.mCourses.hasActiveObservers(),Toast.LENGTH_LONG).show();
   //     Log.i("CourseData", "CourseData1 size = " + courseData.size());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Settings");
        menu.setHeaderIcon(android.R.drawable.ic_menu_more);
    }

    private void initViewModel() {
        final Observer<List<TermsAndCourses>> termObserver =
                new Observer<List<TermsAndCourses>>() {
                    @Override
                    public void onChanged(List<TermsAndCourses> termEntities) {
                        termData.clear();
                        termData.addAll(termEntities);

                        if (mAdapter == null) {
                            mAdapter = new TermListAdapter(termData,TermsActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                        else
                            mAdapter.notifyDataSetChanged();
                    }
                };
   /*     final Observer<List<CourseEntity>> courseObserver =
                new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courseEntities) {
                courseData.clear();
                courseData.addAll(courseEntities);
                Log.i("CourseData", "CourseData2 size = " + courseData.size());
            }
        };
        Log.i("CourseData", "CourseData3 size = " + courseData.size()); */
        mViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        mViewModel.mTerms.observe(this,termObserver);
/*
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.mCourses.observe(this, courseObserver); */
    }

    private void initRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
      //  termData = mViewModel.mTerms;

    }
}
