package com.example.shiftschedule;

import java.time.LocalDate;

/*
  Employee class created by Alex Creencia
  October 16, 2020
 */
public class Employee {
    /*
     CLASS MEMBERS, data-type               DESCRIPTION
     email, string                      - The email of the Employee
     Full Name, string                  - The Full name of the Employee
     Age, age                           - The Age of the Employee
     Sex, enum                          - The Sex of the Employee
     dateEmployed, String            - The Year-Month-Date the Employee was employed
     trainedOpening, bool               - Flag whether the employee is trained for opening
     trainedClosing, bool               - Flag whether the employee is trained for closing
     */
    protected String email;
    private String fullName;
    private int Age;
    private String Sex;
    private String dateEmployed;
    protected boolean trainedOpening;
    protected boolean trainedClosing;
    // Create a new screen


    // Initializes an Employee object
    public Employee(String email, String fullName, int Age, String employedDate, boolean trainedClosing, boolean trainedOpening) {
        this.email = email;
        this.fullName = fullName;
        this.Age = Age;
        this.dateEmployed = employedDate;
        this.trainedClosing = trainedClosing;
        this.trainedOpening = trainedOpening;
    }

    // GETTER FUNCTIONS
    public int getAge() { return Age; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getSex() { return Sex; }
    public String getDateEmployed() { return dateEmployed; }
    public String getDateString() { return dateEmployed.toString(); }
    public boolean isTrainedClosing() {
        if (this.trainedClosing == false) {
            return false;
        }
        return true;
    }
    public boolean isTrainedOpening() {
        if (this.trainedOpening == false) {
            return false;
        }
        return true;
    }
    // SETTER FUNCTIONS

    public void setAge(int age) { Age = age; }

    public void setDateEmployed(String dateEmployed) { this.dateEmployed = dateEmployed; }

    public void setEmail(String email) { this.email = email; }

    public void setSex(String sex) { Sex = sex; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public void setTrainedClosing(boolean trainedClosing) { this.trainedClosing = trainedClosing; }

    public void setTrainedOpening(boolean trainedOpening) { this.trainedOpening = trainedOpening; }

    @Override
    public String toString() {
        String employeeString = "Email: " + email + "\n" +
                "full name: " + fullName + "\n" +
                "Age: " + Age + "\n" +
                "Sex: " + Sex + "\n" +
                "Date Employed: " + dateEmployed.toString() + "\n" +
                "Trained Closing: " + trainedClosing + "\n" +
                "Trained Opening: " + trainedOpening;
        return employeeString;
        /*
        return "Employee{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", Age=" + Age +
                ", Sex='" + Sex + '\'' +
                ", dateEmployed=" + dateEmployed +
                ", trainedOpening=" + trainedOpening +
                ", trainedClosing=" + trainedClosing +
                '}';

         */
    }
}
