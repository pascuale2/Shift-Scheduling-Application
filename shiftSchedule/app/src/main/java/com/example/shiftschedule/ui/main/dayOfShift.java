package com.example.shiftschedule.ui.main;

public class dayOfShift {
    /*
    dayOfShift class created by Alex Creencia
    This class simply holds the shift_ids corresponding to a specific date.
     */
    protected String openingShiftId;
    protected String closingShiftId;

    public dayOfShift(String openShiftId, String closeShiftId) {
        this.openingShiftId = openShiftId;
        this.closingShiftId = closeShiftId;
    }

    public String getOpeningShiftId() {
        return openingShiftId;
    }

    public String getClosingShiftId() {
        return closingShiftId;
    }
}
