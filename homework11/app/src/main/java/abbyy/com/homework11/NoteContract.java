package abbyy.com.homework11;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import androidx.annotation.NonNull;

public class NoteContract {

    public static final String TABLE_NAME = "note_table";

    public interface Columns extends BaseColumns {
        String DATE = "DATE";
        String TEXT = "TEXT";
        String DRAW_ID = "DRAW_ID";
    }

    public static void createTable(@NonNull final SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME
                        + " ( "
                        + Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Columns.DATE + " TEXT NOT NULL,"
                        + Columns.TEXT + " TEXT,"
                        + Columns.DRAW_ID + " INTEGER"
                        + " );"
        );
    }
}