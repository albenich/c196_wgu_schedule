package com.logiconets.c196_nick_albers.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.logiconets.c196_nick_albers.CoursesActivity;
import com.logiconets.c196_nick_albers.database.AppRepository;
import com.logiconets.c196_nick_albers.database.CourseEntity;
import com.logiconets.c196_nick_albers.database.TermEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseEditorViewModel extends AndroidViewModel {

    public MutableLiveData<CourseEntity> mLiveCourse = new MutableLiveData<>();
    //public MutableLiveData<List<TermEntity>> mLiveTerms = new MutableLiveData<>();
    public MutableLiveData<List<String>> termList = new MutableLiveData<>();
    public MutableLiveData<List<Integer>> mTermIds = new MutableLiveData<>();
    public boolean isPopulated;

    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();


    public CourseEditorViewModel(@NonNull Application application){
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        fetchSpinnerItems();
        loadTerms();
    }
/*
    public MutableLiveData<List<String>> fetchSpinnerItems() {
        mLiveTerms = mRepository.mTerms;
        for(TermEntity term : mLiveTerms.getValue()){
            termList.getValue().add(term.getTitle());
        }
        return termList;

    }
*/
    public void loadTerms() {
        executor.execute(() -> {
            List<Integer> termIds = mRepository.getTermIds();
            Log.i("CourseEditorViewModel", "TermId array size = " + termIds.size());
            mTermIds.postValue(termIds);
            Log.i("CourseEditorViewModel", "mTermIds posted " + termIds.toString() + " just fine");
        });
    }
    public void fetchSpinnerItems() {
        executor.execute(() ->{
            List<String> strings = mRepository.getTermTitles();
            termList.postValue(strings);
            Log.i("CourseEditorViewModel", "termList posted " + strings.toString() + " just fine");
        });
    }

    public void loadData(int CourseId) {
        executor.execute(() -> {
            CourseEntity course = mRepository.getCourseById(CourseId);
            mLiveCourse.postValue(course);
        });
    }

    public void saveCourse(String CourseText,Date startDate, Date endDate, String status, String cmName,
                           String cmEmail, String cmPhone, String notes, int termId, boolean hasStartAlarm,
                           boolean hasEndAlarm) {
        CourseEntity course = mLiveCourse.getValue();

        if(course == null){
            if(TextUtils.isEmpty(CourseText.trim())){
                return;
            }
        course = new CourseEntity(CourseText.trim(),startDate, endDate,status,
                cmName,cmPhone,cmEmail,notes,termId,hasStartAlarm,hasEndAlarm);
        }else{
            course.setTitle(CourseText.trim());
            course.setStartDate(startDate);
            course.setAnticipatedEndDate(endDate);
            course.setStatus(status.trim());
            course.setCmName(cmName.trim());
            course.setCmEmail(cmEmail.trim());
            course.setCmPhone(cmPhone.trim());
            course.setNotes(notes.trim());
            course.setTermId(termId);
            course.setHasStartAlarm(hasStartAlarm);
            course.setHasEndAlarm(hasEndAlarm);
        }
        mRepository.insertCourse(course);
    }

    public void deleteCourse(CourseEntity course){
        mRepository.deleteCourse(course);
    }

    public int getTermIdPosition(int termId){
        int position = 0;
        for(int id : mTermIds.getValue()){
            if(id == termId){
                Log.i("CourseEditorViewModel", "Found " + termId + " at position " + position);
                return position;
            }
            else{
                Log.i("CourseEditorViewModel", "Didn't find " + termId + " at position " + position
                        + "\nFound " + id + " instead");
                position++;
            }

        }
        return position;
    }
    public void confirmDelete(Context mContext, CourseEntity course, Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        builder.setTitle("Please Confirm");
        builder.setMessage("Do you really want to delete this Course?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     //   Intent intent = new Intent(mContext, CoursesActivity.class);
                     //   mContext.startActivity(intent);
                        activity.onBackPressed();
                        deleteCourse(course);
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
