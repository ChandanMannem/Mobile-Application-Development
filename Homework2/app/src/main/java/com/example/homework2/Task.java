package com.example.homework2;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private String name;
    private Date date;
    private String priority;

    public Task(String name, Date date, String priority) {
        this.name = name;
        this.date = date;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getPriority() {
        return priority;
    }
}
