package abbyy.com.homework14;

import java.util.Date;

public class Note {
    private long id;
    private Date date;
    private String text;
    private String drawableId;

    public Note(final long id, final Date date, final String text, final String drawableId) {
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
