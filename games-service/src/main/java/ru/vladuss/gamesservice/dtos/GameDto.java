package ru.vladuss.gamesservice.dtos;

public class GameDto {
    private String id;
    private String name;
    private String genre;
    private String platform;

    public GameDto() {
    }

    public GameDto(String id, String name, String genre, String platform) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.platform = platform;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
