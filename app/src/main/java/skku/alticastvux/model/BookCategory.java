package skku.alticastvux.model;

/**
 * Created by woorim on 2018. 7. 31..
 */

public class BookCategory {
    private int id;
    private String name;

    public BookCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
