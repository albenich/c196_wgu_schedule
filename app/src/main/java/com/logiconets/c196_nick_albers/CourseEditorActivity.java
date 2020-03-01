package com.logiconets.c196_nick_albers;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.logiconets.c196_nick_albers.database.AssessmentEntity;
import com.logiconets.c196_nick_albers.ui.AssessmentListAdapter;
import com.logiconets.c196_nick_albers.utility.AlarmController;
import com.logiconets.c196_nick_albers.utility.AlarmReceiver;
import com.logiconets.c196_nick_albers.viewmodel.AssessmentViewModel;
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
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.logiconets.c196_nick_albers.utility.Constants.ASSESSMENT_ID_KEY;
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

    TextView mSelected;
    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    private CourseEditorViewModel mViewModel;

    private boolean mNewCourse;
    Menu mainMenu;
    MenuItem mToggleAlarm;

    private static final int NOTIFICATION_ID = 1337;
    private NotificationManager mNotificationManager;
    private AlarmManager alarmManager;
    private PendingIntent notifyPendingIntent;
    private boolean isArmed;

    AlarmController alarmController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mainMenu = toolbar.getMenu();
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_save_black);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        ButterKnife.bind(this);
        initViewModel();

        alarmController = new AlarmController("Course 1", new Date(),this);

        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        notifyPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        isArmed = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
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
                } else {
                    mToggleAlarm.setIcon(R.drawable.ic_snooze);
                }
                //setAlarm();
                alarmController.setAlarm();
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

    private void setAlarm(){
        //Testing alarmManager setup at SaveAndReturn
        String toastMessage;
        Date testDate = null;
        Calendar today = Calendar.getInstance();
        today.clear(Calendar.HOUR);
        today.clear(Calendar.HOUR_OF_DAY);
        today.clear(Calendar.MINUTE);
        today.clear(Calendar.SECOND);
        today.clear(Calendar.MILLISECOND);
        Date dateOnly = new Date(today.getTimeInMillis() - today.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000);
        try {
            Log.i("Test", isArmed ? "Alarm Enabled":"Alarm Disabled");
            Log.i("Test","mStartDate = " + mStartDate.getText().toString());
            testDate = sdf.parse(mStartDate.getText().toString());
            Log.i("Test","sdf.parse = " + testDate.toString());
            Log.i("Test","Current Time = " + Calendar.getInstance().getTime().toString());
            Log.i("Test","today var = " + dateOnly.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(isArmed){
            toastMessage = "Start Date before current Date";
            if(testDate.after(dateOnly) || testDate.equals(dateOnly)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, testDate.getTime(), notifyPendingIntent);
                }
                toastMessage = mTitle.getText() + " Notification is On!";
            }
        }
        else{
            if(alarmManager != null){
                alarmManager.cancel(notifyPendingIntent);
            }
            mNotificationManager.cancelAll();
            toastMessage = mTitle.getText() + " Notification is Off!";
        }
        Toast.makeText(this,toastMessage,Toast.LENGTH_SHORT).show();
        //End alarmManager setup
    }
}
