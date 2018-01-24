package myoracle.com.quotes.model;

import java.util.List;

/**
 * Created by Midhun on 20-04-2017.
 */

public class Categories {

    private String category;
    private int categoryId;
    private String cIcon;
    List<Quote> quoteList;
    private String genre;


    public Categories(String category, int categoryId, List<Quote> quoteList, String cIcon, String genre){
        this.category=category;
        this.categoryId=categoryId;
        this.quoteList=quoteList;
        this.cIcon=cIcon;
        this.genre=genre;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public List<Quote> getQuoteList() {
        return quoteList;
    }

    public void setQuoteList(List<Quote> quoteList) {
        this.quoteList = quoteList;
    }

    public void setcIcon(String cIcon) {
        this.cIcon = cIcon;
    }

    public String getcIcon() {
        return cIcon;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
