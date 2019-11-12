package com.example.mvvmtaskapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {NotesTable.class},version = 1)
public abstract class NotesTableDataBase extends RoomDatabase {

    private static  NotesTableDataBase instance;
    public abstract NotesDao notesDao();

    public static synchronized NotesTableDataBase  getInstance(Context context){
      if (instance == null){
       instance = Room.databaseBuilder(context.getApplicationContext(),
                  NotesTableDataBase.class,"notes_database")
                  .fallbackToDestructiveMigration()
                  .addCallback(roomCallback)
                  .build();
      }
      return instance;

    }

    private static  RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class  PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private NotesDao notesDao;
        private PopulateDbAsyncTask(NotesTableDataBase Db){
           this.notesDao = Db.notesDao();

        }
        @Override
        protected Void doInBackground(Void... voids) {
            notesDao.insert(new NotesTable("Title 1","Description 1",1));
            notesDao.insert(new NotesTable("Title 2","Description 2",2));
            notesDao.insert(new NotesTable("Title 3","Description 3",3));


            return null;
        }
    }
}
