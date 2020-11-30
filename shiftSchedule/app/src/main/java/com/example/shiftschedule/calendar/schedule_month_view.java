package com.example.shiftschedule.calendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.shiftschedule.R;
import com.example.shiftschedule.shiftScreens.createShiftWeekday;
import com.example.shiftschedule.shiftScreens.createShiftWeekend;
import com.example.shiftschedule.shiftScreens.listShiftOnDay;
import com.example.shiftschedule.shiftScreens.viewShiftDetails;
import com.example.shiftschedule.shifts.Shift;
import com.example.shiftschedule.shifts.WeekdayShifts;
import com.example.shiftschedule.shifts.WeekendShifts;
import com.example.shiftschedule.ui.main.dayOfShift;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class schedule_month_view extends AppCompatActivity {
    protected List<Shift> shifts = new ArrayList<>();
    protected List<EventDay> mEventDays = new ArrayList<>();
    protected EventDay test;
    protected SharedPreferences shiftStorage;
    public static final int DRAW_SHIFT = 1;
    public static final int VIEW_SHIFT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_month_view);
        //Uncomment the following line to clear sharedPreferences file for shifts
        //clearSharedPreferences();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Calendar min = new GregorianCalendar();
        this.shiftStorage = getApplicationContext().getSharedPreferences("shifts", Context.MODE_PRIVATE);
        final Calendar calendar = Calendar.getInstance();
        final com.applandeo.materialcalendarview.CalendarView calendarView = (CalendarView) findViewById(R.id.calendar_month_view);
        fillCalendarView(calendarView);
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                final Calendar clickedDayCalendar = eventDay.getCalendar();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");



                String getCurrentDateTime = sdf.format(calendar.getTime());

                String unformatted_date = clickedDayCalendar.getTime().toString();
                String split_string[] = unformatted_date.split(" ");
                final String formatted_date = split_string[5] + "/" + split_string[1] + "/" + split_string[2];
                final String dayOfWeek = split_string[0];



                //Toast.makeText(schedule_month_view.this, "This is the day of the week: " + split_string[0], Toast.LENGTH_SHORT).show();
                // split_string[0] is the day.

//                Toast.makeText(test.this, m.group(), Toast.LENGTH_SHORT).show();


//                View view = LayoutInflater.from(schedule_month_view.this).inflate(
//                        R.layout.layout_dialog_box,
//                        (ConstraintLayout) findViewById(R.)
                AlertDialog.Builder builder = new AlertDialog.Builder(schedule_month_view.this, R.style.AlertDialogTheme);
                View view = LayoutInflater.from(schedule_month_view.this).inflate(R.layout.layout_dialog_box,
                        (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
                );
                //builder.setTitle(formatted_date);
                //builder.setMessage(formatted_date);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                TextView displayDate = (TextView) view.findViewById(R.id.text_date);
                Button createShift = (Button) view.findViewById(R.id.create_button);
                Button holidayShift = (Button) view.findViewById(R.id.holiday_button);
                Button viewShiftButton = (Button) view.findViewById(R.id.view_button);
                ImageButton cancelAlert = (ImageButton) view.findViewById(R.id.CAL_cancel_button);
                if (Calendar.getInstance().after(clickedDayCalendar)) {
                    Toast.makeText(schedule_month_view.this, "WARNING: Cannot create a Shift on a past date or current date", Toast.LENGTH_SHORT).show();
                    createShift.setEnabled(false);
                    createShift.setClickable(false);
                    createShift.setBackgroundResource(R.drawable.disabled_corner);

                    holidayShift.setEnabled(false);
                    holidayShift.setClickable(false);
                    holidayShift.setBackgroundResource(R.drawable.disabled_corner);
                }

                cancelAlert.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                viewShiftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("dayOfWeek", dayOfWeek);
                        bundle.putString("date", formatted_date);
                        //TODO: Start new Intent here for viewing shift. Viewing shift takes you to screen with shift details. Click button to add employees.
                        int matches = getSelectedShift(calendarView, formatted_date);
                        // if there are 2 shifts on the same day (OPENING AND CLOSING) then we need to pass both shift id's
                        if (matches >= 2) {
                            //need to make a list of shifts.
                            dayOfShift shiftDay = getShiftID(calendarView, formatted_date);
                            bundle.putString("opening", shiftDay.getOpeningShiftId());
                            bundle.putString("closing", shiftDay.getClosingShiftId());
                            Intent intent = new Intent(schedule_month_view.this, listShiftOnDay.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        else if (matches == 1) {
                            // we can view the shiftDetails right away.
                            String shiftId = getSingleShift(calendarView, formatted_date);
                            bundle.putString("shift_id", shiftId);
                            Intent intentViewShift = new Intent(schedule_month_view.this, viewShiftDetails.class);
                            intentViewShift.putExtras(bundle);
                            startActivity(intentViewShift);
                        }
                        else {
                            Toast.makeText(schedule_month_view.this, "ERROR: Cannot view shift because shift does not exist on this date", Toast.LENGTH_SHORT).show();
                        }
                        //Intent intent = new Intent(schedule_month_view.this, viewShiftDetails.class);
                        // I need the shift-id. The shift-id is the formatted_date-Availability.
                        // The problem is that there can be two shifts on one day. So when they hit view shift we need to grab the correct one.

                    }
                });

                createShift.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // goes to create Shift activity
                        Gson gson = new Gson();
                        String selectedCalendarString = gson.toJson(clickedDayCalendar);
                        Bundle bundle = new Bundle();
                        bundle.putString("dayOfWeek", dayOfWeek);
                        bundle.putString("calendar", selectedCalendarString);
                        bundle.putString("date", formatted_date);
                        switch(dayOfWeek)
                        {
                            case "Sun":
                            case "Sat":
                                //Toast.makeText(schedule_month_view.this, "Creating a weekend Shift", Toast.LENGTH_SHORT).show();
                                Intent intentWeekend = new Intent(schedule_month_view.this, createShiftWeekend.class);
                                intentWeekend.putExtras(bundle);
                                startActivityForResult(intentWeekend, 1);
                                break;
                                default:
                                    Intent intent = new Intent(schedule_month_view.this, createShiftWeekday.class);
                                    intent.putExtras(bundle);
                                    startActivityForResult(intent, 1);
                        }


                        // Think about what information needs to be passed first. Calendar, dayOfWeek,
//                        EventDay test = new EventDay(clickedDayCalendar, R.drawable.schedule);
//                        mEventDays.add(test);

                    }
                });


                if (displayDate == null) {
                    Toast.makeText(schedule_month_view.this, "Dis display date bitch empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                displayDate.setText(formatted_date);
                // only setEvents if the shift is saved, not when clicked.
                //calendarView.setEvents(mEventDays);
            }
        });

    }

    // checks the type of shift. We can't convert the string into a class without knowing which class it is (Weekend, Weekday, Holiday). 0 = Holiday, 1 = Weekend, 2 = Weekday.
    protected int checkShiftType(String json) {
        String[] types = {"Holiday", "Weekend", "Weekday"};
        int i;
        for (i = 0; i < types.length; i++) {
            if (json.contains(types[i])) break;
        }
            switch (i) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                default:
                    return 2;
            }
    }

    protected String getSingleShift(CalendarView calendarView, String formatted_date) {
        SharedPreferences.Editor editor = this.shiftStorage.edit();
        Map<String, ?> allShifts = this.shiftStorage.getAll();
        for (Map.Entry<String, ?> entry: allShifts.entrySet()) {
            String shift_id = entry.getKey();
            if (shift_id.contains(formatted_date))
                return entry.getKey();
        }
        return "";
    }
    protected dayOfShift getShiftID(CalendarView calendarView, String formatted_date) {
        List<String> matchingShiftIds = new ArrayList<>();
        SharedPreferences.Editor editor = this.shiftStorage.edit();
        Map<String, ?> allShifts = this.shiftStorage.getAll();
        int numMatches = 0;
        for (Map.Entry<String, ?> entry: allShifts.entrySet()) {
            String shift_id = entry.getKey();
            if (shift_id.contains(formatted_date))
                matchingShiftIds.add(entry.getKey());
        }
        dayOfShift shiftDay = new dayOfShift(matchingShiftIds.get(0), matchingShiftIds.get(1));
        Log.i("all shiftIds", matchingShiftIds.toString());
        return shiftDay;
    }

    protected int getSelectedShift(CalendarView calendarView, String formatted_date) {
        SharedPreferences.Editor editor = this.shiftStorage.edit();
        Map<String, ?> allShifts = this.shiftStorage.getAll();
        int numMatches = 0;
        for (Map.Entry<String, ?> entry: allShifts.entrySet()) {
            String shift_id = entry.getKey();
            if (shift_id.contains(formatted_date))
                numMatches++;
        }
        return numMatches;
    }

    protected void fillCalendarView(CalendarView calendarView) {
        Shift viewShift;
        SharedPreferences.Editor editor = this.shiftStorage.edit();
        Gson gson = new Gson();
        Map<String, ?> allShifts = this.shiftStorage.getAll();
        if (allShifts.isEmpty()) {
            Toast.makeText(schedule_month_view.this, "No shifts created to display", Toast.LENGTH_SHORT).show();
            return;
        }
        for (Map.Entry<String, ?> entry: allShifts.entrySet()) {
            String json = shiftStorage.getString(entry.getKey(), "");
            // Problem is that we don't know what class viewShift is going to be (WeekdayShift, WeekendShift, Holiday Shift).
            Log.i("JsonString", json);
            boolean check = json.contains("type");
            Log.i("jsonContains: ", String.valueOf(check));
            int shiftFlag = checkShiftType(json);
            if (shiftFlag == 1) {
                viewShift = gson.fromJson(json, WeekendShifts.class);
                this.shifts.add(viewShift);
                mEventDays.add(new EventDay(viewShift.getCalendar(), R.drawable.schedule));
            }
            else if (shiftFlag == 2) {
                viewShift = gson.fromJson(json, WeekdayShifts.class);
                this.shifts.add(viewShift);
                mEventDays.add(new EventDay(viewShift.getCalendar(), R.drawable.schedule));
            }
            else {
                Toast.makeText(schedule_month_view.this, "Failed to check contain rules", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(schedule_month_view.this, json, Toast.LENGTH_SHORT).show();
            //this.shifts.add(viewShift);
            //mEventDays.add(new EventDay(viewShift.getCalendar(), R.drawable.schedule));
        }
        calendarView.setEvents(mEventDays);
    }

    protected void clearSharedPreferences() {
        SharedPreferences.Editor editor = shiftStorage.edit();
        editor.clear();
        editor.commit();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIEW_SHIFT) {
            if (resultCode == RESULT_OK) {

            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(schedule_month_view.this, "Failed to save changes to shift", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == DRAW_SHIFT) {
            if (resultCode == RESULT_OK) {
                // Ideally in here we will be getting back the Calendar if they chose to create a shift
                String selectedCalendarDateString = data.getStringExtra("result");
                Gson gson = new Gson();
                Calendar selectedCalendarDate = gson.fromJson(selectedCalendarDateString, Calendar.class);
                //TODO: Make Cases for displaying # of shifts
                mEventDays.add(new EventDay(selectedCalendarDate, R.drawable.schedule));
                final com.applandeo.materialcalendarview.CalendarView calendarView = (CalendarView) findViewById(R.id.calendar_month_view);
                calendarView.setEvents(mEventDays);
                Toast.makeText(schedule_month_view.this, "Shift Creation Successful", Toast.LENGTH_SHORT).show();
                //Toast.makeText(schedule_month_view.this, selectedCalendarDateString, Toast.LENGTH_SHORT).show();

            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(schedule_month_view.this, "Shift Creation Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}