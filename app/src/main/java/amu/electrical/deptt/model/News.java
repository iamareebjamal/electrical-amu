package amu.electrical.deptt.model;

public class News {
    private String title, body;
    private long time;

    public News() {
    }

    public News(String title, String body, long time) {
        this.title = title;
        this.body = body;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public long getTime() {
        return time;
    }
}
