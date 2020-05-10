package abbyy.com.homework12;

import java.util.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NoteRepository {

    private DatabaseHolder databaseHolder;

    public NoteRepository(@NonNull final DatabaseHolder databaseHolder) {
        this.databaseHolder = databaseHolder;
    }

    private Note getNoteFromCursor(Cursor cursor) {
            final long id = cursor.getLong(cursor.getColumnIndex(NoteContract.Columns._ID));
            final Date date = new Date(cursor.getLong(cursor.getColumnIndex(NoteContract.Columns.DATE)));
            final String text = cursor.getString(cursor.getColumnIndex(NoteContract.Columns.TEXT));
            final int drawId = cursor.getInt(cursor.getColumnIndex(NoteContract.Columns.DRAW_ID));
            return new Note(id, date, text, drawId);
    }

    public List<Note> getNotes() {
        // Нужно для проверки, что асинхронность работает
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            throw new RuntimeException("sleep error!");
        }

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
        // Нужно для проверки, что асинхронность работает
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            throw new RuntimeException("sleep error!");
        }

        Cursor cursor = null;
        try {
            SQLiteDatabase database = databaseHolder.open();

            cursor = database.query(
                    NoteContract.TABLE_NAME,
                    new String[] { NoteContract.Columns._ID, NoteContract.Columns.DATE, NoteContract.Columns.TEXT, NoteContract.Columns.DRAW_ID},
                    "_ID == ?", new String[]{ String.valueOf(id)}, null, null, null
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