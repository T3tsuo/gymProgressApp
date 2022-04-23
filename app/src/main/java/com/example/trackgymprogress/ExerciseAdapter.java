package com.example.trackgymprogress;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private ArrayList<ExerciseClass> localDataSet;
    private WorkoutDayClass workoutDay;
    private MuscleClass muscleType;
    private ExerciseTypeClass exerciseType;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    /**
     * Initialize the dataset of the Adapter.
     *
     */
    public ExerciseAdapter(ArrayList<ExerciseClass> dataSet, WorkoutDayClass workoutDay,
                               MuscleClass muscleType, ExerciseTypeClass exerciseType,
                           ActivityResultLauncher<Intent> activityResultLauncher) {
        this.localDataSet = dataSet;
        this.workoutDay = workoutDay;
        this.muscleType = muscleType;
        this.exerciseType = exerciseType;
        this.activityResultLauncher= activityResultLauncher;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_adapter_exercise, viewGroup, false);
        return new ExerciseAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String name = localDataSet.get(position).getName();
        holder.nameLabel.setText(name);
        String weight = "Weight: " + localDataSet.get(position).getWeight();
        holder.weightLabel.setText(weight);
        String setsReps = "SetsxReps: " + localDataSet.get(position).getSets() + "x"
                + localDataSet.get(position).getReps();
        holder.setsRepsLabel.setText(setsReps);

        holder.editBtn.setOnClickListener(view -> {
            Intent i = new Intent(holder.context, EditExerciseActivity.class);
            i.putExtra(holder.WORKOUT_DAY_KEY, workoutDay);
            i.putExtra(holder.WORKOUT_MUSCLE_KEY, muscleType);
            i.putExtra(holder.EXERCISE_TYPE_KEY, exerciseType);
            i.putExtra(holder.EXERCISE_KEY, localDataSet.get(position));
            activityResultLauncher.launch(i);
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameLabel;
        private final TextView weightLabel;
        private final TextView setsRepsLabel;
        private Button editBtn;
        private String WORKOUT_DAY_KEY;
        private String WORKOUT_MUSCLE_KEY;
        private String EXERCISE_TYPE_KEY;
        private String EXERCISE_KEY;
        private Context context;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            nameLabel = view.findViewById(R.id.exerciseName);
            weightLabel = view.findViewById(R.id.exerciseWeight);
            setsRepsLabel = view.findViewById(R.id.exerciseSetsReps);
            editBtn = view.findViewById(R.id.editBtn);
            WORKOUT_DAY_KEY = view.getContext().getString(R.string.WORKOUT_DAY_KEY);
            WORKOUT_MUSCLE_KEY = view.getContext().getString(R.string.WORKOUT_MUSCLE_KEY);
            EXERCISE_TYPE_KEY = view.getContext().getString(R.string.EXERCISE_TYPE_KEY);
            EXERCISE_KEY = view.getContext().getString(R.string.EXERCISE_KEY);
            context = view.getContext();
        }
    }
}

