package abbyy.com.homework16;

import java.util.Date;

public class Note {
    private long id;
    private Date date;
    private String text;
    private String drawableId;

    private static long undefined = -1;

    public Note(final long id, final Date date, final String text, final String drawableId) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.drawableId = drawableId;
    }

    public Note(final Date date, final String text, final String drawableId) {
        this.id = undefined;
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

    public Integer getDrawableId() {
        try {
            return Integer.parseInt(drawableId);
        } catch(NumberFormatException e) {
            return null;
        }
    }

    public String getPath() {
        return drawableId;
    }
}
