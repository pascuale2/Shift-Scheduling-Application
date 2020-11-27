package com.example.shiftschedule;

import android.os.Bundle;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class schedule_month_view extends AppCompatActivity {

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

        Calendar calendar = Calendar.getInstance();
        com.applandeo.materialcalendarview.CalendarView calendarView = (CalendarView) findViewById(R.id.calendar_month_view);
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                Calendar clickedDayCalendar = eventDay.getCalendar();
                String unformatted_date = clickedDayCalendar.getTime().toString();
                String split_string[] = unformatted_date.split(" ");
                String formatted_date = split_string[5] + "/" + split_string[1] + "/" + split_string[2];

//                Toast.makeText(test.this, m.group(), Toast.LENGTH_SHORT).show();

//                View view = LayoutInflater.from(schedule_month_view.this).inflate(
//                        R.layout.layout_dialog_box,
//                        (ConstraintLayout) findViewById(R.)
                AlertDialog.Builder builder = new AlertDialog.Builder(schedule_month_view.this, R.style.AlertDialogTheme);
                View view = LayoutInflater.from(schedule_month_view.this).inflate(R.layout.layout_dialog_box,
                        (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
                );
                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                TextView disp_date = (TextView) findViewById(R.id.text_date);
                disp_date.setText(formatted_date);
            }
        });
    }
}