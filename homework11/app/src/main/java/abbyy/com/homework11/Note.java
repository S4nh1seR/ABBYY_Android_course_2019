package abbyy.com.homework11;

import java.util.Date;

public class Note {
    private long id;
    private Date date;
    private String text;
    private int drawableId;

    public Note() {
    }

    public Note(final long id, final Date date, final String text, final int drawableId) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.drawableId = drawableId;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public int getDrawableId() {
        return drawableId;
    }
}
