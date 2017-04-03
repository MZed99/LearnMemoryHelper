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
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mzeff.learnevents.data.EventContract;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    FloatingActionButton fab;
    RecyclerView mRecyclerView;
    EventAdapter mEventAdapter;
    RecyclerView.LayoutManager layoutManager;
    private static final int EVENT_LOADER_ID = 0;
    TextView error;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mEventAdapter=new EventAdapter(this);
        error=(TextView) findViewById(R.id.no_data_textview);
        Log.v("ON CREATE", "Setting the adapter to the recycler view");
        mRecyclerView.setAdapter(mEventAdapter);
        Log.v("ON CREATE", "Adapter set");



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
        Log.v("ON CREATE", "Finished onCreate");


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v("ON CREATE LOADER", "Setting the adapter to the recycler view");
        return new AsyncTaskLoader<Cursor>(MainActivity.this) {

            Cursor mEventData=null;


            @Override
            protected void onStartLoading() {
                Log.v("ON START LOADING", "Setting the adapter to the recycler view");
                super.onStartLoading();
                if (mEventData != null) {
                    Log.v("ON START LOADING", "Delivering result");
                    deliverResult(mEventData);
                } else {
                    Log.v("ON START LOADING", "Forcing loading");
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {return getContentResolver().query(EventContract.EventEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        EventContract.EventEntry._ID);

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
        Log.v("ON LOAD FINISHED: ",mEvents.toString());
        if (mEvents!=null){
            Toast.makeText(MainActivity.this, " Events loaded with no errors", Toast.LENGTH_SHORT).show();
            mRecyclerView.setVisibility(View.VISIBLE);
            error.setVisibility(View.INVISIBLE);
            mEventAdapter.setEventData(mEvents,this);
        }else{
            mRecyclerView.setVisibility(View.INVISIBLE);
            error.setVisibility(View.VISIBLE);

        }



    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mEventAdapter.setEventData(null,this);

    }
}
