package com.logiconets.c196_nick_albers.database;

import android.content.Context;
import android.util.Log;

import com.logiconets.c196_nick_albers.utility.PopulateData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    public List<TermEntity> mTerms;
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
    }

    public void addSampleData() {
        mTerms = PopulateData.getTerms();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("AppRepo","Inserting terms into Db");
                mDb.termDAO().insertAll(mTerms);
            }
        });
    }

    public void deleteData() {
        mTerms.clear();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("AppRepo", "Deleting everything!");
                mDb.termDAO().deleteAll();
            }
        });
    }
}
