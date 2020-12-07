package com.example.shiftschedule.shifts;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.shiftschedule.Available.Available;
import com.example.shiftschedule.employee.Employee;
import com.example.shiftschedule.employee.EmployeeAvailability;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class Shift {
    /*
     Abstract Class created By Alex Creencia
     All different types of shifts will extend/implement their own version of this Shift class

     MEMBER VARIABLES
     shiftID                      A ID of the form ( yyyy/Mon/dd-TimeOfShift ). This is formatted this way for easy checking for duplicates. (e.g. 2020/Nov/29-OPENING)
     Time                         The time of the shift. Re-uses the EmployeeAvailability
     Date                         The Date of the shift (a string formatted like a date-time format)
     Employee                     An array of Employee's that represent which employee's are working the shift.
     availabilityStorage          The file containing the availabilities of all employees registered in the app
     Day                          The day of the week of the shift (Monday, Tuesday, Wednesday, Friday, etc etc)
     Calendar calendar            The calendar representation of the shift Date. Only for visual purposes on custom Calendar objects.
     special                      Determines whether shift is special (like a holiday) or not.
     */
     protected String shiftID;
     public Available time;
     protected String date;
     protected SharedPreferences availabilityStorage;
     protected String day;
     protected List<Employee> employeeList = new ArrayList<>();
     protected Calendar calendar;
     protected boolean special;
     public int numOfEmployees = 2;
     protected String type;
     public Shift(String dateOfShift, Available timeOfShift, String dayOfWeek, Calendar calendarDate) {
         this.shiftID = dateOfShift + "-" + timeOfShift;
         this.time = timeOfShift;
         this.date = dateOfShift;
         this.day = dayOfWeek;
         this.calendar = calendarDate;
         this.special = false;
     }


     public abstract void addEmployee(Employee employee, Context context);
     public List<Employee> getEmployeeList() {
         return this.employeeList;
     }

    public Available getTime() {
        return this.time;
    }

    public String getShiftID() {
        return this.shiftID;
    }

    public String getDate() {
        return this.date;
    }

    public String getDay() { return day; }

    public boolean checkIfEmployeeAlreadyWorks(Employee employee) {
         if (this.employeeList.contains(employee))
             return true;
         else
             return false;
    }

    public EmployeeAvailability getAvailability(Employee employee, Context context) {
        Gson gson = new Gson();
        availabilityStorage = context.getSharedPreferences("availability", Context.MODE_PRIVATE);
        String json = this.availabilityStorage.getString(employee.getEmail(), "");
        EmployeeAvailability availability = gson.fromJson(json, EmployeeAvailability.class);
        return availability;
    }

    public Calendar getCalendar() {
         return this.calendar;
    }
    public boolean getSpecial() {
         return this.special;
    }
    // function which checks the availability of an employee attached to shift.
    public abstract boolean checkAvailability(EmployeeAvailability availability);
     // function which checks if at least 1 employee is trained to work that shift.
    public abstract boolean checkIfShiftCovered();
    public void setSpecial() {
        this.special = true;
        this.numOfEmployees = 3;
    }


}
