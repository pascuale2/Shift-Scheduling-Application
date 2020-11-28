package com.example.shiftschedule;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class schedule_month_view extends AppCompatActivity {

    List<EventDay> mEventDays = new ArrayList<>();
    EventDay test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_month_view);
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
        final Calendar calendar = Calendar.getInstance();
        final com.applandeo.materialcalendarview.CalendarView calendarView = (CalendarView) findViewById(R.id.calendar_month_view);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                final Calendar clickedDayCalendar = eventDay.getCalendar();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");



                String getCurrentDateTime = sdf.format(calendar.getTime());

                String unformatted_date = clickedDayCalendar.getTime().toString();
                String split_string[] = unformatted_date.split(" ");
                String formatted_date = split_string[5] + "/" + split_string[1] + "/" + split_string[2];
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
                Button cancelAlert = (Button) view.findViewById(R.id.CAL_cancel_button);

                if (Calendar.getInstance().after(clickedDayCalendar)) {
                    Toast.makeText(schedule_month_view.this, "WARNING: Cannot create a Shift on a past date or current date", Toast.LENGTH_SHORT).show();
                    createShift.setEnabled(false);
                    createShift.setClickable(false);
                    //TODO: Erwin I need you to select a color for the disabled button (this is so they cannot create shifts in the past. They can VIEW shifts if there were shifts on that day, but cannot create them.
                    // Also needs a drawable for it because the following line makes it a square
                    //createShift.setBackgroundColor(Color.GRAY);
                }

                cancelAlert.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
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
                        Intent intent = new Intent(schedule_month_view.this, createShiftWeekday.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 1);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Ideally in here we will be getting back the Calendar if they chose to create a shift
                String selectedCalendarDateString = data.getStringExtra("result");
                Gson gson = new Gson();
                Calendar selectedCalendarDate = gson.fromJson(selectedCalendarDateString, Calendar.class);
                mEventDays.add(new EventDay(selectedCalendarDate, R.drawable.schedule));
                final com.applandeo.materialcalendarview.CalendarView calendarView = (CalendarView) findViewById(R.id.calendar_month_view);
                calendarView.setEvents(mEventDays);
                //Toast.makeText(schedule_month_view.this, selectedCalendarDateString, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(schedule_month_view.this, "Shift Creation Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}