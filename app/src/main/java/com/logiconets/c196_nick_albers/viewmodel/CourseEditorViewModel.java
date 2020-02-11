package com.logiconets.c196_nick_albers.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.logiconets.c196_nick_albers.database.AppRepository;
import com.logiconets.c196_nick_albers.database.CourseEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseEditorViewModel extends AndroidViewModel {

    public MutableLiveData<CourseEntity> mLiveCourse = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public CourseEditorViewModel(@NonNull Application application){
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(int CourseId) {
        executor.execute(() -> {
            CourseEntity course = mRepository.getCourseById(CourseId);
            mLiveCourse.postValue(course);
        });
    }

    public void saveCourse(String CourseText) {
        CourseEntity course = mLiveCourse.getValue();

        if(course == null){
            if(TextUtils.isEmpty(CourseText.trim())){
                return;
            }
            course = new CourseEntity(CourseText.trim(),new Date(), new Date(),"Registered",
            "Dana Cobbs","8909898989","dana.cobbs@wgu.edu","");
        }else{
            course.setTitle(CourseText.trim());
        }
        mRepository.insertCourse(course);
    }
}