package myoracle.com.quotes.model;

import java.io.Serializable;

/**
 * Created by Midhun on 19-01-2018.
 */

public class Games implements Serializable{

    private String name;
    private String description;
    private String image;
    private String gameData;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGameData() {
        return gameData;
    }

    public void setGameData(String gameData) {
        this.gameData = gameData;
    }
}
