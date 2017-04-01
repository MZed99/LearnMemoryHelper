package com.example.mzeff.learnevents;

import android.widget.TextView;

/**
 * Created by azeff on 01/04/2017.
 */

public class Event {
    private String textFor1;
    private String textFor2;

    Event(String text1,String text2){
        textFor1=text1;
        textFor2=text2;
    }

    public String getTextFor1() {
        return textFor1;
    }

    public String getTextFor2() {
        return textFor2;
    }
}
