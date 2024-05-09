package models;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Badges {

    public static int ultimo_id = 0;

    private int id;
    private String name;
    private String description;
    private Boolean enable;
    private String linkImage;
    private String howToUnlock;

    public Badges() {

        this.id = -1;
        this.name = "";
        this.description = "";
        this.enable = false;
        this.linkImage = "";
        this.howToUnlock = "";
    }

    public Badges(int id, String name, String description, Boolean enable, String linkImage, String howToUnlock) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.enable = enable;
        this.linkImage = linkImage;
        this.howToUnlock = howToUnlock;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getHowToUnlock() {
        return howToUnlock;
    }

    public void setHowToUnlock(String howToUnlock) {
        this.howToUnlock = howToUnlock;
    }
}
