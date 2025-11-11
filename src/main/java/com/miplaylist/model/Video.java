package com.miplaylist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)  // ← Agregar esta anotación
public class Video {
    private String id;
    private String nombre;
    private String link;
    private int likes;
    private boolean favorito;

    public Video() {
        this.id = java.util.UUID.randomUUID().toString();
    }

    public Video(String nombre, String link) {
        this();
        this.nombre = nombre;
        this.link = link;
    }
    
    public String getId() { 
        return id; 
    }
    
    public void setId(String id) { 
        this.id = id; 
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    
    public String getLink() { 
        return link; 
    }
    
    public void setLink(String link) { 
        this.link = link; 
    }
    
    public int getLikes() { 
        return likes; 
    }
    
    public void setLikes(int likes) { 
        this.likes = likes; 
    }
    
    public boolean isFavorito() { 
        return favorito; 
    }
    
    public void setFavorito(boolean favorito) { 
        this.favorito = favorito; 
    }
    
    @com.fasterxml.jackson.annotation.JsonIgnore
    public String getEmbedUrl() {
        if (link != null) {
            if (link.contains("youtube.com/watch?v=")) {
                String videoId = link.split("v=")[1].split("&")[0];
                return "https://www.youtube.com/embed/" + videoId;
            } else if (link.contains("youtu.be/")) {
                String videoId = link.split("youtu.be/")[1].split("\\?")[0];
                return "https://www.youtube.com/embed/" + videoId;
            }
        }
        return link;
    }
    
    public void addLike() {
        this.likes++;
    }
    
    public void toggleFavorito() {
        this.favorito = !this.favorito;
    }
}