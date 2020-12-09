package com.example.shiftschedule.employee;

import com.example.shiftschedule.Available.Available;

/*
  Employee Availability class Created by Alex Creencia, based on Design notes created
   by Team Lovelace (Erwin P, Jaxon S, Wamiq H, Alex C)
   October 16, 2020
 */
public class EmployeeAvailability {
    /*
        MEMBER VARIABLE                        DESCRIPTION
        email                                  The employee's email so we can link their availability to them
        Monday                                 Employee's availability Monday
        Tuesday                                Employee's Availability Tuesday
        Wednesday                              Employee's Availability Wednesday
        Thursday                                ...
        Friday                                  ...
        Saturday                                ...
        Sunday                                  ...
     */
    public String email;                       // The employee's email so we can link their availability to them
    protected Available Monday;                // Employee's availability Monday
    protected Available Tuesday;               // Employee's Availability Tuesday
    protected Available Wednesday;             // Employee's Availability Wednesday
    protected Available Thursday;              // Employee's Availability Thursday
    protected Available Friday;                // Employee's Availability Friday
    protected Available Saturday;              // Employee's Availability Saturday
    protected Available Sunday;                // Employee's Availability Sunday
    protected Available Holiday;               // Employee's Availability On Holidays/Special Days

    // Caller Function to create/instance an EmployeeAvailability Object
    // Their availability will be set through buttons or drop downs, so we don't need all the information at once
    public EmployeeAvailability(String employeeEmail) {
        this.email = employeeEmail;
        this.Monday = Available.CANNOT;
        this.Tuesday = Available.CANNOT;
        this.Wednesday = Available.CANNOT;
        this.Thursday = Available.CANNOT;
        this.Friday = Available.CANNOT;
        this.Saturday = Available.CANNOT;
        this.Sunday = Available.CANNOT;
        this.Holiday = Available.CANNOT;
    }

    // bool checkWork function will be put in once wamiq is done creating shifts

    // SETTER FUNCTIONS
    // This will only change the email in this object, in order to change the linking, we have to do it in another screen
    public void setEmail(String email) {
        this.email = email;
    }

    public void setMonday(Available monday) {
        Monday = monday;
    }

    public void setTuesday(Available tuesday) {
        Tuesday = tuesday;
    }

    public void setWednesday(Available wednesday) {
        Wednesday = wednesday;
    }

    public void setThursday(Available thursday) {
        Thursday = thursday;
    }

    public void setFriday(Available friday) {
        Friday = friday;
    }

    public void setSaturday(Available saturday) {
        Saturday = saturday;
    }

    public void setSunday(Available sunday) {
        Sunday = sunday;
    }

    public void setHoliday(Available holiday) { Holiday = holiday; }

    // GETTER FUNCTIONS

    public String getEmail() {
        return email;
    }

    public Available getMonday() {
        return Monday;
    }

    public Available getTuesday() {
        return Tuesday;
    }

    public Available getWednesday() {
        return Wednesday;
    }

    public Available getThursday() {
        return Thursday;
    }

    public Available getFriday() {
        return Friday;
    }

    public Available getSaturday() {
        return Saturday;
    }

    public Available getSunday() {
        return Sunday;
    }

    public Available getHoliday() { return Holiday; }

}

