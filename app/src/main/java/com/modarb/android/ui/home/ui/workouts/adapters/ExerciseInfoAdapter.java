package com.modarb.android.ui.home.ui.workouts.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.modarb.android.R;
import com.modarb.android.ui.home.ui.workouts.models.Exercise;

import java.util.List;

public class ExerciseInfoAdapter extends RecyclerView.Adapter<ExerciseInfoAdapter.ExerciseViewHolder> {

    private final List<Exercise> exercises;

    public ExerciseInfoAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_type, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.exerciseName.setText(exercise.getName());
        holder.exerciseImage.setImageResource(exercise.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public TextView exerciseName;
        public ImageView exerciseImage;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            exerciseImage = itemView.findViewById(R.id.exerciseImage);
            exerciseName = itemView.findViewById(R.id.exerciseName);
        }
    }
}
