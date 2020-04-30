package abbyy.com.homework11;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private static DatabaseHolder databaseHolder;
    private static NoteRepository noteRepository;

    @Override
    public void onCreate()  {
        super.onCreate();
        context = this;
        databaseHolder = new DatabaseHolder(context);
        noteRepository = new NoteRepository(databaseHolder);
    }

    public static Context getContext() {
        return context;
    }
    public static DatabaseHolder getDatabaseHolder() {
        return databaseHolder;
    }
    public static NoteRepository getNoteRepository() {
        return noteRepository;
    }
}
