package ru.antonsibgatulin.stockmarketoftaskclient.include.tasks;

public class ActionTask {
    private TaskType taskType;


    private Task task;

    private  boolean private_task=false;


    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean isPrivate_task() {
        return private_task;
    }

    public void setPrivate_task(boolean private_task) {
        this.private_task = private_task;
    }


}