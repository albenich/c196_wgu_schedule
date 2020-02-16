package com.logiconets.c196_nick_albers.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.logiconets.c196_nick_albers.database.AppRepository;
import com.logiconets.c196_nick_albers.database.AssessmentEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AssessmentEditorViewModel extends AndroidViewModel {

    public MutableLiveData<AssessmentEntity> mLiveAssessment = new MutableLiveData<>();
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
}
