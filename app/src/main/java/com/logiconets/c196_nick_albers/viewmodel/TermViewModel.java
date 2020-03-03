package com.logiconets.c196_nick_albers.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.logiconets.c196_nick_albers.database.AppRepository;
import com.logiconets.c196_nick_albers.database.AssessmentEntity;
import com.logiconets.c196_nick_albers.database.CourseEntity;
import com.logiconets.c196_nick_albers.database.TermEntity;
import com.logiconets.c196_nick_albers.database.TermsAndCourses;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    //public LiveData<List<TermEntity>> mTerms;
    public LiveData<List<TermsAndCourses>> mTerms;

    private AppRepository mRepository;

    public TermViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mTerms = mRepository.mTermsAndCourses;
    }

}
