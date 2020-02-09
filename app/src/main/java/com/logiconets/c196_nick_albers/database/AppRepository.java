package com.logiconets.c196_nick_albers.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.logiconets.c196_nick_albers.utility.PopulateData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<TermEntity>> mTerms;
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
        mTerms = getAllNotes();
        Log.i("AppRepository",mTerms == null ? "Empty mTerms" : "mTerms Populated!");
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("AppRepo","Inserting terms into Db");
                mDb.termDAO().insertAll(PopulateData.getTerms());
            }
        });
    }

    public void deleteData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("AppRepo", "Deleting everything!");
                mDb.termDAO().deleteAll();
            }
        });
    }

    private LiveData<List<TermEntity>> getAllNotes(){
        return mDb.termDAO().getAll();
    }

    public TermEntity getTermById(int termId) {
        return mDb.termDAO().getTermById(termId);
    }

    public void insertTerm(final TermEntity term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDAO().insert(term);
            }
        });
    }
}
