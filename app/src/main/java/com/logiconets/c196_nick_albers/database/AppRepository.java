package com.logiconets.c196_nick_albers.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.logiconets.c196_nick_albers.utility.PopulateData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<TermEntity>> mTerms;
    public LiveData<List<CourseEntity>> mCourses;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        //mTerms = PopulateData.getTerms();
        mDb = AppDatabase.getDatabase(context);
        mTerms = getAllTerms();
        mCourses = getAllCourses();
        Log.i("AppRepository",mTerms == null ? "Empty mTerms" : "mTerms Populated!");
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("AppRepo","Inserting terms into Db");
                mDb.termDAO().insertAll(PopulateData.getTerms());
                mDb.courseDAO().insertAll(PopulateData.getCourses());
            }
        });
    }

    public void deleteData() {
        executor.execute(() -> {
            Log.i("AppRepo", "Deleting everything!");
            mDb.termDAO().deleteAll();
            mDb.courseDAO().deleteAll();
        });
    }
//Modules for TermEntity Querying
    private LiveData<List<TermEntity>> getAllTerms(){
        return mDb.termDAO().getAll();
    }

    public TermEntity getTermById(int termId) {
        return mDb.termDAO().getTermById(termId);
    }

    public void insertTerm(final TermEntity term) {
        executor.execute(() -> mDb.termDAO().insert(term));
    }

//Modules for CourseEntity Querying
    private LiveData<List<CourseEntity>> getAllCourses() { return mDb.courseDAO().getAll(); }

    public CourseEntity getCourseById(int courseId) {
        return mDb.courseDAO().getCourseById(courseId);
    }

    public void insertCourse(final CourseEntity course) {
        executor.execute(() -> mDb.courseDAO().insert(course));
    }
}
