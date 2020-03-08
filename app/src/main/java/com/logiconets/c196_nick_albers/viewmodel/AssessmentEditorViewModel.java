package com.logiconets.c196_nick_albers.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.logiconets.c196_nick_albers.AssessmentActivity;
import com.logiconets.c196_nick_albers.CoursesActivity;
import com.logiconets.c196_nick_albers.database.AppRepository;
import com.logiconets.c196_nick_albers.database.AssessmentEntity;
import com.logiconets.c196_nick_albers.database.CourseEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AssessmentEditorViewModel extends AndroidViewModel {

    public MutableLiveData<AssessmentEntity> mLiveAssessment = new MutableLiveData<>();
    public boolean isPopulated;

    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public AssessmentEditorViewModel(@NonNull Application application){
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(int AssessmentId) {
        executor.execute(() -> {
            AssessmentEntity assessment = mRepository.getAssessmentById(AssessmentId);
            mLiveAssessment.postValue(assessment);
        });
    }

    public void saveAssessment(String AssessmentText,String type,Date dueDate,int courseId) {
        AssessmentEntity assessment = mLiveAssessment.getValue();

        if(assessment == null){
            if(TextUtils.isEmpty(AssessmentText.trim())){
                return;
            }
            assessment = new AssessmentEntity(AssessmentText.trim(),type,dueDate,courseId);
        }else{
            assessment.setTitle(AssessmentText.trim());
            assessment.setDueDate(dueDate);
            assessment.setAssessType(type);
            assessment.setCourseId(courseId);
        }
        mRepository.insertAssessment(assessment);
    }

    public void deleteAssessment(AssessmentEntity assessment){
        mRepository.deleteAssessment(assessment);
    }

    public void confirmDelete(Context mContext, AssessmentEntity assessment, Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        builder.setTitle("Please Confirm");
        builder.setMessage("Do you really want to delete this Assessment?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     //   Intent intent = new Intent(mContext, AssessmentActivity.class);
                     //   mContext.startActivity(intent);
                        activity.onBackPressed();
                        deleteAssessment(assessment);
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
