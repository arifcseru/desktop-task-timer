// 
// Decompiled by Procyon v0.5.36
// 
package com.timer.tos;

public class TaskTimerTO {

    private Integer id;
    private String taskDetails;
    private Integer timeLimit;
    private Integer timeLeft;
    private Integer status;

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getTaskDetails() {
        return this.taskDetails;
    }

    public void setTaskDetails(final String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public Integer getTimeLimit() {
        return this.timeLimit;
    }

    public void setTimeLimit(final Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getTimeLeft() {
        return this.timeLeft;
    }

    public void setTimeLeft(final Integer timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }
}
