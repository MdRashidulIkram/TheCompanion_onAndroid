package com.example.the_companion;

public class Task {
    private String taskDescription;
    private String taskImage;
    private String userId;
    private String taskId;

    public Task(String taskDescription, String taskImage, String taskId) {
        this.taskDescription = taskDescription;
        this.taskImage = taskImage;
        this.taskId = taskId;
    }
    public Task(Integer integer, String description){}
    public Task(String taskDescription){
        this.taskDescription = taskDescription;
    }
    public Task(String taskId, String taskDescription) {
        this.taskDescription = taskDescription;
        this.taskId = taskId;
    }
    public Task(){};
    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
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
