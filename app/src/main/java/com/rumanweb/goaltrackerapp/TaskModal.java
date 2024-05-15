package com.rumanweb.goaltrackerapp;

public class TaskModal {
    private String taskTitle;
    private String dueDate;
    private String stepsCount;
    private String notes;
    private String creationDate;
    private String stepsText;
    private int progress;
    private int id;

    public TaskModal(String taskTitle, String dueDate, String stepsCount,String stepsText, String notes, String creationDate, int progress) {
        this.taskTitle = taskTitle;
        this.dueDate = dueDate;
        this.stepsCount = stepsCount;
        this.stepsText = stepsText;
        this.notes = notes;
        this.creationDate = creationDate;
        this.progress = progress;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    public String getStepsText(){
        return stepsText;
    }
    public void setStepsText(String stepsText){
        this.stepsText = stepsText;
    }
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getStepsCount() {
        return stepsCount;
    }
    public void setStepsCount(String stepsCount) {
        this.stepsCount = stepsCount;
    }
    public String getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
    public int getProgress() {
        return progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
