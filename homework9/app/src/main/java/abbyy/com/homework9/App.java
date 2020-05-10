package abbyy.com.homework9;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NoteRepository.initialize(this);
    }
}
