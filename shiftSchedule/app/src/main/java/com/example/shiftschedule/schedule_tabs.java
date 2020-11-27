package com.example.shiftschedule;

import android.os.Bundle;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.shiftschedule.shifts.Shift;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.example.shiftschedule.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class schedule_tabs extends AppCompatActivity {

    /*
    Member Variables
    shifts             - An array of created shifts
     */
    List<Shift> shifts = new ArrayList<>();
    List<EventDay> events = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_tabs);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        // Instantiating CustomViewPager Object
        CustomViewPager ViewPagerObj = (CustomViewPager)findViewById(R.id.view_pager);
        // Disable Side-swiping by called setPagingEnabled method in CustomViewPager class
        ViewPagerObj.setPagingEnabled(false);

        TabLayout tabs = findViewById(R.id.EA_tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        //testing some things
        Calendar calendar = Calendar.getInstance();
        CalendarView calendarView = (CalendarView)findViewById(R.id.calendar_month_view);
        calendarView.setOnDayClickListener(new OnDayClickListener () {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}

