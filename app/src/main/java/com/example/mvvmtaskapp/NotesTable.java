package com.example.mvvmtaskapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_notes")
public class NotesTable {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    private String title;
    private String description;
    private int priority;

    public NotesTable(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
