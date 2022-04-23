package com.example.trackgymprogress;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private WorkoutDayClass workoutDay;
    private MuscleClass muscleType;
    private ExerciseTypeClass exerciseType;
    private FirebaseDatabase FBDatabase;
    Query exerciseDatabase;
    private ExerciseAdapter adapter;
    private ArrayList<ExerciseClass> exerciseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        getExercise();
                    }
                });
        setContentView(R.layout.activity_exercise);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        recyclerView = findViewById(R.id.exercisesRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        workoutDay = (WorkoutDayClass) intent.getSerializableExtra(getString(R.string.WORKOUT_DAY_KEY));
        muscleType = (MuscleClass) intent.getSerializableExtra(getString(R.string.WORKOUT_MUSCLE_KEY));
        exerciseType = (ExerciseTypeClass) intent.getSerializableExtra(getString(R.string.EXERCISE_TYPE_KEY));
        TextView title = findViewById(R.id.textView);
        String titleText = muscleType.getMuscleType() + ": " + exerciseType.getExerciseType();
        title.setText(titleText);

        initFB();
        getExercise();

        fabAdd.setOnClickListener(view -> {
            Intent i = new Intent(ExerciseActivity.this, AddExerciseActivity.class);
            i.putExtra(getString(R.string.WORKOUT_DAY_KEY), workoutDay);
            i.putExtra(getString(R.string.WORKOUT_MUSCLE_KEY), muscleType);
            i.putExtra(getString(R.string.EXERCISE_TYPE_KEY), exerciseType);
            activityResultLauncher.launch(i);
        });
    }

    public void initFB() {
        FBDatabase = FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL));
        exerciseDatabase = FBDatabase.getReference(workoutDay.getDay())
                .child(getString(R.string.WORKOUT_MUSCLE_KEY))
                .child(muscleType.getUuid()).child(getString(R.string.EXERCISE_TYPE_KEY))
                .child(exerciseType.getUuid()).child(getString(R.string.EXERCISE_KEY))
                .orderByValue();
    }

    public void getExercise() {
        exerciseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exerciseList.clear();
                for (DataSnapshot exercise: snapshot.getChildren()) {
                    exerciseList.add(exercise.getValue(ExerciseClass.class));
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void setAdapter() {
        adapter = new ExerciseAdapter(exerciseList, workoutDay, muscleType, exerciseType,
                activityResultLauncher);
        recyclerView.setAdapter(adapter);
    }
}