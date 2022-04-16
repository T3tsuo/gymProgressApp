package com.example.trackgymprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private WorkoutDayClass workoutDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workoutDay = new WorkoutDayClass();
        Button pushBtn = findViewById(R.id.pushBtn);
        Button pullBtn = findViewById(R.id.pullBtn);
        Button legsBtn = findViewById(R.id.legsBtn);

        pushBtn.setOnClickListener(view -> {
            workoutDay.setPush();
            switchActivity();
        });
        pullBtn.setOnClickListener(view -> {
            workoutDay.setPull();
            switchActivity();
        });
        legsBtn.setOnClickListener(view -> {
            workoutDay.setLegs();
            switchActivity();
        });
    }

    public void switchActivity() {
        Intent intent = new Intent(MainActivity.this, WorkoutMuscleActivity.class);
        intent.putExtra(getString(R.string.WORKOUT_DAY_KEY), workoutDay);
        startActivity(intent);
    }
}