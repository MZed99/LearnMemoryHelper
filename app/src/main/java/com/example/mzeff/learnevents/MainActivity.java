package com.example.mzeff.learnevents;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.mzeff.learnevents.data.EventContract;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    FloatingActionButton fab;
    RecyclerView mRecyclerView;
    EventAdapter mEventAdapter;
    RecyclerView.LayoutManager layoutManager;
    private static final int EVENT_LOADER_ID = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mEventAdapter=new EventAdapter(this);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete

                // COMPLETED (1) Construct the URI for the item to delete
                //[Hint] Use getTag (from the adapter code) to get the id of the swiped item
                // Retrieve the id of the task to delete
                int id = (int) viewHolder.itemView.getTag();

                // Build appropriate uri with String row id appended
                String stringId = Integer.toString(id);
                Uri uri = EventContract.EventEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();

                // COMPLETED (2) Delete a single row of data using a ContentResolver
                getContentResolver().delete(uri, null, null);

                // COMPLETED (3) Restart the loader to re-query for all tasks after a deletion
                getSupportLoaderManager().restartLoader(EVENT_LOADER_ID, null, MainActivity.this);

            }
        }).attachToRecyclerView(mRecyclerView);


        //definition of the floating action button
        fab=(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddNewEventActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(MainActivity.this) {

            Cursor mEventData;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (mEventData != null) {
                    deliverResult(mEventData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {return getContentResolver().query(EventContract.EventEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        EventContract.EventEntry.COLUMN_EVENTDATE);

                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
            public void deliverResult(Cursor data) {
                mEventData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<Event> mEvents= new ArrayList<>();
        for(data.moveToFirst();data.moveToLast();data.moveToNext()){
            mEvents.add(new Event(data.getString(data.getColumnIndex("event_date")),data.getString(data.getColumnIndex("event_description"))));
        }
        if (mEvents!=null){
            Toast.makeText(MainActivity.this, " Events loaded with no errors", Toast.LENGTH_SHORT).show();
            mEventAdapter.setEventData(mEvents,this);
        }



    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mEventAdapter.setEventData(null,this);

    }
}
