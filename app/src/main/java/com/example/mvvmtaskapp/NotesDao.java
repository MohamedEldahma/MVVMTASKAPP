package com.example.mvvmtaskapp;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static android.icu.text.MessagePattern.ArgType.SELECT;

@Dao
public interface NotesDao {

    @Insert
    void insert(NotesTable notesTable);
    @Update
    void update(NotesTable notesTable);
    @Delete
    void delete (NotesTable notesTable);

    @Query("DELETE FROM  table_notes ")
    void  deleteAllNotesTable();

    @Query("SELECT * FROM  table_notes ORDER BY priority DESC")
     LiveData<List<NotesTable>> getAllNotesTable();



}
