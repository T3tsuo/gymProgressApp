package com.example.trackgymprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class EditExerciseActivity extends AppCompatActivity {
    private WorkoutDayClass workoutDay;
    private MuscleClass muscleType;
    private ExerciseTypeClass exerciseType;
    private ExerciseClass exercise;
    private FirebaseDatabase FBDatabase;
    private DatabaseReference exerciseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);
        Intent intent = getIntent();
        workoutDay = (WorkoutDayClass)
                intent.getSerializableExtra(getString(R.string.WORKOUT_DAY_KEY));
        muscleType = (MuscleClass)
                intent.getSerializableExtra(getString(R.string.WORKOUT_MUSCLE_KEY));
        exerciseType = (ExerciseTypeClass)
                intent.getSerializableExtra(getString(R.string.EXERCISE_TYPE_KEY));
        exercise = (ExerciseClass) intent.getSerializableExtra(getString(R.string.EXERCISE_KEY));
        initFB();

        EditText nameInput = findViewById(R.id.nameInput);
        nameInput.setText(exercise.getName());
        EditText weightInput = findViewById(R.id.weightInput);
        weightInput.setText(exercise.getWeight());
        EditText setsInput = findViewById(R.id.setsInput);
        setsInput.setText(exercise.getSets());
        EditText repsInput = findViewById(R.id.repsInput);
        repsInput.setText(exercise.getReps());
        Button deleteBtn = findViewById(R.id.deleteBtn);
        Button setBtn = findViewById(R.id.setBtn);

        setBtn.setOnClickListener(view -> {
            String name = nameInput.getText().toString();
            String weight = weightInput.getText().toString();
            String sets = setsInput.getText().toString();
            String reps = repsInput.getText().toString();
            if (!name.equals("") && !weight.equals("") && !sets.equals("") && !reps.equals("")) {
                updateDataToFB(name, weight, sets, reps);
                finish();
            } else {
                Toast.makeText(this, "Missing Inputs", Toast.LENGTH_SHORT).show();
            }
        });

        deleteBtn.setOnClickListener(view -> {
            removeFromDB();
            finish();
        });
    }

    public void initFB() {
        FBDatabase = FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL));
        exerciseDatabase = FBDatabase.getReference(workoutDay.getDay())
                .child(getString(R.string.WORKOUT_MUSCLE_KEY))
                .child(muscleType.getUuid()).child(getString(R.string.EXERCISE_TYPE_KEY))
                .child(exerciseType.getUuid()).child(getString(R.string.EXERCISE_KEY));
    }

    public void updateDataToFB(String name, String weight, String sets, String reps) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("weight", weight);
        map.put("sets", sets);
        map.put("reps", reps);
        exerciseDatabase.child(exercise.getUuid()).updateChildren(map);
    }

    public void removeFromDB() {
        exerciseDatabase.child(exercise.getUuid()).removeValue();
    }
}