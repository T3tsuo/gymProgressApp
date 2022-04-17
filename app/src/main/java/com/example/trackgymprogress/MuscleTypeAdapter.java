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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MuscleTypeAdapter extends RecyclerView.Adapter<MuscleTypeAdapter.ViewHolder> {

    private ArrayList<MuscleClass> localDataSet;
    private WorkoutDayClass workoutDay;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    /**
     * Initialize the dataset of the Adapter.
     *
     */
    public MuscleTypeAdapter(ArrayList<MuscleClass> dataSet, WorkoutDayClass workoutDay,
                             ActivityResultLauncher<Intent> activityResultLauncher) {
        this.localDataSet = dataSet;
        this.workoutDay = workoutDay;
        this.activityResultLauncher= activityResultLauncher;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_adapter_muscle, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.muscleTypeLabel.setText(localDataSet.get(position).getMuscleType());
        holder.editBtn.setOnClickListener(view -> {
            Intent i = new Intent(holder.context, EditMuscleActivity.class);
            i.putExtra(holder.WORKOUT_DAY_KEY, workoutDay);
            i.putExtra(holder.WORKOUT_MUSCLE_KEY, localDataSet.get(position));
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
        private final TextView muscleTypeLabel;
        private Button continueBtn;
        private Button editBtn;
        private String WORKOUT_DAY_KEY;
        private String WORKOUT_MUSCLE_KEY;
        private Context context;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            muscleTypeLabel = view.findViewById(R.id.muscleType);
            editBtn = view.findViewById(R.id.editBtn);
            continueBtn = view.findViewById(R.id.continueBtn);
            WORKOUT_DAY_KEY = view.getContext().getString(R.string.WORKOUT_DAY_KEY);
            WORKOUT_MUSCLE_KEY = view.getContext().getString(R.string.WORKOUT_MUSCLE_KEY);
            context = view.getContext();
        }
    }
}
