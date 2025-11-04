package com.miplaylist.service;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miplaylist.model.Playlist;
import com.miplaylist.model.Video;

public class PlaylistService {
    private Playlist playlist;
    private final FileService fileService;
    private final ObjectMapper mapper;
    private static final String DATA_FILE = "playlist_data.json";
    
    public PlaylistService() {
        this.playlist = new Playlist();
        this.fileService = new FileService();
        this.mapper = new ObjectMapper();
        cargarPlaylist();
    }
    
    public void agregarVideo(String nombre, String link) {
        Video video = new Video(nombre, link);
        playlist.agregarVideo(video);
        guardarPlaylist();
    }
    
    public void eliminarVideo(String id) {
        playlist.eliminarVideo(id);
        guardarPlaylist();
    }
    
    public void darLike(String id) {
        Video video = playlist.buscarVideo(id);
        if (video != null) {
            video.addLike();
            guardarPlaylist();
        }
    }
    
    public void toggleFavorito(String id) {
        Video video = playlist.buscarVideo(id);
        if (video != null) {
            video.toggleFavorito();
            guardarPlaylist();
        }
    }
    
    public Playlist getPlaylist() {
        return playlist;
    }
    
    private void guardarPlaylist() {
        try {
            String json = mapper.writeValueAsString(playlist);
            fileService.guardarArchivo(DATA_FILE, json);
        } catch (IOException e) {
            System.err.println("Error guardando playlist: " + e.getMessage());
        }
    }
    
    private void cargarPlaylist() {
        try {
            String json = fileService.cargarArchivo(DATA_FILE);
            if (json != null && !json.isEmpty()) {
                this.playlist = mapper.readValue(json, Playlist.class);
            }
        } catch (IOException e) {
            System.err.println("Error cargando playlist: " + e.getMessage());
        }
    }
}