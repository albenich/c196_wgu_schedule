package com.logiconets.c196_nick_albers;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;

import com.logiconets.c196_nick_albers.viewmodel.TermEditorViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
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

import static com.logiconets.c196_nick_albers.utility.Constants.TERM_ID_KEY;

public class TermEditorActivity extends AppCompatActivity {

    @BindView(R.id.term_title)
    TextView mTitle;

    @BindView(R.id.term_startDate)
    TextView mStartDate;

    @BindView(R.id.term_endDate)
    TextView mEndDate;

    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    TextView mSelected;
    //AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

    private TermEditorViewModel mViewModel;
    private boolean mNewTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_save_black);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initViewModel();
        Toast.makeText(this, mViewModel.mLiveTerm.getValue().getCourses().get(0).getTitle(),Toast.LENGTH_LONG).show();
    }

    private void initViewModel(){
        mViewModel = new ViewModelProvider(this).get(TermEditorViewModel.class);

        mViewModel.mLiveTerm.observe(this, termEntity ->{
            mTitle.setText(termEntity.getTerm().getTitle());
            mStartDate.setText(sdf.format(termEntity.getTerm().getStartDate()));
            mEndDate.setText(sdf.format(termEntity.getTerm().getEndDate()));
            });

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            setTitle(R.string.new_term);
            mNewTerm = true;
        }
        else{
            setTitle(R.string.edit_term);
            int termId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadData(termId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            try {
                saveAndReturn();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        try {
            saveAndReturn();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void saveAndReturn() throws ParseException {
        mViewModel.saveTerm(mTitle.getText().toString(),sdf.parse(mStartDate.getText().toString()),
        sdf.parse(mEndDate.getText().toString()));
        finish();
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
        try {
            calendar.setTime(sdf.parse(mStartDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        new DatePickerDialog(TermEditorActivity.this,date,calendar.get(Calendar.YEAR),
                  calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.term_endDate)
    public void onClickEndDate() {
        mSelected = mEndDate;
        try {
            calendar.setTime(sdf.parse(mEndDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        new DatePickerDialog(TermEditorActivity.this,date,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}



