package com.example.trackgymprogress;

import java.io.Serializable;

public class ExerciseTypeClass implements Serializable {
    private String exerciseType;
    private String uuid;

    public ExerciseTypeClass() {}

    public ExerciseTypeClass(String exerciseType, String uuid) {
        this.exerciseType = exerciseType;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }
}

