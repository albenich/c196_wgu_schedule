package com.logiconets.c196_nick_albers.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.logiconets.c196_nick_albers.database.AppRepository;
import com.logiconets.c196_nick_albers.database.AssessmentEntity;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    public LiveData<List<AssessmentEntity>> mAssessments;
    private AppRepository mRepository;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mAssessments = mRepository.mAssessments;
    }

}
