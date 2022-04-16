package com.example.trackgymprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WorkoutMuscleActivity extends AppCompatActivity {

    private FirebaseDatabase FBDatabase;
    private DatabaseReference dayDatabase;
    private WorkoutDayClass workoutDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_muscle);
        Intent intent = getIntent();
        workoutDay = (WorkoutDayClass)
                intent.getSerializableExtra(getString(R.string.WORKOUT_DAY_KEY));
        initFB();
    }

    public void initFB() {
        FBDatabase = FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL));
        dayDatabase = FBDatabase.getReference(workoutDay.getDay());
    }
}