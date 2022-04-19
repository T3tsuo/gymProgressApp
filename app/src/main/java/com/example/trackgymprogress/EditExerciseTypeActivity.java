package com.example.trackgymprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditExerciseTypeActivity extends AppCompatActivity {
    private FirebaseDatabase FBDatabase;
    private ExerciseTypeClass exerciseType;
    private WorkoutDayClass workoutDay;
    private MuscleClass muscleType;
    private DatabaseReference exerciseTypeDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise_type);
        Intent intent = getIntent();
        workoutDay = (WorkoutDayClass) intent.getSerializableExtra(getString(R.string.WORKOUT_DAY_KEY));
        muscleType = (MuscleClass)
                intent.getSerializableExtra(getString(R.string.WORKOUT_MUSCLE_KEY));
        exerciseType = (ExerciseTypeClass)
                intent.getSerializableExtra(getString(R.string.EXERCISE_TYPE_KEY));
        initFB();
        EditText exerciseTypeInput = findViewById(R.id.exerciseTypeInput);
        exerciseTypeInput.setText(exerciseType.getExerciseType());
        Button deleteBtn = findViewById(R.id.deleteBtn);
        Button setBtn =  findViewById(R.id.setBtn);

        setBtn.setOnClickListener(view -> {
            String input = exerciseTypeInput.getText().toString();
            if (!input.equals("")) {
                updateDataToFB(input);
                finish();
            } else {
                Toast.makeText(this, "Nothing Inputted", Toast.LENGTH_SHORT).show();
            }
        });
        deleteBtn.setOnClickListener(view -> {
            removeFromDB();
            finish();
        });
    }

    public void initFB() {
        FBDatabase = FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL));
        exerciseTypeDatabase = FBDatabase.getReference(workoutDay.getDay())
                .child(getString(R.string.WORKOUT_MUSCLE_KEY))
                .child(muscleType.getUuid()).child(getString(R.string.EXERCISE_TYPE_KEY));
    }

    public void updateDataToFB(String data) {
        ExerciseTypeClass exercise = new ExerciseTypeClass(data, exerciseType.getUuid());
        exerciseTypeDatabase.child(exerciseType.getUuid()).setValue(exercise);
    }

    public void removeFromDB() {
        exerciseTypeDatabase.child(exerciseType.getUuid()).removeValue();
    }
}