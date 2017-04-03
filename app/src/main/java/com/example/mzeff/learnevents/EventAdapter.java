package com.example.mzeff.learnevents;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by azeff on 01/04/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    ArrayList<Event> mEvents=new ArrayList<>();
    Context context;
    Cursor mCursor;


    //interface for the buttons hide/unhide click
    public interface EventClickHandler{
        void onClick(int t1visibility,int t2visibility);
    }
    public void setEventData(ArrayList<Event> events,Context context){
        mEvents=events;
        this.context=context;
    }

    public EventAdapter(Context context){
        this.context=context;

    }

    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.event_item, parent, false);

        return new EventAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(EventAdapter.ViewHolder holder, int position) {
        Event event=mEvents.get(position);
        String text1=event.getTextFor1();
        String text2=event.getTextFor2();

        holder.eventDate.setText(text1);
        holder.eventBody.setText(text2);


    }




    @Override
    public int getItemCount() {
        return mEvents.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView eventDate;
        TextView eventBody;
        Button event1Button;
        Button event2Button;

        public ViewHolder(View itemView) {
            super(itemView);
            eventBody = (TextView) itemView.findViewById(R.id.textViewEventBody);
            eventDate = (TextView) itemView.findViewById(R.id.textViewEventDate);
            event1Button = (Button) itemView.findViewById(R.id.button_hide_text1);
            event2Button = (Button) itemView.findViewById(R.id.button_hide_text2);
        }

        @Override
        public void onClick(View v) {
            int id=v.getId();
            if(id==event1Button.getId()){
                if(event1Button.getVisibility()==View.VISIBLE) eventDate.setVisibility(View.INVISIBLE);
                else eventDate.setVisibility(View.INVISIBLE);
            }else if(id==event2Button.getId()){
                if(event2Button.getVisibility()==View.VISIBLE) eventBody.setVisibility(View.INVISIBLE);
                else eventBody.setVisibility(View.VISIBLE);
            }


        }
    }
}
