package com.example.mzeff.learnevents.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by azeff on 01/04/2017.
 */

public class EventDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "eventsDb.db";
    private static final int VERSION = 4;

    EventDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE "  + EventContract.EventEntry.TABLE_NAME + " (" +
                EventContract.EventEntry._ID                + " INTEGER PRIMARY KEY, " +
                EventContract.EventEntry.COLUMN_EVENTDATE + " TEXT NOT NULL, " +
                EventContract.EventEntry.COLUMN_EVENTDESCRIPTION    + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EventContract.EventEntry.TABLE_NAME);
        onCreate(db);

    }
}
