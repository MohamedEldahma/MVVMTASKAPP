package com.example.mvvmtaskapp;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NotesTableRepository {

    private NotesDao notesDao;
    LiveData<List<NotesTable>> allNotesTable;

    public  NotesTableRepository(Application application){
        NotesTableDataBase dataBase = NotesTableDataBase.getInstance(application);
        notesDao = dataBase.notesDao();
        allNotesTable = notesDao.getAllNotesTable();
    }

    public void insert (NotesTable notesTable){
        new InsertNoteAsyncTask(notesDao).execute(notesTable);

    }
    public void update (NotesTable notesTable){
        new UpdateNoteAsyncTask(notesDao).execute(notesTable);

    }
    public void delete (NotesTable notesTable){
        new DeleteNoteAsyncTask(notesDao).execute(notesTable);

    }
    public void deleteAllNotesTable(){
        new DeleteAllNoteAsyncTask(notesDao).execute();

    }

    public LiveData<List<NotesTable>>getAllNotesTable(){
        return allNotesTable;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<NotesTable,Void ,Void >{
        private NotesDao notesDao ;

        private InsertNoteAsyncTask(NotesDao notesDao){
           this.notesDao = notesDao;
        }
        @Override
        protected Void doInBackground(NotesTable... notesTables) {
            notesDao.insert(notesTables[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<NotesTable,Void ,Void >{
        private NotesDao notesDao ;

        private UpdateNoteAsyncTask(NotesDao notesDao){
            this.notesDao = notesDao;
        }
        @Override
        protected Void doInBackground(NotesTable... notesTables) {
            notesDao.update(notesTables[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<NotesTable,Void ,Void >{
        private NotesDao notesDao ;

        private DeleteNoteAsyncTask(NotesDao notesDao){
            this.notesDao = notesDao;
        }
        @Override
        protected Void doInBackground(NotesTable... notesTables) {
            notesDao.delete(notesTables[0]);
            return null;
        }
    }
    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void,Void ,Void >{
        private NotesDao notesDao ;

        private DeleteAllNoteAsyncTask(NotesDao notesDao){
            this.notesDao = notesDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            notesDao.deleteAllNotesTable();
            return null;
        }
    }












}
