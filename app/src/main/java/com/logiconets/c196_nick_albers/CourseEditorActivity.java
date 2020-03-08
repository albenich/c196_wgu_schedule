package com.logiconets.c196_nick_albers;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.logiconets.c196_nick_albers.database.TermEntity;
import com.logiconets.c196_nick_albers.utility.AlarmController;
import com.logiconets.c196_nick_albers.viewmodel.CourseEditorViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
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
import butterknife.OnItemClick;

import static com.logiconets.c196_nick_albers.utility.Constants.COURSE_ID_KEY;
import static com.logiconets.c196_nick_albers.utility.Constants.TERM_ID_KEY;

public class CourseEditorActivity extends AppCompatActivity {

    @BindView(R.id.course_title)
    TextView mTitle;

    @BindView(R.id.course_startDate)
    TextView mStartDate;

    @BindView(R.id.course_endDate)
    TextView mEndDate;

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

    private Spinner mTermCombo;
    private Spinner mStatusCombo;
    ArrayAdapter<String> adapter;
    ArrayAdapter<CharSequence> statusAdapter;

    TextView mSelected;
    final Calendar calendar = Calendar.getInstance();
    final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    int mTermId;
    private boolean isNew;

    private CourseEditorViewModel mViewModel;

    AlarmController alarmController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_save_black);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initViewModel();


    }

    public void populateSpinners(){
        mTermCombo = (Spinner) findViewById(R.id.term_spinner);
        mStatusCombo = (Spinner) findViewById(R.id.status_spinner);
        statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.course_status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mViewModel.termList.observe(this, spinnerData -> {
            Log.i("Spinner", "Spinner Array is this big " + spinnerData.size());
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerData);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            mTermCombo.setAdapter(adapter);
            mStatusCombo.setAdapter(statusAdapter);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_editor, menu);
        return true;
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(CourseEditorViewModel.class);
        populateSpinners();

        Intent intent = getIntent();
        int termId = intent.getIntExtra(TERM_ID_KEY,-1);
        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            setTitle("New Course");
            isNew = true;
        }
        else if(termId != -1){
            setTitle("New Course");
            Log.i("CourseEditor", "CourseId = " + termId);
            mTermId = termId;
            isNew = true;
        }
        else {
            setTitle("Edit Course");
            int courseId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadData(courseId);
        }
        if(!mViewModel.isPopulated) {
            mViewModel.mLiveCourse.observe(this, courseEntity -> {
                mTitle.setText(courseEntity.getTitle());
                mStartDate.setText(sdf.format(courseEntity.getStartDate()));
                mEndDate.setText(sdf.format(courseEntity.getAnticipatedEndDate()));
                mStatusCombo.setSelection(statusAdapter.getPosition(courseEntity.getStatus()));
                mCmName.setText(courseEntity.getCmName());
                mCmEmail.setText(courseEntity.getCmEmail());
                mCmPhone.setText(courseEntity.getCmPhone());
                mNotes.setText(courseEntity.getNotes());
                mTermId = courseEntity.getTermId();
                mTermCombo.setSelection(mViewModel.getTermIdPosition(mTermId));
            });
        }mViewModel.isPopulated = true;
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
                return true;
            case R.id.action_delete_course:
                if(!isNew){
                    mViewModel.confirmDelete(this,mViewModel.mLiveCourse.getValue(),this);
                }else{
                   Toast.makeText(this,"Delete not available for new items", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
/*  Prefer to leave back button standard to allow a way to abort changes
    @Override
    public void onBackPressed(){
        saveAndReturn();
    }
*/
    private void saveAndReturn(){
        if(mTitle.getText().toString().equals("") || mStartDate.getText().toString().equals("") || mEndDate.getText().toString().equals("") ){
            Toast.makeText(this, "Fill out Title, Start, and End Date at a minimum.", Toast.LENGTH_LONG).show();
        }
        else if(convertStrToDate(mEndDate.getText().toString()).before(convertStrToDate(mStartDate.getText().toString()))){
            Toast.makeText(this,"Course cannot start after it ends",Toast.LENGTH_LONG).show();
        }
        else {
            mTermId = mViewModel.mTermIds.getValue().get(mTermCombo.getSelectedItemPosition());
            Log.i("CourseEditor", "Selected TermId = " + mTermId);
            mViewModel.saveCourse(mTitle.getText().toString(), convertStrToDate(mStartDate.getText().toString()),
                    convertStrToDate(mEndDate.getText().toString()), mStatusCombo.getSelectedItem().toString(), mCmName.getText().toString(),
                    mCmEmail.getText().toString(), mCmPhone.getText().toString(), mNotes.getText().toString(), mTermId);
            Toast.makeText(this, "Course Saved", Toast.LENGTH_SHORT);
            finish();
        }
}

    @OnClick(R.id.course_start_alarmSwitch)
    public void onClickStartAlarm(){
        if(mStartAlarm.isChecked()){
            alarmController = new AlarmController("Start of " + mTitle.getText(), convertStrToDate(mStartDate.getText().toString()),this);
            alarmController.setAlarm();
        }
        else{
            alarmController.cancelAlarm();
        }
    }

    @OnClick(R.id.course_end_alarmSwitch)
    public void onClickEndAlarm(){
        if(mEndAlarm.isChecked()){
            alarmController = new AlarmController("End of " + mTitle.getText(), convertStrToDate(mEndDate.getText().toString()),this);
            alarmController.setAlarm();
        }
        else{
            alarmController.cancelAlarm();
    }}


    @OnClick(R.id.course_startDate)
    public void onClickStartDate() {
        mSelected = mStartDate;
        if(TextUtils.isEmpty(mStartDate.getText())) {
            calendar.setTime(new Date());
        }
        else {
            calendar.setTime(convertStrToDate(mStartDate.getText().toString()));
        }
        new DatePickerDialog(CourseEditorActivity.this,date,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.course_endDate)
    public void onClickEndDate() {
        mSelected = mEndDate;
        if(TextUtils.isEmpty(mEndDate.getText())) {
            calendar.setTime(new Date());
        }
        else {
            calendar.setTime(convertStrToDate(mEndDate.getText().toString()));
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
