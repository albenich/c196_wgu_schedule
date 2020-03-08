package com.logiconets.c196_nick_albers;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.logiconets.c196_nick_albers.viewmodel.TermEditorViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.logiconets.c196_nick_albers.utility.Constants.COURSE_ID_KEY;
import static com.logiconets.c196_nick_albers.utility.Constants.TERM_ID_KEY;

public class TermEditorActivity extends AppCompatActivity {

    @BindView(R.id.term_title)
    TextView mTitle;

    @BindView(R.id.term_startDate)
    TextView mStartDate;

    @BindView(R.id.term_endDate)
    TextView mEndDate;

    final Calendar calendar = Calendar.getInstance();
    final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    TextView mSelected;
    private boolean isNew;

    private TermEditorViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_save_black);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        Log.i("TermEditor", "TermEditor has run through onCreate");
        initViewModel();

    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        initViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_editor, menu);
        return true;
    }

    private void initViewModel(){
        mViewModel = new ViewModelProvider(this).get(TermEditorViewModel.class);

        if(!mViewModel.isPopulated) {
            mViewModel.mLiveTerm.observe(this, termEntity -> {
                mTitle.setText(termEntity.getTerm().getTitle());
                mStartDate.setText(sdf.format(termEntity.getTerm().getStartDate()));
                mEndDate.setText(sdf.format(termEntity.getTerm().getEndDate()));
                //Toast.makeText(this, termEntity.getCourses().toString(),Toast.LENGTH_LONG).show();
            });
        }mViewModel.isPopulated = true;
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            setTitle(R.string.new_term);
            isNew = true;
        }
        else{
            setTitle(R.string.edit_term);
            int termId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadData(termId);
            isNew = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                saveAndReturn();
                return true;
            case R.id.action_courses:
                Intent intent=new Intent(this,CoursesActivity.class);
                intent.putExtra(TERM_ID_KEY,mViewModel.mLiveTerm.getValue().getTerm().getTitleId());
                intent.putExtra("TermTitle",mViewModel.mLiveTerm.getValue().getTerm().getTitle());
                startActivity(intent);
                return true;
            case R.id.action_add_courses:
                Intent addIntent = new Intent(this,CourseEditorActivity.class);
                addIntent.putExtra(TERM_ID_KEY,mViewModel.mLiveTerm.getValue().getTerm().getTitleId());
                Log.i("TermEditor", "TermId = " + mViewModel.mLiveTerm.getValue().getTerm().getTitleId());
                startActivity(addIntent);
                return true;
            case R.id.action_delete_term:
                if(!isNew) {
                    Log.i("TermEditor","mLiveTerm has " + mViewModel.mLiveTerm.getValue().getCourses().size() + " courses" );
                    if (mViewModel.mLiveTerm.getValue().getCourses().size() > 0) {
                        Toast.makeText(this, "Term has " + mViewModel.mLiveTerm.getValue().getCourses().toString() +
                                " Courses.\nUnable to delete a Term with Courses added", Toast.LENGTH_LONG).show();
                    } else {
                        mViewModel.confirmDelete(this, mViewModel.mLiveTerm.getValue().getTerm());
                    }
                }
                else{
                    Toast.makeText(this,"Delete not available for new items", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
/*  Prefer to leave back button standard to allow a way to abort changes
    @Override
    public void onBackPressed() {
        saveAndReturn();
    }
*/
    private void saveAndReturn() {
        if(mTitle.getText().toString().equals("") || mStartDate.getText().toString().equals("") || mEndDate.getText().toString().equals("") ){
            Toast.makeText(this, "Fill out all fields.", Toast.LENGTH_LONG).show();
        }
        else if(convertStrToDate(mEndDate.getText().toString()).before(convertStrToDate(mStartDate.getText().toString()))){
            Toast.makeText(this,"Term cannot start after it ends",Toast.LENGTH_LONG).show();
        }
        else {
            mViewModel.saveTerm(mTitle.getText().toString(), convertStrToDate(mStartDate.getText().toString()),
                    convertStrToDate(mEndDate.getText().toString()));
            Toast.makeText(this, "Term Saved", Toast.LENGTH_SHORT);
            finish();
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            mSelected.setText(sdf.format(calendar.getTime()));
        }
    };

    @OnClick(R.id.term_startDate)
    public void onClickStartDate() {
        mSelected = mStartDate;
        if(TextUtils.isEmpty(mStartDate.getText())) {
            calendar.setTime(new Date());
        }
        else {
            calendar.setTime(convertStrToDate(mStartDate.getText().toString()));
        }
        new DatePickerDialog(TermEditorActivity.this,date,calendar.get(Calendar.YEAR),
                  calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.term_endDate)
    public void onClickEndDate() {
        mSelected = mEndDate;
        if(TextUtils.isEmpty(mEndDate.getText())) {
            calendar.setTime(new Date());
        }
        else {
            calendar.setTime(convertStrToDate(mEndDate.getText().toString()));
        }
        new DatePickerDialog(TermEditorActivity.this,date,calendar.get(Calendar.YEAR),
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



