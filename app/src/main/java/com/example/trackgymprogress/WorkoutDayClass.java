package com.example.trackgymprogress;

import java.io.Serializable;

public class WorkoutDayClass implements Serializable {

    private String Day = "";

    public WorkoutDayClass() {}

    public void setPush() { this.Day = "Push"; }

    public void setPull() { this.Day = "Pull"; }

    public void setLegs() { this.Day = "Legs"; }

    public String getDay() {
        return this.Day;
    }
}
