package com.example.shiftschedule;


import java.util.ArrayList;

/*
    This class will store the availability of an employee for one day
 */
public class DaySchedule {

    public ArrayList<String> openingEmail = new ArrayList<String>();
    public ArrayList<String> closingEmail = new ArrayList<String>();
    public Integer date;

    public DaySchedule(String emailOpen, String emailClose, Integer date){
        if(emailOpen.length() > 0) {openingEmail.add(emailOpen);}
        if(emailClose.length() > 0) {closingEmail.add(emailClose);}
        if(this.date > 0) {this.date = date;}

    }

}
