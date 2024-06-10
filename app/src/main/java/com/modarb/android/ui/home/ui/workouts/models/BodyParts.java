package com.modarb.android.ui.home.ui.workouts.models;

public class BodyParts {
    private final String name;
    private final int imageResourceId;

    public BodyParts(String name, int imageResourceId) {
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
