package com.logiconets.c196_nick_albers;

import android.app.DatePickerDialog;
import android.os.Bundle;

import com.logiconets.c196_nick_albers.viewmodel.TermEditorViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.logiconets.c196_nick_albers.utility.Constants.TERM_ID_KEY;

public class TermEditorActivity extends AppCompatActivity {

    @BindView(R.id.term_title)
    TextView mTextView;

    private TermEditorViewModel mViewModel;
    private boolean mNewTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cake);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initViewModel();
    }

    private void initViewModel(){
        mViewModel = new ViewModelProvider(this).get(TermEditorViewModel.class);

        mViewModel.mLiveTerm.observe(this, termEntity ->
                mTextView.setText(termEntity.getTitle()));

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
            saveAndReturn();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveTerm(mTextView.getText().toString());
        finish();
    }

/*    @OnClick(R.id.term_startDate)
    public void onClickStartDate(){
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                startDate.setText(sdf.format(calendar.getTime()));
            }
        };
        if(calendar == null) {
            new DatePickerDialog(getApplication().getApplicationContext(), date, 1990, 0, 1).show();
        }
        else{
            new DatePickerDialog(getApplication().getApplicationContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }

    }
*/

}
