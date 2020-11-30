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
import com.example.shiftschedule.shifts.WeekendShifts;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;

public class createShiftWeekend extends AppCompatActivity {
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
    protected SharedPreferences shiftStorage;
    protected TextView displayDate;
    protected RadioButton allday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shift_weekend);
        // Point member variables/attributes to elements within the xml file
        this.timeOfShift = Available.ALLDAY;
        this.displayDate = (TextView)findViewById(R.id.CS_WeekendDate);
        this.shiftStorage = getApplicationContext().getSharedPreferences("shifts", Context.MODE_PRIVATE);
        this.allday = (RadioButton) findViewById(R.id.CS_WeekendAllDay);
        this.submitShiftButton = (Button) findViewById(R.id.CS_WeekendCreateShift);
        this.cancelShiftButton = (Button) findViewById(R.id.CS_WeekendCancel);
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
        Window window = createShiftWeekend.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(createShiftWeekend.this, R.color.hot_pink));
    }
    public void onCancelShiftButtonClickWeekend(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }


    public Available getTimeSelected() {
        if (this.allday.isChecked())
            return Available.ALLDAY;
        else
            return Available.CANNOT;
    }

    public void onCreateShiftClickWeekend(View view) {
        this.timeOfShift = getTimeSelected();
        if (this.timeOfShift.equals(Available.CANNOT)) {
            Toast.makeText(createShiftWeekend.this, "ERROR: MUST select a time for shift on " + this.dateOfShift, Toast.LENGTH_SHORT).show();
            return;
        }
        String shift_id = this.dateOfShift + "-" + this.timeOfShift;
        if (this.shiftStorage.contains(shift_id)) {
            Toast.makeText(createShiftWeekend.this, "ERROR: Shift at selected time and date already exists", Toast.LENGTH_SHORT).show();
            return;
        }
        WeekendShifts weekendShift = new WeekendShifts(this.dateOfShift, this.timeOfShift, this.dayOfWeek, this.selectedCalendarDate);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = shiftStorage.edit();
        GsonBuilder builder = new GsonBuilder();
        String shiftToString = gson.toJson(weekendShift);
        Log.i("shift Object", "this is the shift object: " + shiftToString);
        editor.putString(weekendShift.getShiftID(), shiftToString);
        editor.commit();
        String returnedCalendarDateString = gson.toJson(this.selectedCalendarDate);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", returnedCalendarDateString);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}