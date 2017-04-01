package com.example.mzeff.learnevents;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mzeff.learnevents.data.EventContract;

/**
 * Created by azeff on 01/04/2017.
 */

public class AddNewEventActivity extends AppCompatActivity {
    private EditText editTextField1;
    private EditText editTextField2;
    private Button addButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewevent);
        editTextField1=(EditText) findViewById(R.id.editText);
        editTextField2=(EditText) findViewById(R.id.editText2);
        addButton=(Button) findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputTxt1=editTextField1.getText().toString();
                String inputTxt2=editTextField2.getText().toString();
                if(inputTxt1.length()==0 || inputTxt2.length()==0){
                    return;
                }
                ContentValues contentValues=new ContentValues();
                contentValues.put(EventContract.EventEntry.COLUMN_EVENTDATE,inputTxt1);
                contentValues.put(EventContract.EventEntry.COLUMN_EVENTDESCRIPTION,inputTxt2);
                Uri uri = getContentResolver().insert(EventContract.EventEntry.CONTENT_URI, contentValues);
                finish();
            }
        });

    }
}
