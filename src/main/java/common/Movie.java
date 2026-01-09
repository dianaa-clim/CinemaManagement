package common;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Movie {
    private int id;
    private String title;
    private int duration;
    private String genre;
    private String description;
    private BigDecimal rating;
    private BigDecimal basePrice;
    private String imageUrl;
    private LocalDate runFrom;
    private LocalDate runTo;
    private LocalDate premiereDate;

    // ✅ constructor gol (OBLIGATORIU pentru Spring)
    public Movie() {
    }

    public Movie(int id,
                 String title,
                 int duration,
                 String genre,
                 String description,
                 BigDecimal rating,
                 BigDecimal basePrice,
                 String imageUrl,
                 LocalDate runFrom,
                 LocalDate runTo,
                 LocalDate premiereDate) {

        this.id = id;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.description = description;
        this.rating = rating;
        this.basePrice = basePrice;
        this.imageUrl = imageUrl;
        this.runFrom = runFrom;
        this.runTo = runTo;
        this.premiereDate = premiereDate;
    }

    public Integer getIdMovie() { return id; }
    public void setIdMovie(Integer idMovie) { this.id = idMovie; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDate getRunFrom() { return runFrom; }
    public void setRunFrom(LocalDate runFrom) { this.runFrom = runFrom; }

    public LocalDate getRunTo() { return runTo; }
    public void setRunTo(LocalDate runTo) { this.runTo = runTo; }

    public LocalDate getPremiereDate() { return premiereDate; }
    public void setPremiereDate(LocalDate premiereDate) { this.premiereDate = premiereDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
