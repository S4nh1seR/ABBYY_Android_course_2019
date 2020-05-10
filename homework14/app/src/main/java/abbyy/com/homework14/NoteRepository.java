package abbyy.com.homework14;

import java.util.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository {

    private DatabaseHolder databaseHolder;
    private int notesNumber;

    public NoteRepository(@NonNull final DatabaseHolder databaseHolder) {
        this.databaseHolder = databaseHolder;
    }

    private Note getNoteFromCursor(Cursor cursor) {
            final long id = cursor.getLong(cursor.getColumnIndex(NoteContract.Columns._ID));
            final Date date = new Date(cursor.getLong(cursor.getColumnIndex(NoteContract.Columns.DATE)));
            final String text = cursor.getString(cursor.getColumnIndex(NoteContract.Columns.TEXT));
            final String drawId = cursor.getString(cursor.getColumnIndex(NoteContract.Columns.DRAW_ID));
            return new Note(id, date, text, drawId);
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
        notesNumber = noteList.size();
        return noteList;
    }

    public Note getNoteWithId(final long id) {
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

    public void insertNote(@NonNull final Note note) {
        SQLiteDatabase database = databaseHolder.open();
        NoteContract.insertNote(database, note);
        databaseHolder.close();
        notesNumber += 1;
    }

    public int getNotesNumber() {
        return notesNumber;
    }

}