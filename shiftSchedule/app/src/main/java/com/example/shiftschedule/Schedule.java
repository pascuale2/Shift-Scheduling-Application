package com.example.shiftschedule;
import com.example.shiftschedule.Available.Available;
import com.example.shiftschedule.employee.EmployeeAvailability;

import java.util.ArrayList;
import java.time.*;

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
                    if(ea.getSunday() == Available.OPENING && ea.getSunday() == Available.CLOSING && ea.getSunday() == Available.ALLDAY){
                        assignBusyDay.add(new BusyDaySchedule(ea.email,i));
                    } else {
                    if (ea.getSunday() == Available.OPENING){
                        assignDay.add(new DaySchedule(ea.email,"",i));
                    } else if (ea.getSunday() == Available.CLOSING){
                        assignDay.add(new DaySchedule("",ea.email,i));
                    } else if (ea.getSunday() == Available.CANNOT){
                        assignDay.add(new DaySchedule("","",i));
                    }
                    }
                }

                else if(nameDow.compareTo("Monday") == 1){
                    if(ea.getMonday() == Available.OPENING && ea.getMonday() == Available.CLOSING && ea.getMonday() == Available.ALLDAY){
                        assignBusyDay.add(new BusyDaySchedule(ea.email,i));
                    } else {
                        if (ea.getMonday() == Available.OPENING){
                            assignDay.add(new DaySchedule(ea.email,"",i));
                        } else if (ea.getMonday() == Available.CLOSING){
                            assignDay.add(new DaySchedule("",ea.email,i));
                        } else if (ea.getMonday() == Available.CANNOT){
                            assignDay.add(new DaySchedule("","",i));
                        }
                    }

                } else if(nameDow.compareTo("Tuesday") == 2){
                    if(ea.getTuesday() == Available.OPENING && ea.getTuesday() == Available.CLOSING && ea.getTuesday() == Available.ALLDAY){
                        assignBusyDay.add(new BusyDaySchedule(ea.email,i));
                    } else {
                        if (ea.getTuesday() == Available.OPENING){
                            assignDay.add(new DaySchedule(ea.email,"",i));
                        } else if (ea.getTuesday() == Available.CLOSING){
                            assignDay.add(new DaySchedule("",ea.email,i));
                        } else if (ea.getTuesday() == Available.CANNOT){
                            assignDay.add(new DaySchedule("","",i));
                        }
                    }

                }
                else if(nameDow.compareTo("Wednesday") == 3){
                    if(ea.getWednesday() == Available.OPENING && ea.getWednesday() == Available.CLOSING && ea.getWednesday() == Available.ALLDAY){
                        assignBusyDay.add(new BusyDaySchedule(ea.email,i));
                    } else {
                        if (ea.getWednesday() == Available.OPENING){
                            assignDay.add(new DaySchedule(ea.email,"",i));
                        } else if (ea.getWednesday() == Available.CLOSING){
                            assignDay.add(new DaySchedule("",ea.email,i));
                        } else if (ea.getWednesday() == Available.CANNOT){
                            assignDay.add(new DaySchedule("","",i));
                        }
                    }

                } else if(nameDow.compareTo("Thursday") == 4){
                    if(ea.getThursday() == Available.OPENING && ea.getThursday() == Available.CLOSING && ea.getThursday() == Available.ALLDAY){
                        assignBusyDay.add(new BusyDaySchedule(ea.email,i));
                    } else {
                        if (ea.getThursday() == Available.OPENING){
                            assignDay.add(new DaySchedule(ea.email,"",i));
                        } else if (ea.getThursday() == Available.CLOSING){
                            assignDay.add(new DaySchedule("",ea.email,i));
                        } else if (ea.getThursday() == Available.CANNOT){
                            assignDay.add(new DaySchedule("","",i));
                        }
                    }

                } else if(nameDow.compareTo("Friday") == 5){
                    if(ea.getFriday() == Available.OPENING && ea.getFriday() == Available.CLOSING && ea.getFriday() == Available.ALLDAY){
                        assignBusyDay.add(new BusyDaySchedule(ea.email,i));
                    } else {
                        if (ea.getFriday() == Available.OPENING){
                            assignDay.add(new DaySchedule(ea.email,"",i));
                        } else if (ea.getFriday() == Available.CLOSING){
                            assignDay.add(new DaySchedule("",ea.email,i));
                        } else if (ea.getFriday() == Available.CANNOT){
                            assignDay.add(new DaySchedule("","",i));
                        }
                    }

                } else if(nameDow.compareTo("Saturday") == 6){
                    if(ea.getSaturday() == Available.OPENING && ea.getSaturday() == Available.CLOSING && ea.getSaturday() == Available.ALLDAY){
                        assignBusyDay.add(new BusyDaySchedule(ea.email,i));
                    } else {
                        if (ea.getSaturday() == Available.OPENING){
                            assignDay.add(new DaySchedule(ea.email,"",i));
                        } else if (ea.getSaturday() == Available.CLOSING){
                            assignDay.add(new DaySchedule("",ea.email,i));
                        } else if (ea.getSaturday() == Available.CANNOT){
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
