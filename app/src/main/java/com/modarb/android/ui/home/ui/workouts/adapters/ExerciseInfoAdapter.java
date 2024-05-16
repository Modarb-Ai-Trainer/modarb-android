package com.modarb.android.ui.home.ui.workouts.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.modarb.android.databinding.ItemExerciseTypeBinding;
import com.modarb.android.ui.home.ui.workouts.models.Exercise;

import java.util.List;

public class ExerciseInfoAdapter extends RecyclerView.Adapter<ExerciseInfoAdapter.ExerciseViewHolder> {

    private final List<Exercise> exercises;

    public ExerciseInfoAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemExerciseTypeBinding binding = ItemExerciseTypeBinding.inflate(inflater, parent, false);
        return new ExerciseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.bind(exercise);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private final ItemExerciseTypeBinding binding;

        public ExerciseViewHolder(ItemExerciseTypeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Exercise exercise) {
            binding.exerciseName.setText(exercise.getName());
            binding.exerciseImage.setImageResource(exercise.getImageResourceId());
        }
    }
}
