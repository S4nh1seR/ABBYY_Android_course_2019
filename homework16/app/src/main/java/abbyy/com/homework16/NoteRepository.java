package abbyy.com.homework16;

import java.io.File;
import java.util.*;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository {

    private DatabaseHolder databaseHolder;
    private List<Note> notesList;
    private Note lastRequiredNote;
    private long lastRequiredNoteId;

    public NoteRepository(@NonNull final DatabaseHolder databaseHolder) {
        this.databaseHolder = databaseHolder;
    }

    public interface OnGetNotesCallback {
        void onGetNotes(final List<Note> list);
    }

    @SuppressLint( "StaticFieldLeak" )
    public List<Note> getNotes(AsyncTask asyncTask, final OnGetNotesCallback callback) {
        asyncTask = new AsyncTask<Void, Void, List<Note>>() {
            @WorkerThread
            @Override
            protected List<Note> doInBackground(final Void... voids) {
                return getNotesFromDB();
            }

            @MainThread
            @Override
            protected void onPostExecute(final List<Note> list) {
                super.onPostExecute(list);
                notesList = list;
                if (callback != null) {
                    callback.onGetNotes(list);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return notesList;
    }

    public interface OnGetNoteByIdCallback {
        void onGetNote(final Note note);
    }

    @SuppressLint( "StaticFieldLeak" )
    public Note getNoteById(AsyncTask asyncTask, long id, final OnGetNoteByIdCallback callback) {
        asyncTask = new AsyncTask<Long, Void, Note>() {
            @WorkerThread
            @Override
            protected Note doInBackground(final Long... id) {
                return getNoteFromDBWithId(id[0]);
            }

            @MainThread
            @Override
            protected void onPostExecute(final Note note) {
                super.onPostExecute(note);
                lastRequiredNote = note;
                if (callback != null) {
                    callback.onGetNote(note);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id);
        return lastRequiredNote;
    }

    @SuppressLint( "StaticFieldLeak" )
    public long insertNote(AsyncTask asyncTask, Note note) {
        asyncTask = new AsyncTask<Note, Void, Long>() {
            @WorkerThread
            @Override
            protected Long doInBackground(final Note... notes) {
                SQLiteDatabase database = databaseHolder.open();
                final long id = NoteContract.insertNote(database, notes[0]);
                databaseHolder.close();
                return id;
            }

            @MainThread
            @Override
            protected void onPostExecute(final Long id) {
                super.onPostExecute(id);
                lastRequiredNoteId = id;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, note);
        return lastRequiredNoteId;
    }

    public interface onDeleteNoteCallback {
        void onDeleteNote();
    }

    @SuppressLint( "StaticFieldLeak" )
    public void deleteNote(AsyncTask asyncTask, long id, onDeleteNoteCallback callback) {
        asyncTask = new AsyncTask<Long, Void, Void>() {
            @WorkerThread
            @Override
            protected Void doInBackground(final Long... ids) {
                Note note = getNoteFromDBWithId(ids[0]);
                SQLiteDatabase database = databaseHolder.open();
                database.delete(NoteContract.TABLE_NAME, "_ID == ?", new String[]{ String.valueOf(ids[0])});
                databaseHolder.close();
                if (note.getDrawableId() == null) {
                    new File(note.getPath()).delete();
                }
                return null;
            }
            @MainThread
            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                if (callback != null) {
                    callback.onDeleteNote();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id);
    }

    private List<Note> getNotesFromDB() {
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

    private Note getNoteFromCursor(Cursor cursor) {
        final long id = cursor.getLong(cursor.getColumnIndex(NoteContract.Columns._ID));
        final Date date = new Date(cursor.getLong(cursor.getColumnIndex(NoteContract.Columns.DATE)));
        final String text = cursor.getString(cursor.getColumnIndex(NoteContract.Columns.TEXT));
        final String drawId = cursor.getString(cursor.getColumnIndex(NoteContract.Columns.DRAW_ID));
        return new Note(id, date, text, drawId);
    }

    private Note getNoteFromDBWithId(final long id) {
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