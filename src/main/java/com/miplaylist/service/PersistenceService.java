package com.miplaylist.service;

import com.miplaylist.model.Playlist;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class PersistenceService {
    private final FileService fileService;
    private final ObjectMapper mapper;
    private static final String DATA_FILE = "playlist_data.json";
    
    public PersistenceService() {
        this.fileService = new FileService();
        this.mapper = new ObjectMapper();
    }
    
    public void guardarPlaylist(Playlist playlist) {
        try {
            String json = mapper.writeValueAsString(playlist);
            fileService.guardarArchivo(DATA_FILE, json);
            System.out.println("💾 Playlist guardada correctamente");
        } catch (IOException e) {
            System.err.println("❌ Error guardando playlist: " + e.getMessage());
        }
    }
    
    public Playlist cargarPlaylist() {
        try {
            String json = fileService.cargarArchivo(DATA_FILE);
            if (json != null && !json.isEmpty()) {
                Playlist playlist = mapper.readValue(json, Playlist.class);
                System.out.println("📀 Playlist cargada correctamente. Videos: " + playlist.getVideos().size());
                return playlist;
            }
        } catch (IOException e) {
            System.err.println("❌ Error cargando playlist: " + e.getMessage());
        }
        System.out.println("🆕 No hay datos previos, creando nueva playlist");
        return null;
    }
}