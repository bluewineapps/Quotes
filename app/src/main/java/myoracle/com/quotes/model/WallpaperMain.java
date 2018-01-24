package myoracle.com.quotes.model;

import java.io.Serializable;

/**
 * Created by Midhun on 18-11-2017.
 */

public class WallpaperMain implements Serializable {

    private Wallpaper wallpaper;
    private Integer id;

    public Wallpaper getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(Wallpaper wallpaper) {
        this.wallpaper = wallpaper;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
