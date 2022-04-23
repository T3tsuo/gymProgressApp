package com.example.trackgymprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.UUID;

public class AddExerciseActivity extends AppCompatActivity {

    private WorkoutDayClass workoutDay;
    private MuscleClass muscleType;
    private ExerciseTypeClass exerciseType;
    private FirebaseDatabase FBDatabase;
    DatabaseReference exerciseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        Intent intent = getIntent();
        workoutDay = (WorkoutDayClass) intent.getSerializableExtra(getString(R.string.WORKOUT_DAY_KEY));
        muscleType = (MuscleClass) intent.getSerializableExtra(getString(R.string.WORKOUT_MUSCLE_KEY));
        exerciseType = (ExerciseTypeClass) intent.getSerializableExtra(getString(R.string.EXERCISE_TYPE_KEY));

        initFB();

        EditText nameInput = findViewById(R.id.nameInput);
        EditText weightInput = findViewById(R.id.weightInput);
        EditText setsInput = findViewById(R.id.setsInput);
        EditText repsInput = findViewById(R.id.repsInput);
        Button addBtn =  findViewById(R.id.addBtn);

        addBtn.setOnClickListener(view -> {
            String name = nameInput.getText().toString();
            String weight = weightInput.getText().toString();
            String sets = setsInput.getText().toString();
            String reps = repsInput.getText().toString();
            if (!name.equals("") && !weight.equals("") && !sets.equals("") && !reps.equals("")) {
                addDataToFB(name, weight, sets, reps);
                finish();
            } else {
                Toast.makeText(this, "Missing Inputs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFB() {
        FBDatabase = FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL));
        exerciseDatabase = FBDatabase.getReference(workoutDay.getDay())
                .child(getString(R.string.WORKOUT_MUSCLE_KEY))
                .child(muscleType.getUuid()).child(getString(R.string.EXERCISE_TYPE_KEY))
                .child(exerciseType.getUuid()).child(getString(R.string.EXERCISE_KEY));
    }

    public void addDataToFB(String name, String weight, String sets, String reps) {
        UUID uuid = UUID.randomUUID();
        ExerciseClass exercise = new ExerciseClass(name, weight, sets, reps, String.valueOf(uuid));
        exerciseDatabase.child(String.valueOf(uuid)).setValue(exercise);
    }
}