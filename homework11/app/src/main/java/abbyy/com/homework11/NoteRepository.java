package abbyy.com.homework11;

import android.content.Context;
import android.content.res.Resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteRepository {

    private DatabaseHolder databaseHolder;

    public void Initialize() {
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
            final int nSamples = 100;
            Random random = new Random();
            for (int i = 0; i < nSamples; ++i) {
                int imageId = catImageIds.get(random.nextInt(nImages));
                Date date = dates.get(random.nextInt(nImages));
                create(new Note(i, date, notesText, imageId));
            }
        } catch (ParseException e) {
            System.out.println("Parsing error in notes date text occured!");
            e.printStackTrace();
        }
    }

    public NoteRepository(@NonNull final DatabaseHolder databaseHolder) {
        this.databaseHolder = databaseHolder;
    }

    public void create(@NonNull final Note note) {
        try {
            SQLiteDatabase database = databaseHolder.open();
            ContentValues contentValues = new ContentValues();

            contentValues.put(NoteContract.Columns.DATE, note.getDate().toString());
            contentValues.put(NoteContract.Columns.TEXT, note.getText());
            contentValues.put(NoteContract.Columns.DRAW_ID, note.getDrawableId());
            database.insert(NoteContract.TABLE_NAME, null, contentValues);
        } finally {
            databaseHolder.close();
        }
    }

    private Note getNoteFromCursor(Cursor cursor) {
        try {
            final long id = cursor.getLong(cursor.getColumnIndex(NoteContract.Columns._ID));
            final String dateStr = cursor.getString(cursor.getColumnIndex(NoteContract.Columns.DATE));
            final Date date = new SimpleDateFormat("yyyy.mm.dd").parse(dateStr);
            final String text = cursor.getString(cursor.getColumnIndex(NoteContract.Columns.TEXT));
            final int drawId = cursor.getInt(cursor.getColumnIndex(NoteContract.Columns.DRAW_ID));
            return new Note(id, date, text, drawId);
        } catch (ParseException e) {
            System.out.println("Parsing error in notes date text occured!");
            e.printStackTrace();
            return new Note();
        }
    }

    public List<Note> getNotes() {
        List<Note> noteList = new ArrayList<>();
        Cursor cursor = null;
        try {
            SQLiteDatabase database = databaseHolder.open();
            cursor = database.query(NoteContract.TABLE_NAME,
                    new String[] { NoteContract.Columns._ID, NoteContract.Columns.DATE, NoteContract.Columns.TEXT, NoteContract.Columns.DRAW_ID},
                    null, null, null, null, null
            );
            while (cursor.moveToNext()) {
                noteList.add(getNoteFromCursor(cursor));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            databaseHolder.close();
        }

        return noteList;
    }

    public Note getNoteWithId(final long id) {
        Cursor cursor = null;
        try {
            SQLiteDatabase database = databaseHolder.open();

            cursor = database.query(
                    NoteContract.TABLE_NAME,
                    new String[] { NoteContract.Columns._ID, NoteContract.Columns.DATE, NoteContract.Columns.TEXT, NoteContract.Columns.DRAW_ID},
                    "ID == ?", new String[]{ String.valueOf(id)}, null, null, null
            );

            cursor.moveToNext();
            return getNoteFromCursor(cursor);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            databaseHolder.close();
        }
    }
}