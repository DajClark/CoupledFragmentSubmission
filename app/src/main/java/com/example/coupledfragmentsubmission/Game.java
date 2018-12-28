package com.example.coupledfragmentsubmission;

import java.util.Date;
import java.util.UUID;

public class Game {

    private UUID gameID;
    private String title;
    private String platform;
    private String description;
    private Date dateComplete;
    private boolean isComplete;

    public Game(String title, String platform, String description) {

        this.title = title;
        this.platform = platform;
        this.description = description;
        this.gameID = UUID.randomUUID();
        this.dateComplete = new Date();
        this.isComplete = false;
    }

    public UUID getGameID() {
        return gameID;
    }

    public void setGameID(UUID gameID) {
        this.gameID = gameID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateComplete() {
        return dateComplete;
    }

    public void setDateComplete(Date dateComplete) {
        this.dateComplete = dateComplete;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
