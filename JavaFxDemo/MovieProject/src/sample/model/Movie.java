package sample.model;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private final String title;
    private final int year;
    private final int price;  
    private final  Genre genre;

    public Movie(String title, int year, Genre genre, int price) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.price = price;
    }

    public Genre getGenre() {
        return this.genre;
    }

    public int getPrice() {
        return this.price;
    }
    
    public int getYear() {
        return this.year;
    }
    
    public String getTitle() {
        return this.title;
    }

    public boolean titleMatches(String title) {

        return this.title.equals(title);
    }
    
    public boolean yearMatches(int year) {

        return this.year == year;
    }

    @Override
    public String toString() {
        return this.year+"\t"+this.title + "\t" +this.genre.toString() + "\t$" + this.price;
    }

}
