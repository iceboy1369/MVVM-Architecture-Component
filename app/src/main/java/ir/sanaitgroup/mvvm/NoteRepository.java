package ir.sanaitgroup.mvvm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;

    public NoteRepository(Application application){
        NoteDataBase noteDataBase = NoteDataBase.getInstance(application);
        noteDao = noteDataBase.noteDao();
    }

    public void insert(Note note){
        noteDao.insert(note);
    }

    public void update(Note note){
        noteDao.update(note);
    }

    public void delete(Note note){
        noteDao.delete(note);
    }

    public void deleteAllNotes(){
        noteDao.deleteAll();
    }

    public Note getNoteById(int id){
        return noteDao.getNoteById(id);
    }

    public LiveData<List<Note>> getAllNotes(){
        return noteDao.getAllNote();
    }


}
