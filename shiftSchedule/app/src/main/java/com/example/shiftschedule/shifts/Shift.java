package com.example.shiftschedule.shifts;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.shiftschedule.Available;
import com.example.shiftschedule.Employee;
import com.example.shiftschedule.EmployeeAvailability;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public abstract class Shift {
    /*
     Abstract Class created By Alex Creencia
     All different types of shifts will extend/implement their own version of this Shift class

     MEMBER VARIABLES
     shiftID                      A ID of the form ( yyyy/MM/dd-TimeOfShift ). This is formatted this way for easy checking for duplicates. (e.g. 2020/11/29-OPENING)
     Time                         The time of the shift. Re-uses the EmployeeAvailability
     Date                         The Date of the shift (a string formatted like a date-time format)
     Employee                     An array of Employee's that represent which employee's are working the shift.
     availabilityStorage          The file containing the availabilities of all employees registered in the app
     Day                          The day of the week of the shift (Monday, Tuesday, Wednesday, Friday, etc etc)
     */
     protected String shiftID;
     public Available time;
     protected String date;
     protected SharedPreferences availabilityStorage;
     protected String day;
     protected List<Employee> employeeList = new ArrayList<>();
     public Shift(String dateOfShift, Available timeOfShift, Context context, String dayOfWeek) {
         this.shiftID = dateOfShift + "-" + timeOfShift;
         this.time = timeOfShift;
         this.date = dateOfShift;
         this.availabilityStorage = context.getSharedPreferences("availability", Context.MODE_PRIVATE);
         this.day = dayOfWeek;
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

    public EmployeeAvailability getAvailability(Employee employee) {
        Gson gson = new Gson();
        String json = this.availabilityStorage.getString(employee.getEmail(), "");
        EmployeeAvailability availability = gson.fromJson(json, EmployeeAvailability.class);
        return availability;
    }

    // function which checks the availability of an employee attached to shift.
    public abstract boolean checkAvailability(EmployeeAvailability availability);
     // function which checks if at least 1 employee is trained to work that shift.
    public abstract boolean checkIfShiftCovered();

}
