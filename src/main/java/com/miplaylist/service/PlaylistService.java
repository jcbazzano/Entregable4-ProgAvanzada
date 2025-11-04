package com.miplaylist.service;

import com.miplaylist.model.Playlist;
import com.miplaylist.model.Video;

public class PlaylistService {
    private Playlist playlist;
    private final PersistenceService persistenceService;
    private static final String DATA_FILE = "playlist_data.json";
    
    public PlaylistService() {
        this.persistenceService = new PersistenceService();
        this.playlist = persistenceService.cargarPlaylist();
        
        // Si no hay datos previos, crear playlist vacía
        if (this.playlist == null) {
            this.playlist = new Playlist();
        }
    }

    public void agregarVideo(String nombre, String link) {
        Video video = new Video(nombre, link);
        playlist.agregarVideo(video);
        persistenceService.guardarPlaylist(playlist);
    }
    
    public void eliminarVideo(String id) {
        playlist.eliminarVideo(id);
        persistenceService.guardarPlaylist(playlist);
    }
    
    public void darLike(String id) {
        Video video = playlist.buscarVideo(id);
        if (video != null) {
            video.addLike();
            persistenceService.guardarPlaylist(playlist);
        }
    }
    
    public void toggleFavorito(String id) {
        Video video = playlist.buscarVideo(id);
        if (video != null) {
            video.toggleFavorito();
            persistenceService.guardarPlaylist(playlist);
        }
    }
    
    public Playlist getPlaylist() {
        return playlist;
    }
}