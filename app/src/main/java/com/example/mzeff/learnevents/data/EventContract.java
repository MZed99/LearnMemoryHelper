package com.example.mzeff.learnevents.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by azeff on 01/04/2017.
 */

public class EventContract {
    public static final String AUTHORITY = "com.example.mzeff.learnevents";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_EVENTS= "events";

    public static final class EventEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVENTS).build();
        public static final String TABLE_NAME = "events";

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_EVENTDATE = "event_date";
        public static final String COLUMN_EVENTDESCRIPTION = "event_description";
    }
}
