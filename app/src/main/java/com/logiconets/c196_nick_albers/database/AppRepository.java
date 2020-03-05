package com.logiconets.c196_nick_albers.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.logiconets.c196_nick_albers.utility.PopulateData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<TermEntity>> mTerms;
    public LiveData<List<CourseEntity>> mCourses;
    public LiveData<List<AssessmentEntity>> mAssessments;
    public LiveData<List<TermsAndCourses>> mTermsAndCourses;

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
        mAssessments = getAllAssessments();
        mTermsAndCourses = getAllTermsAndCourses();
        Log.i("AppRepository",mTerms == null ? "Empty mTerms" : "mTerms Populated!");
    }

    public void addSampleData() {
        executor.execute(() -> {
            Log.i("AppRepo","Inserting terms into Db");
            mDb.termDAO().insertAll(PopulateData.getTerms());
            mDb.courseDAO().insertAll(PopulateData.getCourses());
            mDb.assessmentDAO().insertAll(PopulateData.getAssessments());
        });
    }

    public void deleteData() {
        executor.execute(() -> {
            Log.i("AppRepo", "Deleting everything!");
            mDb.termDAO().deleteAll();
            mDb.courseDAO().deleteAll();
            mDb.assessmentDAO().deleteAll();
        });
    }
//Modules for TermEntity Querying
    private LiveData<List<TermEntity>> getAllTerms(){
        return mDb.termDAO().getAll();

    }

    public List<String> getTermTitles(){
        return mDb.termDAO().getTermTitles();
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

//Modules for AssessmentEntity Querying
    private LiveData<List<AssessmentEntity>> getAllAssessments() { return mDb.assessmentDAO().getAll(); }

    public AssessmentEntity getAssessmentById(int assessmentId) {
        return mDb.assessmentDAO().getAssessmentById(assessmentId);
    }

    public void insertAssessment(final AssessmentEntity assessment){
        executor.execute(() -> mDb.assessmentDAO().insert(assessment));
    }

//Modules for TermsAndCourses
    private LiveData<List<TermsAndCourses>> getAllTermsAndCourses() { return mDb.termDAO().loadTermsAndCourses(); }

    public TermsAndCourses getTermAndCoursesById(int termId) {
        return mDb.termDAO().getTermAndCoursesById(termId);
    }

}
