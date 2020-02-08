package com.logiconets.c196_nick_albers;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.logiconets.c196_nick_albers.database.AppDatabase;
import com.logiconets.c196_nick_albers.database.TermDAO;
import com.logiconets.c196_nick_albers.database.TermEntity;
import com.logiconets.c196_nick_albers.utility.PopulateData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

        public static final String TAG = "Junit";
        private AppDatabase mDb;
        private TermDAO mDao;

        @Before
        public void createDb(){
            Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
            mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
            mDao = mDb.termDAO();
            Log.i(TAG, "createDb");
        }

        @After
        public void closeDb(){
            mDb.close();
            Log.i(TAG, "closeDb");
        }

        @Test
        public void createAndRetrieveNotes(){
            mDao.insertAll(PopulateData.getTerms());
            int count = mDao.getCount();
            Log.i(TAG, "createAndRetrieveNotes: count= " + count);
            assertEquals(PopulateData.getTerms().size(), count);
        }

        @Test
        public void compareStrings(){
            mDao.insertAll(PopulateData.getTerms());
            TermEntity original = PopulateData.getTerms().get(0);
            TermEntity fromDb = mDao.getTermById(1);
            assertEquals(original.getTitle(),fromDb.getTitle());
        }
}
