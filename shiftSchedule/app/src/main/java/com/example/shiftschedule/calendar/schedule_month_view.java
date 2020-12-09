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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                // split_string[0] is the day.

                AlertDialog.Builder builder = new AlertDialog.Builder(schedule_month_view.this, R.style.AlertDialogTheme);
                View view = LayoutInflater.from(schedule_month_view.this).inflate(R.layout.layout_dialog_box,
                        (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
                );

                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                TextView displayDate = (TextView) view.findViewById(R.id.text_date);
                Button createShift = (Button) view.findViewById(R.id.create_button);
                Button viewShiftButton = (Button) view.findViewById(R.id.view_button);
                ImageButton cancelAlert = (ImageButton) view.findViewById(R.id.CAL_cancel_button);

                final int matches = getSelectedShift(calendarView, formatted_date);
                // IF THERE ARE NO SHIFTS ON THIS DAY.
                // ~> Then you can't click on the view shift button
                if (matches == 0){
                    viewShiftButton.setEnabled(false);
                    viewShiftButton.setClickable(false);
                    viewShiftButton.setBackgroundResource(R.drawable.disabled_corner);
                }
                // IF SHIFT IS IN THE PAST OR PRESENT
                // ~> Then you can't click on the create shift button
                if (Calendar.getInstance().after(clickedDayCalendar)) {
                    Toast.makeText(schedule_month_view.this, "WARNING: Cannot create a Shift on a past date or current date", Toast.LENGTH_SHORT).show();
                    createShift.setEnabled(false);
                    createShift.setClickable(false);
                    createShift.setBackgroundResource(R.drawable.disabled_corner);
                }
                // IF THE BACK BUTTON IS CLICKED ON THE DIALOGUE
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
                        // if there are 2 shifts on the same day (OPENING AND CLOSING) then we need to pass both shift id's
                        if (matches >= 2) {
                            //need to make a list of shifts.
                            dayOfShift shiftDay = getShiftID(calendarView, formatted_date);
                            bundle.putString("opening", shiftDay.getOpeningShiftId());
                            bundle.putString("closing", shiftDay.getClosingShiftId());
                            Intent intent = new Intent(schedule_month_view.this, listShiftOnDay.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            alertDialog.dismiss();
                            finish();
                        }
                        else if (matches == 1) {
                            // we can view the shiftDetails right away.
                            String shiftId = getSingleShift(calendarView, formatted_date);
                            bundle.putString("shift_id", shiftId);
                            Intent intentViewShift = new Intent(schedule_month_view.this, viewShiftDetails.class);
                            intentViewShift.putExtras(bundle);
                            startActivity(intentViewShift);
                            alertDialog.dismiss();
                            finish();
                        }
                        else {
                            Toast.makeText(schedule_month_view.this, "ERROR: Cannot view shift because shift does not exist on this date", Toast.LENGTH_SHORT).show();
                        }
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
                                alertDialog.dismiss();
                                break;
                                default:
                                    Intent intent = new Intent(schedule_month_view.this, createShiftWeekday.class);
                                    intent.putExtras(bundle);
                                    startActivityForResult(intent, 1);
                                    alertDialog.dismiss();
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

    // checks the type of shift. We can't convert the string into a class without knowing which class it is (Weekend, Weekday) 1 = Weekend, 2 = Weekday.
    protected int checkShiftType(String json) {
        String[] types = {"Holiday", "Weekend", "Weekday"};
        int i;
        for (i = 0; i < types.length; i++) {
            if (json.contains(types[i])) break;
        }
            switch (i) {
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
        // GETTING COUNT OF DATES ~> date_frequencies
        Map<String,Integer> date_frequencies = new HashMap<>();

        if (allShifts.isEmpty()) {
            Toast.makeText(schedule_month_view.this, "No shifts created to display", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Map.Entry<String, ?> keys: allShifts.entrySet()){
            String json2 = shiftStorage.getString(keys.getKey(),"");
            // IF WEEKDAY SHIFT ONLY
            if (checkShiftType(json2) == 2) {
                Shift view_shift_dates = gson.fromJson(json2, WeekdayShifts.class);
                date_frequencies.put(view_shift_dates.getDate(),date_frequencies.getOrDefault(view_shift_dates.getDate(),0) + 1 );
            }
        }

        for (Map.Entry<String, ?> entry: allShifts.entrySet()) {
            String json = shiftStorage.getString(entry.getKey(), "");
            Log.i("JsonString", json);
            boolean check = json.contains("type");
            Log.i("jsonContains: ", String.valueOf(check));
            int shiftFlag = checkShiftType(json);

            // FOR WEEKEND SHIFTS
            if (shiftFlag == 1) {
                viewShift = gson.fromJson(json, WeekendShifts.class);

                this.shifts.add(viewShift);
                // IF ITS A HOLIDAY THEN SET THE ICON TO HOLIDAY
                if (viewShift.getSpecial()) {
                    mEventDays.add(new EventDay(viewShift.getCalendar(), R.drawable.holiday));
                }
                else{
                    mEventDays.add(new EventDay(viewShift.getCalendar(), R.drawable.allday));
                }
            }
            // FOR WEEKDAY SHIFTS
            else if (shiftFlag == 2) {
                viewShift = gson.fromJson(json, WeekdayShifts.class);
                this.shifts.add(viewShift);
                String shift_timeblock = viewShift.time.toString();
                // IF ITS A HOLIDAY THEN SET THE ICON TO HOLIDAY
                if (viewShift.getSpecial()) {
                    mEventDays.add(new EventDay(viewShift.getCalendar(), R.drawable.holiday));
                }
                else {
                    // IF THERE IS MORE THAN ONE SHIFT CREATED ON THIS DAY DISPLAY OPENING &
                    // CLOSING ICON AT THE SAME TIME
                    if (date_frequencies.get(viewShift.getDate()) > 1) {
                        mEventDays.add(new EventDay(viewShift.getCalendar(), R.drawable.opening_closing));
                    }
                    else {
                        // IF CLOSING SHIFT DISPLAY CLOSING SHIFT ICON
                        if (shift_timeblock.matches("CLOSING")) {
                            mEventDays.add(new EventDay(viewShift.getCalendar(), R.drawable.closing));
                        }
                        // IF OPENING SHIFT DISPLAY OPENING SHIFT ICON
                        else if (shift_timeblock.matches("OPENING")) {
                            mEventDays.add(new EventDay(viewShift.getCalendar(), R.drawable.opening));
                        }
                    }
                }
            }
            else {
                Toast.makeText(schedule_month_view.this, "Failed to check contain rules", Toast.LENGTH_SHORT).show();
            }
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
                // DO NOTHING I GUESS?
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

                final com.applandeo.materialcalendarview.CalendarView calendarView = (CalendarView) findViewById(R.id.calendar_month_view);
                fillCalendarView(calendarView);
                calendarView.setEvents(mEventDays);

                // RELOADS THE PAGE SO THAT THE ICONS CAN REFRESH
                finish();
                startActivity(getIntent());

                Toast.makeText(schedule_month_view.this, "Shift Creation Successful", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(schedule_month_view.this, "Shift Creation Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}