package abbyy.com.homework8;

import android.content.Context;
import android.content.res.Resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NoteRepository {

    private static final Map<Long, Note> notesList = new HashMap<>();

    public static void initialize(final Context context) {
        final Resources resources = context.getResources();
        String notesText = resources.getString(resources.getIdentifier("mainText", "string", context.getPackageName()));
        try {
            for (long i = 1; i <= 8; ++i) {
                int catImageId = resources.getIdentifier("cat" + Long.toString(i), "drawable", context.getPackageName());
                int dateId = resources.getIdentifier("dateText" + Long.toString(i), "string", context.getPackageName());
                Date date = new SimpleDateFormat("yyyy.mm.dd").parse(resources.getString(dateId));

                notesList.put(i, new Note(i, date, notesText, catImageId));
            }
        } catch (ParseException e) {
            System.out.println("Parsing error in notes date text occured!");
            e.printStackTrace();
        }
    }

    public static List<Note> getNotes() {
        return new ArrayList<>(notesList.values());
    }

    public static Note getNoteWithId(final long id) {
        return notesList.get(id);
    }
}

