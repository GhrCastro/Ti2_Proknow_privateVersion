package models;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class Badge {

    private UUID id;
    private String name;
    private String description;
    private Boolean enable;
    private String linkImage;
    // private String howToUnlock;

    public Badge(UUID id, String name, String description, Boolean enable, String linkImage) {

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
