package com.example.shiftschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;

public class createShiftWeekday extends AppCompatActivity {

    /*
    MEMBER VARIABLES                                                        Description
    String Day of week:
    Possible values for Day of the Week:
    Sun, Mon, Tue, Wed, Thu, Fri, Sat
    String dateOfShift                                                      The date of the shift, with the format YYYY/
    String dayOfWeek
    Available timeOfShift
    Context context ==> The current context. Allows to create Error toast messages in the class instead of duplicating them per activity
    Calendar calendar ==>                                                   The calendar object that was clicked, used only for schedule_month_view (for visual purposes only, keeps track of visual day clicked).
    Bundle bundle                                                           The bundle that holds the information that we send back.
     */

    protected Button submitShiftButton;
    protected Calendar selectedCalendarDate;
    //TODO: Create rest of member variables, and physically create a weekday Shift Object. Only testing on calendar has been done in regards to this (User cannot create a shift on a date in the past. Need to return Calendar in resultIntent if done.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shift_weekday);
        this.submitShiftButton = (Button) findViewById(R.id.createShiftButton);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String dayOfTheWeek = bundle.getString("dayOfWeek");
            //Toast.makeText(createShiftWeekday.this, dayOfTheWeek, Toast.LENGTH_SHORT).show();
            String selectedCalendarAsString = bundle.getString("calendar");
            Gson gson = new Gson();
            //Calendar selectedCalendar = gson.fromJson(selectedCalendarAsString, Calendar.class);
            this.selectedCalendarDate = gson.fromJson(selectedCalendarAsString, Calendar.class);
            Toast.makeText(createShiftWeekday.this, selectedCalendarDate.toString(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(createShiftWeekday.this, selectedCalendarAsString, Toast.LENGTH_SHORT).show();
        }
    }
    public void onCreateShiftClick(View view) {
        //String testing = "I'm testing";
        Gson gson = new Gson();
        String returnedCalendarDateString = gson.toJson(this.selectedCalendarDate);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", returnedCalendarDateString);
        setResult(RESULT_OK, resultIntent);
        finish();
    }


}