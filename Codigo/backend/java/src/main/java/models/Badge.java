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

    // Getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getEnable() {
        return enable;
    }

    public String getLinkImage() {
        return linkImage;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}