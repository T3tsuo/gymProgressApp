package com.example.trackgymprogress;

import java.io.Serializable;

public class ExerciseClass implements Serializable {

    private String name;
    private String weight;
    private String sets;
    private String reps;
    private String uuid;


    public ExerciseClass() {}

    public ExerciseClass(String name, String weight, String sets, String reps, String uuid) {
        this.name = name;
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public String getSets() {
        return sets;
    }

    public String getReps() {
        return reps;
    }

    public String getUuid() {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
