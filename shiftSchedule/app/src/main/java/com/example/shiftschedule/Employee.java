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
     dateEmployed, localDate            - The Year-Month-Date the Employee was employed
     trainedOpening, bool               - Flag whether the employee is trained for opening
     trainedClosing, bool               - Flag whether the employee is trained for closing
     */
    protected String email;
    private String fullName;
    private int Age;
    private String Sex;
    private LocalDate dateEmployed;
    protected boolean trainedOpening;
    protected boolean trainedClosing;
    // There will be a hashMap array that holds all the shifts the employee currently has -- Don't worry about this yet,
    // will be filled in another screen/function

    // Initializes an Employee object
    public Employee(String email, String fullName, int Age, LocalDate employedDate, boolean trainedClosing, boolean trainedOpening) {
        this.email = email;
        this.fullName = fullName;
        this.Age = Age;
        this.dateEmployed = employedDate;
        this.trainedClosing = trainedClosing;
        this.trainedOpening = trainedOpening;
    }
    // TODO: Fix setEmail Once employee Availaibility is done and stored (if email changes, the availability should update to new email also)
    // GETTER FUNCTIONS
    public int getAge() { return Age; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getSex() { return Sex; }
    public LocalDate getDateEmployed() { return dateEmployed; }
    public boolean isTrainedClosing() { return trainedClosing; }
    public boolean isTrainedOpening() { return trainedOpening; }
    // SETTER FUNCTIONS

    public void setAge(int age) { Age = age; }

    public void setDateEmployed(LocalDate dateEmployed) { this.dateEmployed = dateEmployed; }

    public void setEmail(String email) { this.email = email; }

    public void setSex(String sex) { Sex = sex; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public void setTrainedClosing(boolean trainedClosing) { this.trainedClosing = trainedClosing; }

    public void setTrainedOpening(boolean trainedOpening) { this.trainedOpening = trainedOpening; }

}
