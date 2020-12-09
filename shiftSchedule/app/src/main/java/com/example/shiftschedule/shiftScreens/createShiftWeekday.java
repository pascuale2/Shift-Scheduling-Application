package com.example.shiftschedule.shiftScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiftschedule.Available.Available;
import com.example.shiftschedule.R;
import com.example.shiftschedule.employee.screens.changeAvailability;
import com.example.shiftschedule.shifts.WeekdayShifts;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    protected Button cancelShiftButton;
    protected Calendar selectedCalendarDate;
    protected String dayOfWeek;
    protected String dateOfShift;
    protected Available timeOfShift;
    protected RadioButton openingRadioButton;
    protected RadioButton closingRadioButton;
    protected SharedPreferences shiftStorage;
    protected TextView displayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shift_weekday);
        this.submitShiftButton = (Button) findViewById(R.id.createShiftButton);
        this.openingRadioButton = (RadioButton) findViewById(R.id.CSWeek_openingRadio);
        this.closingRadioButton = (RadioButton) findViewById(R.id.CSWeek_closingRadio);
        this.shiftStorage = getApplicationContext().getSharedPreferences("shifts", Context.MODE_PRIVATE);
        this.displayDate = (TextView) findViewById(R.id.CSWeek_dateLabel);
        this.cancelShiftButton = (Button) findViewById(R.id.CSWeek_cancelButton);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.dateOfShift = bundle.getString("date");
            this.dayOfWeek = bundle.getString("dayOfWeek");
            //Toast.makeText(createShiftWeekday.this, dayOfWeek, Toast.LENGTH_SHORT).show();
            String selectedCalendarAsString = bundle.getString("calendar");
            Gson gson = new Gson();
            //Calendar selectedCalendar = gson.fromJson(selectedCalendarAsString, Calendar.class);
            this.selectedCalendarDate = gson.fromJson(selectedCalendarAsString, Calendar.class);
            this.displayDate.setText(this.dateOfShift);
            //Toast.makeText(createShiftWeekday.this, selectedCalendarDate.toString(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(createShiftWeekday.this, selectedCalendarAsString, Toast.LENGTH_SHORT).show();
            //Toast.makeText(createShiftWeekday.this, this.dateOfShift, Toast.LENGTH_SHORT).show();
        }
        // Changes the taskbar color to match the background
        Window window = createShiftWeekday.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(createShiftWeekday.this, R.color.hot_pink));
    }

    public Available selectTimeOfShift() {
        if (this.openingRadioButton.isChecked())
            return Available.OPENING;
        else if (this.closingRadioButton.isChecked())
            return Available.CLOSING;
        else
            return Available.CANNOT;
    }

    public void onCancelShiftButtonClickWeek(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
    public void onCreateShiftClickWeek(View view) {
        // Create necessary parameters to create Shift Object
        this.timeOfShift = selectTimeOfShift();
        if (this.timeOfShift.equals(Available.CANNOT)) {
            Toast.makeText(createShiftWeekday.this, "ERROR: MUST select a time for shift on " + this.dateOfShift, Toast.LENGTH_SHORT).show();
            return;
        }
        String shift_id = this.dateOfShift + "-" + this.timeOfShift;
        if (this.shiftStorage.contains(shift_id)) {
            Toast.makeText(createShiftWeekday.this, "ERROR: Shift at selected time and date already exists", Toast.LENGTH_SHORT).show();
            return;
        }
        //Toast.makeText(createShiftWeekday.this, shift_id, Toast.LENGTH_SHORT).show();

        // create Shift object.
        WeekdayShifts weekDayShift = new WeekdayShifts(this.dateOfShift, this.timeOfShift, this.dayOfWeek, this.selectedCalendarDate);
        //Toast.makeText(createShiftWeekday.this, "This is shift ID for weekdayShift Object: " + weekDayShift.getShiftID(), Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        SharedPreferences.Editor editor = shiftStorage.edit();
        GsonBuilder builder = new GsonBuilder();
        String shiftToString = gson.toJson(weekDayShift);
        Log.i("shift Object", "this is the shift object: " + shiftToString);
        editor.putString(weekDayShift.getShiftID(), shiftToString);
        editor.apply();

        // Returning the calendar to display onto the Calendar. Also need to store it
        String returnedCalendarDateString = gson.toJson(this.selectedCalendarDate);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", returnedCalendarDateString);
        setResult(RESULT_OK, resultIntent);
        finish();
    }


}