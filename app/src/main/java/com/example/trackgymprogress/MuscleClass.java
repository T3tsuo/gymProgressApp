package com.example.trackgymprogress;

import java.io.Serializable;

public class MuscleClass implements Serializable {
    private String muscleType;
    private String uuid;

    public MuscleClass() {}

    public MuscleClass(String muscleType, String uuid) {
        this.muscleType = muscleType;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMuscleType() {
        return muscleType;
    }

    public void setMuscleType(String muscleType) {
        this.muscleType = muscleType;
    }
}
