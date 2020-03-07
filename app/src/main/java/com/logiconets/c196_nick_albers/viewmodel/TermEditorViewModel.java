package com.logiconets.c196_nick_albers.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.logiconets.c196_nick_albers.CoursesActivity;
import com.logiconets.c196_nick_albers.TermsActivity;
import com.logiconets.c196_nick_albers.database.AppRepository;
import com.logiconets.c196_nick_albers.database.CourseEntity;
import com.logiconets.c196_nick_albers.database.TermEntity;
import com.logiconets.c196_nick_albers.database.TermsAndCourses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TermEditorViewModel extends AndroidViewModel {

    public MutableLiveData<TermsAndCourses> mLiveTerm = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public TermEditorViewModel(@NonNull Application application){
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(int termId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                TermsAndCourses term = mRepository.getTermAndCoursesById(termId);
                mLiveTerm.postValue(term);
            }
        });
    }

    public void saveTerm(String termText,Date startDate, Date endDate) {
        TermsAndCourses term = mLiveTerm.getValue();

        if(term == null){
            if(TextUtils.isEmpty(termText.trim())){
                return;
            }
            TermEntity termOnly = new TermEntity(termText.trim(),startDate, endDate);
            term = new TermsAndCourses(termOnly, new ArrayList<CourseEntity>());
        }else{
            term.getTerm().setTitle(termText.trim());
            term.getTerm().setStartDate(startDate);
            term.getTerm().setEndDate(endDate);
        }
        mRepository.insertTerm(term.getTerm());
    }

    public void deleteTerm(TermEntity term){
        executor.execute(() -> mRepository.deleteTerm(term));
    }

    public void confirmDelete(Context mContext, TermEntity term) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        builder.setTitle("Please Confirm");
        builder.setMessage("Do you really want to delete this Term?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(mContext, TermsActivity.class);
                        mContext.startActivity(intent);
                        deleteTerm(term);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }

        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
