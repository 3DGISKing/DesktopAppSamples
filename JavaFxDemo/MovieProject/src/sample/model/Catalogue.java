package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Catalogue {

    private final Kiosk kiosk;
    private final ObservableList<Movie> moviesAvailable;
    private final ObservableList<Movie> moviesRented;
    private final ObservableList<Genre> genres;

    public Catalogue(Kiosk kiosk) {
        this.kiosk = kiosk;
        moviesAvailable = FXCollections.observableArrayList();
        moviesRented = FXCollections.observableArrayList();
        genres = FXCollections.observableArrayList();
                
        //DATA BASE OF MOVIES
        Genre g = new Genre("SciFi");
        addGenre(g);
        
        moviesAvailable.add(new Movie("Matrix", 1999, g, 3));
        moviesAvailable.add(new Movie("Jurassic Park", 1993, g, 4));
        moviesAvailable.add(new Movie("Terminator 2", 1991, g, 3));
        
        g = new Genre("Drama");
        addGenre(g);
        
        moviesAvailable.add(new Movie("Titanic", 1997, g, 4));
        
        g = new Genre("Crime");
        addGenre(g);
        moviesAvailable.add(new Movie("The Silence of the Lambs", 1991, g, 3));
        
    }


    private void addMovie(Movie movie) {

        if (!moviesAvailable.contains(movie))
            moviesAvailable.add(movie);

    }

    public void addMovie(String title, int year, String genre, int price) {
        Genre g = oldGenre(genre) ? retrieveGenre(genre) : new Genre(genre);
        addGenre(g);

        this.addMovie(new Movie(title, year, g, price));
    }

    private void addGenre(Genre genre) {

        if (!genres.contains(genre))
            genres.add(genre);

    }

    public ObservableList<Movie> getAllMovies() {

        ObservableList<Movie> allMovies = FXCollections.observableArrayList();        
        allMovies.addAll(moviesRented);
        allMovies.addAll(moviesAvailable);        
        return allMovies;
    }

    public ObservableList<Movie> getAvailableMovies() {
        return this.moviesAvailable;
    }

    private Movie getMovieByTitle(String title) {
        for (Movie b : this.getAllMovies()) {
            if (b.titleMatches(title))
                return b;
        }

        return null;
    }

    public boolean rentMovieToCustomer(Movie movie, Customer customer) {
        if(customer.getBalance() >= movie.getPrice()) {
            customer.rentMovie(movie);
            customer.deductAmount(movie.getPrice());
            moviesRented.add(movie);
            moviesAvailable.remove(movie); 
            return true;
        }
        return false;
    }

    private boolean oldGenre(String genre) {

        for (Genre g : this.genres) {
            if (g.isSameGenreAs(genre))
                return true;
        }

        return false;
    }

    private Genre retrieveGenre(String genre) {

        for (Genre g : this.genres) {
            if (g.isSameGenreAs(genre))
                return g;
        }

        return null;
    }

    public boolean returnMovieFromCustomer(Movie movie, Customer customer) {

        if (customer.hasMovie(movie)) {

            customer.returnMovie(movie);
            moviesRented.remove(movie);
            moviesAvailable.add(movie);
            return true;

        }
        return false;
    }
    
    public boolean hasMovie(String title, int year) {

        for (Movie m : this.getAllMovies()) {
            if (m.titleMatches(title) && m.getYear() == year) {
                return true;
            }
        }

        return false;
    }
    
    public void removeMovie(Movie movie) {
        this.moviesAvailable.remove(movie);
        Genre genre = movie.getGenre();
         if (this.getMoviesInGenre(genre).isEmpty()) {
            genres.remove(genre);
        }
    }
   
    public ObservableList<Movie> getMoviesInGenre(Genre genre) {
        ObservableList<Movie> moviesInGenre = FXCollections.observableArrayList();

        for (Movie m : this.getAllMovies()) {
            if (m.getGenre().isSameGenreAs(genre)) {
                moviesInGenre.add(m);
            }
        }

        return moviesInGenre;
    }
          
    public ObservableList<Movie> getMoviesByYear(int year) {

       ObservableList<Movie> moviesByYear = FXCollections.observableArrayList();

        for (Movie m : this.getAllMovies()) {
            if (m.getYear() == year) {
                moviesByYear.add(m);
            }
        }
        
        return moviesByYear;     
    }

    public ObservableList<Genre> getGenres() {
        return this.genres;
    }
    
    public ObservableList<Integer> getYears() {
        ObservableList<Integer> availableYears = FXCollections.observableArrayList();

        for (Movie m : this.getAllMovies()) {
            int year = m.getYear();
            if(!availableYears.contains(year))
                availableYears.add(year);
        }
        return availableYears;    
    }
    
    public Customer getCustomer(int id) {
        return this.kiosk.getCustomer(id);
    }
}
