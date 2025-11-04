package com.miplaylist.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)  // ← Agregar esta anotación
public class Playlist {
    private final List<Video> videos;
    
    public Playlist() {
        this.videos = new ArrayList<>();
    }
    
    public void agregarVideo(Video video) {
        videos.add(video);
    }
    
    public void eliminarVideo(String id) {
        videos.removeIf(v -> v.getId().equals(id));
    }
    
    public Video buscarVideo(String id) {
        return videos.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<Video> getVideos() {
        return new ArrayList<>(videos);
    }
    
    public void setVideos(List<Video> videos) {  // ← Agregar setter para deserialización
        if (videos != null) {
            this.videos.clear();
            this.videos.addAll(videos);
        }
    }
    
    @JsonIgnore
    public List<Video> getFavoritos() {
        return videos.stream()
                .filter(Video::isFavorito)
                .toList();
    }
}