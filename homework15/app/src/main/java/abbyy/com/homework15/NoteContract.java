package abbyy.com.homework15;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import androidx.annotation.NonNull;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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
                        + Columns.DATE + " LONG,"
                        + Columns.TEXT + " TEXT,"
                        + Columns.DRAW_ID + " TEXT"
                        + " );"
        );
        initializeTable(db);
    }

    private static void initializeTable(@NonNull final SQLiteDatabase db) {
        final Context context = App.getContext();
        final Resources resources = context.getResources();
        String notesText = resources.getString(resources.getIdentifier("mainText", "string", context.getPackageName()));
        try {
            ArrayList<Integer> catImageIds = new ArrayList<Integer>();
            ArrayList<Date> dates = new ArrayList<Date>();
            final int nImages = 8;
            for (long i = 1; i <= nImages; ++i) {
                int currCatImageId = resources.getIdentifier("cat" + Long.toString(i), "drawable", context.getPackageName());
                catImageIds.add(currCatImageId);
                int dateId = resources.getIdentifier("dateText" + Long.toString(i), "string", context.getPackageName());
                Date currDate = new SimpleDateFormat("yyyy.mm.dd").parse(resources.getString(dateId));
                dates.add(currDate);
            }
            final int nSamples = 5;
            Random random = new Random();
            for (int i = 0; i < nSamples; ++i) {
                int imageId = catImageIds.get(random.nextInt(nImages));
                Date date = dates.get(random.nextInt(nImages));
                insertNote(db, new Note(i, date, notesText, String.valueOf(imageId)));
            }
        } catch (ParseException e) {
            System.out.println("Parsing error in notes date text occured!");
            e.printStackTrace();
        }
    }

    public static void insertNote(@NonNull final SQLiteDatabase db, @NonNull final Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteContract.Columns.DATE, note.getDate().getTime());
        contentValues.put(NoteContract.Columns.TEXT, note.getText());
        contentValues.put(NoteContract.Columns.DRAW_ID, note.getPath());
        db.insert(NoteContract.TABLE_NAME, null, contentValues);
    }

    public static void deleteNote(@NonNull final SQLiteDatabase db, @NonNull final Note note) {
        db.delete(NoteContract.TABLE_NAME, "_ID == ?", new String[]{ String.valueOf(note.getId())});
        if (note.getDrawableId() == null) {
            new File(note.getPath()).delete();
        }
    }
}