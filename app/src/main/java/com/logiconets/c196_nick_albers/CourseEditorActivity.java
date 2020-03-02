package com.logiconets.c196_nick_albers;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.logiconets.c196_nick_albers.utility.AlarmController;
import com.logiconets.c196_nick_albers.viewmodel.CourseEditorViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    @BindView(R.id.course_start_alarmSwitch)
    Switch mStartAlarm;

    @BindView(R.id.course_end_alarmSwitch)
    Switch mEndAlarm;

    TextView mSelected;
    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    int mTermId;

    private CourseEditorViewModel mViewModel;

    private boolean mNewCourse;
    Menu mainMenu;
    MenuItem mToggleAlarm;

    private boolean isArmed;

    AlarmController startAlarmController;
    AlarmController endAlarmController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mainMenu = toolbar.getMenu();
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_save_black);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initViewModel();

        startAlarmController = new AlarmController("Course 1 Start", new Date(),this);
        endAlarmController = new AlarmController("Course 1 End", new Date(),this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_editor, menu);
        mToggleAlarm = mainMenu.findItem(R.id.toggle_alarm);
        return true;
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(CourseEditorViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle("New Course");
            mNewCourse = true;
        } else {
            setTitle("Edit Course");
            int courseId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadData(courseId);
        }

        mViewModel.mLiveCourse.observe(this, courseEntity -> {
            mTitle.setText(courseEntity.getTitle());
            mStartDate.setText(sdf.format(courseEntity.getStartDate()));
            mEndDate.setText(sdf.format(courseEntity.getAnticipatedEndDate()));
            mStatus.setText(courseEntity.getStatus());
            mCmName.setText(courseEntity.getCmName());
            mCmEmail.setText(courseEntity.getCmEmail());
            mCmPhone.setText(courseEntity.getCmPhone());
            mNotes.setText(courseEntity.getNotes());
            mTermId = courseEntity.getTermId(); //fix later
        });

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            mSelected.setText(sdf.format(calendar.getTime()));
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                saveAndReturn();
                return true;
            case R.id.toggle_alarm:
                isArmed = !isArmed;
                if (isArmed) {
                    mToggleAlarm.setIcon(R.drawable.ic_alarm_black);
                    startAlarmController.setAlarmDate(convertStrToDate(mStartDate.getText().toString()));
                    startAlarmController.setAlarm();
                } else {
                    mToggleAlarm.setIcon(R.drawable.ic_snooze);
                    startAlarmController.cancelAlarm();
                }
                return true;
            case R.id.action_assessments:
                Intent intent=new Intent(this,AssessmentActivity.class);
                intent.putExtra("CourseId",mViewModel.mLiveCourse.getValue().getCourseId());
                intent.putExtra("CourseTitle",mViewModel.mLiveCourse.getValue().getTitle());
                startActivity(intent);
                return true;
            case R.id.action_add_assessments:
                Intent addIntent = new Intent(this,AssessmentEditorActivity.class);
                addIntent.putExtra(COURSE_ID_KEY,mViewModel.mLiveCourse.getValue().getCourseId());
                Log.i("CourseEditor", "CourseId = " + mViewModel.mLiveCourse.getValue().getCourseId());
                startActivity(addIntent);
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
                    mCmEmail.getText().toString(),mCmPhone.getText().toString(),mNotes.getText().toString(),mTermId); //fix later
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finish();
    }

    @OnClick(R.id.course_start_alarmSwitch)
    public void onClickStartAlarm(){
        if(mStartAlarm.isChecked()){
            startAlarmController.setAlarmDate(convertStrToDate(mStartDate.getText().toString()));
            startAlarmController.setAlarm();
        }
        else{
            startAlarmController.cancelAlarm();
        }
    }

    @OnClick(R.id.course_end_alarmSwitch)
    public void onClickEndAlarm(){
        if(mEndAlarm.isChecked()){
            endAlarmController.setAlarmDate(convertStrToDate(mEndDate.getText().toString()));
            endAlarmController.setAlarm();
        }
        else{
            endAlarmController.cancelAlarm();
        }
    }

    @OnClick(R.id.course_startDate)
    public void onClickStartDate() {
        mSelected = mStartDate;

        calendar.setTime(convertStrToDate(mStartDate.getText().toString()));

        new DatePickerDialog(CourseEditorActivity.this,date,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.course_endDate)
    public void onClickEndDate() {
        mSelected = mEndDate;

        calendar.setTime(convertStrToDate(mEndDate.getText().toString()));

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
