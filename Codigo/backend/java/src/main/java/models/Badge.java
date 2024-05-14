package models;

import java.util.UUID;
import java.time.LocalDate;
import java.text.SimpleDateFormat;

public class Badge {

    private UUID id;
    private String name;
    private String description;
    private Boolean enable;
    private String linkImage;

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
