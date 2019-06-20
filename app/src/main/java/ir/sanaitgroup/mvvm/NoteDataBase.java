package ir.sanaitgroup.mvvm;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Note.class},version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    private static NoteDataBase instance;
    public abstract NoteDao noteDao();

    public static synchronized NoteDataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                    ,NoteDataBase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build(); //.addCallback(roomCallback)
        }
        return instance;
    }

//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDBAsyncTask(instance).execute();
//        }
//    };
//
//    private static class PopulateDBAsyncTask extends AsyncTask<Void,Void,Void> {
//        private NoteDao noteDao;
//
//        private PopulateDBAsyncTask(NoteDataBase db){
//            noteDao = db.noteDao();
//        }
//        @Override
//        protected Void doInBackground(Void... Voids) {
//            noteDao.insert(new Note("مرتضی","موحدیان",1));
//            noteDao.insert(new Note("مجید","محمودی",2));
//            noteDao.insert(new Note("حسین","غفاریان",3));
//            return null;
//        }
//    }
}
