package abbyy.com.homework16;

import java.io.File;
import java.util.*;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public class NoteRepository {

    private DatabaseHolder databaseHolder;

    public NoteRepository(@NonNull final DatabaseHolder databaseHolder) {
        this.databaseHolder = databaseHolder;
    }

    public long insertNote(Note note) {
        SQLiteDatabase database = databaseHolder.open();
        final long id = NoteContract.insertNote(database, note);
        databaseHolder.close();
        return id;
    }

    public void deleteNote(long id) {
        Note note = getNoteById(id);
        SQLiteDatabase database = databaseHolder.open();
        database.delete(NoteContract.TABLE_NAME, "_ID == ?", new String[]{ String.valueOf(id)});
        databaseHolder.close();
        if (note.getDrawableId() == null) {
            new File(note.getPath()).delete();
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

    public Note getNoteById(final long id) {
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

    private Note getNoteFromCursor(Cursor cursor) {
        final long id = cursor.getLong(cursor.getColumnIndex(NoteContract.Columns._ID));
        final Date date = new Date(cursor.getLong(cursor.getColumnIndex(NoteContract.Columns.DATE)));
        final String text = cursor.getString(cursor.getColumnIndex(NoteContract.Columns.TEXT));
        final String drawId = cursor.getString(cursor.getColumnIndex(NoteContract.Columns.DRAW_ID));
        return new Note(id, date, text, drawId);
    }
}