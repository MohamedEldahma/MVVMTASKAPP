package com.example.mvvmtaskapp;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NotesTableViewModel extends AndroidViewModel {
    private NotesTableRepository repository;
    private LiveData<List<NotesTable>> allNotesTable;

    public NotesTableViewModel(@NonNull Application application) {
        super(application);
        repository = new  NotesTableRepository(application);
        allNotesTable = repository.getAllNotesTable();
    }


    public void insert (NotesTable notes){
        repository.insert(notes);
    }
    public void update (NotesTable notes){
        repository.update(notes);
    }
    public void delete (NotesTable notes){
        repository.delete(notes);
    }
    public void deleteAllNotes (){
        repository.deleteAllNotesTable();
    }

    public LiveData<List<NotesTable>> getAllNotesTable() {
        return allNotesTable;
    }
}
