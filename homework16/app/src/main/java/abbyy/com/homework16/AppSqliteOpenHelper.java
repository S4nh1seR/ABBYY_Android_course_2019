package abbyy.com.homework16;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppSqliteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NoteDatabase.db";
    private static final int VERSION = 1;

    public AppSqliteOpenHelper(@Nullable final Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(@NonNull final SQLiteDatabase db) {
        NoteContract.createTable(db);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
    }

}