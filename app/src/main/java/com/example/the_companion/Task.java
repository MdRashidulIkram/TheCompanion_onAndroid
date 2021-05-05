package com.example.the_companion;

public class Task {
    private String taskDescription;
    private String taskImage;
    private String userId;

    public Task(String taskDescription, String taskImage, String userId) {
        this.taskDescription = taskDescription;
        this.taskImage = taskImage;
        this.userId = userId;
    }
    public Task(){

    }
    public Task(String taskDescription, String taskImage) {
        this.taskDescription = taskDescription;
        this.taskImage = taskImage;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskImage() {
        return taskImage;
    }

    public void setTaskImage(String taskImage) {
        this.taskImage = taskImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
