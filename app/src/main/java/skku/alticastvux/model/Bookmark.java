package skku.alticastvux.model;

/**
 * Created by woorim on 2018. 7. 25..
 */

public class Bookmark {
    private long id;
    private long time; // milliseconds
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
