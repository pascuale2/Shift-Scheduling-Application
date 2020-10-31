package com.example.shiftschedule;

import java.util.ArrayList;

public class BusyDaySchedule {

    public ArrayList<String> allDayEmail = new ArrayList<String>();
    public Integer date;

    public BusyDaySchedule(String emailAllDay, Integer date){
        if(emailAllDay.length() > 0) {allDayEmail.add(emailAllDay);}
        if(this.date > 0) {this.date = date;}

    }
}
