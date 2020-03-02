package com.logiconets.c196_nick_albers;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.logiconets.c196_nick_albers.utility.AlarmController;
import com.logiconets.c196_nick_albers.viewmodel.AssessmentEditorViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.logiconets.c196_nick_albers.utility.Constants.ASSESSMENT_ID_KEY;
import static com.logiconets.c196_nick_albers.utility.Constants.COURSE_ID_KEY;

public class AssessmentEditorActivity extends AppCompatActivity {

    @BindView(R.id.assessment_title)
    TextView mTitle;

    @BindView(R.id.assessment_dueDate)
    TextView mDueDate;

    @BindView(R.id.typeRadio)
    RadioGroup mType;

    @BindView(R.id.assessment_course)
    TextView mCourseId;

    @BindView(R.id.assessment_alarmSwitch)
    Switch mAlarmSwitch;

    AssessmentEditorViewModel mViewModel;
    Boolean mNewAssessment;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Calendar calendar = new GregorianCalendar();
    AlarmController alarmController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_save_black);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        
        initViewModel();

    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(AssessmentEditorViewModel.class);

        Intent intent = getIntent();
        int courseId = intent.getIntExtra(COURSE_ID_KEY,-1);
        Bundle extras = intent.getExtras();

        if(extras == null) {
            setTitle("New Assessment");
            mNewAssessment = true;
        }
        else if(courseId != -1){
            setTitle("New Assessment");
            Log.i("CourseEditor", "CourseId = " + courseId);
            mCourseId.setText(String.valueOf(courseId));
        }
        else{
            setTitle("Edit Assessment");
            int assessmentId = extras.getInt(ASSESSMENT_ID_KEY);
            mViewModel.loadData(assessmentId);
        }

        mViewModel.mLiveAssessment.observe(this, assessmentEntity -> {
            mTitle.setText(assessmentEntity.getTitle());
            if(assessmentEntity.getAssessType().equals("Performance")) {
                mType.check(R.id.performanceRadio);
            } else{
                mType.check(R.id.objectiveRadio);
            }
            mDueDate.setText(sdf.format(assessmentEntity.getDueDate()));
            mCourseId.setText(String.valueOf(assessmentEntity.getCourseId()));
        });


    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            mDueDate.setText(sdf.format(calendar.getTime()));
        }
    };

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
        String typeString = mType.getCheckedRadioButtonId() == R.id.performanceRadio ? "Performance" : "Objective";
        try {
            mViewModel.saveAssessment(mTitle.getText().toString(),typeString,
                    sdf.parse(mDueDate.getText().toString()),Integer.parseInt(mCourseId.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finish();
    }
    @OnClick(R.id.assessment_alarmSwitch)
    public void onClickAlarmSwitch(){
        if(mAlarmSwitch.isChecked()){
            alarmController = new AlarmController("Assessment: " + mTitle.getText().toString(),
                    convertStrToDate(mDueDate.getText().toString()),this);
            alarmController.setAlarm();
        }
        else{
            alarmController.cancelAlarm();
        }
    }

    @OnClick(R.id.assessment_dueDate)
    public void onClickStartDate() {
       try {
            calendar.setTime(sdf.parse(mDueDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        new DatePickerDialog(AssessmentEditorActivity.this,date,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    private Date convertStrToDate(String strDate){
        Date convDate = null;
        try {
            convDate = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convDate;
    }
}
