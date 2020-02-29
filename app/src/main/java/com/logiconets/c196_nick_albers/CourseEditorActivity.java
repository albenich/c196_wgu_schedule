package com.logiconets.c196_nick_albers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;

import com.logiconets.c196_nick_albers.database.AssessmentEntity;
import com.logiconets.c196_nick_albers.database.CourseEntity;
import com.logiconets.c196_nick_albers.ui.AssessmentListAdapter;
import com.logiconets.c196_nick_albers.ui.CourseListAdapter;
import com.logiconets.c196_nick_albers.utility.AlarmReceiver;
import com.logiconets.c196_nick_albers.viewmodel.AssessmentViewModel;
import com.logiconets.c196_nick_albers.viewmodel.CourseEditorViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.logiconets.c196_nick_albers.utility.Constants.COURSE_ID_KEY;

public class CourseEditorActivity extends AppCompatActivity {

    @BindView(R.id.course_title)
    TextView mTitle;

    @BindView(R.id.course_startDate)
    TextView mStartDate;

    @BindView(R.id.course_endDate)
    TextView mEndDate;

    @BindView(R.id.course_status)
    TextView mStatus;

    @BindView(R.id.course_cmName)
    TextView mCmName;

    @BindView(R.id.course_cmEmail)
    TextView mCmEmail;

    @BindView(R.id.course_cmPhone)
    TextView mCmPhone;

    @BindView(R.id.course_notes)
    TextView mNotes;

   // @BindView(R.id.courseAssessmentRecyclerView)
   // RecyclerView mRecyclerView;

    TextView mSelected;
    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    private CourseEditorViewModel mViewModel;
    private AssessmentViewModel mAssessmentViewModel;
    private List<AssessmentEntity> assessmentData = new ArrayList<>();
    private AssessmentListAdapter mAdapter;
    private boolean mNewCourse;
    private NotificationManager mNotificationManager;
    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cake);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
  //      initRecyclerView();
        initViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_couse_editor, menu);
        return true;
    }

    private void initViewModel() {
    //    mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        mViewModel = new ViewModelProvider(this).get(CourseEditorViewModel.class);
/*
        final Observer<List<AssessmentEntity>> assessmentObserver = assessmentEntities -> {
            assessmentData.clear();
            assessmentData.addAll(assessmentEntities);
            int courseId;

            if(mViewModel.mLiveCourse.getValue() != null && assessmentData != null) {
                courseId = mViewModel.mLiveCourse.getValue().getCourseId();
                for (AssessmentEntity assessment : assessmentData) {
                    Log.i("CourseEditor", "assessmentData = " + String.valueOf(assessment.getCourseId()));
                    Log.i("CourseEditor","mViewModel courseId = " + String.valueOf(courseId));
                    if (assessment.getCourseId() != courseId)
                        assessmentData.remove(assessment);
                }
            }


            if (mAdapter == null) {
                mAdapter = new AssessmentListAdapter(assessmentData, CourseEditorActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            } else
                mAdapter.notifyDataSetChanged();
        };

        mAssessmentViewModel.mAssessments.observe(this,assessmentObserver);
*/
        mViewModel.mLiveCourse.observe(this, courseEntity -> {
            mTitle.setText(courseEntity.getTitle());
            mStartDate.setText(sdf.format(courseEntity.getStartDate()));
            mEndDate.setText(sdf.format(courseEntity.getAnticipatedEndDate()));
            mStatus.setText(courseEntity.getStatus());
            mCmName.setText(courseEntity.getCmName());
            mCmEmail.setText(courseEntity.getCmEmail());
            mCmPhone.setText(courseEntity.getCmPhone());
            mNotes.setText(courseEntity.getNotes());
            });

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

  /*  private void initRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }
*/
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            mSelected.setText(sdf.format(calendar.getTime()));
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                saveAndReturn();
                return true;
            case R.id.action_assessments:
                Intent intent=new Intent(this,AssessmentActivity.class);
                intent.putExtra("CourseId",mViewModel.mLiveCourse.getValue().getCourseId());
                intent.putExtra("CourseTitle",mViewModel.mLiveCourse.getValue().getTitle());
                startActivity(intent);
                return true;
            case R.id.action_setAlarm:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        saveAndReturn();
    }

    private void saveAndReturn(){
        try {
            mViewModel.saveCourse(mTitle.getText().toString(),sdf.parse(mStartDate.getText().toString()),
                    sdf.parse(mEndDate.getText().toString()),mStatus.getText().toString(),mCmName.getText().toString(),
                    mCmEmail.getText().toString(),mCmPhone.getText().toString(),mNotes.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finish();
    }



    @OnClick(R.id.course_startDate)
    public void onClickStartDate() {
        mSelected = mStartDate;
        try {
            calendar.setTime(sdf.parse(mStartDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        new DatePickerDialog(CourseEditorActivity.this,date,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.course_endDate)
    public void onClickEndDate() {
        mSelected = mEndDate;
        try {
            calendar.setTime(sdf.parse(mEndDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        new DatePickerDialog(CourseEditorActivity.this,date,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void shareNote(View view) {
        String text = mTitle.getText().toString()
               + " Notes: " + mNotes.getText().toString();
        ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle("Share With")
                .setText(text)
                .startChooser();
    }
}
