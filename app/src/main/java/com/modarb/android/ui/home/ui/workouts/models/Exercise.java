package com.modarb.android.ui.home.ui.workouts.models;

public class Exercise {
    private final String name;
    private final int imageResourceId;

    public Exercise(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
