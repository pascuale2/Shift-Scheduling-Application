package com.example.shiftschedule;
import java.util.ArrayList;
import java.time.*;
import java.util.Calendar;

public class Schedule {

    private LocalDate selectedDate; //Input
    ArrayList<EmployeeAvailability> assignEmpl = new ArrayList<EmployeeAvailability>(); //Input
    ArrayList<DaySchedule> assignDay = new ArrayList<DaySchedule>();
    ArrayList<BusyDaySchedule> assignBusyDay = new ArrayList<BusyDaySchedule>();
    //ArrayList<Integer> Holidays = new ArrayList<Integer>(); //Input - Extended Class?

    public void MonthlySchedule(int year, int month, int day){
        /*
            Get current month from calendar
            Loop over all dates in the calendar
            Foreach date, find the respective day
                Read availability of all employees for current day
                    may need a temp array to assign shifts to employees based on criteria given
                Assign opening and closing shift for current day
                If the day is a holiday, assign to both opening and closing
                Store information to dayshift obj
                Add dayshift obj to assignDay arrlist
         */

        selectedDate = LocalDate.of(year, month, day);
        int number_Of_DaysInMonth = 0;
        int monthValue = selectedDate.getMonthValue();
        String MonthOfName = "";
        Month m = selectedDate.getMonth();


        MonthOfName = m.name();

        //Get number of days per month
        switch (monthValue) {
            case 1:
                MonthOfName = "January";
                number_Of_DaysInMonth = 31;
                break;
            case 2:
                MonthOfName = "February";
                if ((selectedDate.getYear() % 400 == 0) || ((selectedDate.getYear() % 4 == 0) && (selectedDate.getYear() % 100 != 0))) {
                    number_Of_DaysInMonth = 29;
                } else {
                    number_Of_DaysInMonth = 28;
                }
                break;
            case 3:
                MonthOfName = "March";
                number_Of_DaysInMonth = 31;
                break;
            case 4:
                MonthOfName = "April";
                number_Of_DaysInMonth = 30;
                break;
            case 5:
                MonthOfName = "May";
                number_Of_DaysInMonth = 31;
                break;
            case 6:
                MonthOfName = "June";
                number_Of_DaysInMonth = 30;
                break;
            case 7:
                MonthOfName = "July";
                number_Of_DaysInMonth = 31;
                break;
            case 8:
                MonthOfName = "August";
                number_Of_DaysInMonth = 31;
                break;
            case 9:
                MonthOfName = "September";
                number_Of_DaysInMonth = 30;
                break;
            case 10:
                MonthOfName = "October";
                number_Of_DaysInMonth = 31;
                break;
            case 11:
                MonthOfName = "November";
                number_Of_DaysInMonth = 30;
                break;
            case 12:
                MonthOfName = "December";
                number_Of_DaysInMonth = 31;
        }

        for(int i = 0; i <= number_Of_DaysInMonth; i++){
            LocalDate mD = LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(),i);
            DayOfWeek dow = mD.getDayOfWeek();
            String nameDow = dow.name();
            for (EmployeeAvailability ea: assignEmpl) {

                if(nameDow.compareTo("Sunday") == 0){
                    if(ea.Sunday == Available.OPENING && ea.Sunday == Available.CLOSING && ea.Sunday == Available.ALLDAY){
                        assignBusyDay.add(new BusyDaySchedule(ea.email,i));
                    } else {
                    if (ea.Sunday == Available.OPENING){
                        assignDay.add(new DaySchedule(ea.email,"",i));
                    } else if (ea.Sunday == Available.CLOSING){
                        assignDay.add(new DaySchedule("",ea.email,i));
                    } else if (ea.Sunday == Available.CANNOT){
                        assignDay.add(new DaySchedule("","",i));
                    }
                    }
                }

                else if(nameDow.compareTo("Monday") == 1){
                    if(ea.Monday == Available.OPENING && ea.Monday == Available.CLOSING && ea.Monday == Available.ALLDAY){
                        assignBusyDay.add(new BusyDaySchedule(ea.email,i));
                    } else {
                        if (ea.Monday == Available.OPENING){
                            assignDay.add(new DaySchedule(ea.email,"",i));
                        } else if (ea.Monday == Available.CLOSING){
                            assignDay.add(new DaySchedule("",ea.email,i));
                        } else if (ea.Monday == Available.CANNOT){
                            assignDay.add(new DaySchedule("","",i));
                        }
                    }

                } else if(nameDow.compareTo("Tuesday") == 2){
                    if(ea.Tuesday == Available.OPENING && ea.Tuesday == Available.CLOSING && ea.Tuesday == Available.ALLDAY){
                        assignBusyDay.add(new BusyDaySchedule(ea.email,i));
                    } else {
                        if (ea.Tuesday == Available.OPENING){
                            assignDay.add(new DaySchedule(ea.email,"",i));
                        } else if (ea.Tuesday == Available.CLOSING){
                            assignDay.add(new DaySchedule("",ea.email,i));
                        } else if (ea.Tuesday == Available.CANNOT){
                            assignDay.add(new DaySchedule("","",i));
                        }
                    }

                }
                else if(nameDow.compareTo("Wednesday") == 3){
                    if(ea.Wednesday == Available.OPENING && ea.Wednesday == Available.CLOSING && ea.Wednesday == Available.ALLDAY){
                        assignBusyDay.add(new BusyDaySchedule(ea.email,i));
                    } else {
                        if (ea.Wednesday == Available.OPENING){
                            assignDay.add(new DaySchedule(ea.email,"",i));
                        } else if (ea.Wednesday == Available.CLOSING){
                            assignDay.add(new DaySchedule("",ea.email,i));
                        } else if (ea.Wednesday == Available.CANNOT){
                            assignDay.add(new DaySchedule("","",i));
                        }
                    }

                } else if(nameDow.compareTo("Thursday") == 4){
                    if(ea.Thursday == Available.OPENING && ea.Thursday == Available.CLOSING && ea.Thursday == Available.ALLDAY){
                        assignBusyDay.add(new BusyDaySchedule(ea.email,i));
                    } else {
                        if (ea.Thursday == Available.OPENING){
                            assignDay.add(new DaySchedule(ea.email,"",i));
                        } else if (ea.Thursday == Available.CLOSING){
                            assignDay.add(new DaySchedule("",ea.email,i));
                        } else if (ea.Thursday == Available.CANNOT){
                            assignDay.add(new DaySchedule("","",i));
                        }
                    }

                } else if(nameDow.compareTo("Friday") == 5){
                    if(ea.Friday == Available.OPENING && ea.Friday == Available.CLOSING && ea.Friday == Available.ALLDAY){
                        assignBusyDay.add(new BusyDaySchedule(ea.email,i));
                    } else {
                        if (ea.Friday == Available.OPENING){
                            assignDay.add(new DaySchedule(ea.email,"",i));
                        } else if (ea.Friday == Available.CLOSING){
                            assignDay.add(new DaySchedule("",ea.email,i));
                        } else if (ea.Friday == Available.CANNOT){
                            assignDay.add(new DaySchedule("","",i));
                        }
                    }

                } else if(nameDow.compareTo("Saturday") == 6){
                    if(ea.Saturday == Available.OPENING && ea.Saturday == Available.CLOSING && ea.Saturday == Available.ALLDAY){
                        assignBusyDay.add(new BusyDaySchedule(ea.email,i));
                    } else {
                        if (ea.Saturday == Available.OPENING){
                            assignDay.add(new DaySchedule(ea.email,"",i));
                        } else if (ea.Saturday == Available.CLOSING){
                            assignDay.add(new DaySchedule("",ea.email,i));
                        } else if (ea.Saturday == Available.CANNOT){
                            assignDay.add(new DaySchedule("","",i));
                        }
                    }

                }
            }

        }

    }

    public ArrayList<DaySchedule> GetSchedule(){
        return assignDay;
    }

}
