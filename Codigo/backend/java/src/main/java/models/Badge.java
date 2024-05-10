package models;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Badge {

    public static int ultimo_id = 0;

    private int id;
    private String name;
    private String description;
    private Boolean enable;
    private String linkImage;
    // private String howToUnlock;

    public Badge(int id, String name, String description, Boolean enable, String linkImage) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.enable = enable;
        this.linkImage = linkImage;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
