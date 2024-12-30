package com.example.jokejet;

public class Joke {

    public static final String TABLE_NAME = "joke";
    public static final String FIELD_ID = "id";
    public static final String FIELD_CONTENT = "content";
    public static final String FIELD_CATEGORY = "category";
    public static final String FIELD_TYPE = "type";

    public static final String FIELD_DELIVERY="delivery";
    public static final String FIELD_STATUS = "status";

    private int id;
    private String content;
    private String category;
    private String type;
    private String delivery;
    private String status;

    public Joke(int id, String content, String category, String status) {
        this(id, content, category, "Joke", null, status);
    }

    public Joke(int id, String content, String category, String delivery, String status) {
        this(id, content, category, "SetupDelivery", delivery, status);
    }

    public Joke(int id, String content, String category, String type, String delivery, String status) {
        this.id = id;
        this.content = content;
        this.category = category;
        this.type = type;
        this.delivery = delivery;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", delivery='" + (delivery != null ? delivery : "N/A") + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

