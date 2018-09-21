package myoracle.com.quotes.model;

import java.io.Serializable;

/**
 * Created by Midhun on 18-11-2017.
 */

public class Wallpaper implements Serializable {

   private String small;
   private String medium;
   private String large;
   private String view;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
