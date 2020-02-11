package com.logiconets.c196_nick_albers;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.logiconets.c196_nick_albers.viewmodel.CourseEditorViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.logiconets.c196_nick_albers.utility.Constants.COURSE_ID_KEY;

public class CourseEditorActivity extends AppCompatActivity {

    @BindView(R.id.course_text)
    TextView mTextView;

    private CourseEditorViewModel mViewModel;
    private boolean mNewCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cake);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(CourseEditorViewModel.class);

        mViewModel.mLiveCourse.observe(this, courseEntity ->
                mTextView.setText(courseEntity.getTitle()));

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            setTitle("New Course");
            mNewCourse = true;
        }
        else{
            setTitle("Edit Course");
            int courseId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadData(courseId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            saveAndReturn();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        saveAndReturn();
    }

    private void saveAndReturn(){
        mViewModel.saveCourse(mTextView.getText().toString());
        finish();
    }
}
