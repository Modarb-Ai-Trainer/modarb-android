package com.modarb.android.ui.home.ui.workouts.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.modarb.android.databinding.ItemExerciseTypeBinding;
import com.modarb.android.ui.home.ui.workouts.OnBodyPartClickListener;
import com.modarb.android.ui.home.ui.workouts.models.BodyParts;

import java.util.List;

public class BodyPartsAdapter extends RecyclerView.Adapter<BodyPartsAdapter.ExerciseViewHolder> {

    private final List<BodyParts> bodyParts;
    private final OnBodyPartClickListener listener;

    public BodyPartsAdapter(List<BodyParts> bodyParts, OnBodyPartClickListener listener) {
        this.bodyParts = bodyParts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemExerciseTypeBinding binding = ItemExerciseTypeBinding.inflate(inflater, parent, false);
        return new ExerciseViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        BodyParts exercise = bodyParts.get(position);
        holder.bind(exercise);
    }

    @Override
    public int getItemCount() {
        return bodyParts.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private final ItemExerciseTypeBinding binding;

        public ExerciseViewHolder(ItemExerciseTypeBinding binding, OnBodyPartClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(v -> listener.onBodyPartClick((BodyParts) v.getTag()));
        }

        public void bind(BodyParts exercise) {
            binding.exerciseName.setText(exercise.getName());
            binding.exerciseImage.setImageResource(exercise.getImageResourceId());
            itemView.setTag(exercise);
        }
    }
}
