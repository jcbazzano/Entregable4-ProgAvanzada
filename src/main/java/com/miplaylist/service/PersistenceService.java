package com.miplaylist.service;

import java.io.IOException;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miplaylist.model.Playlist;

public class PersistenceService {
    private final FileService fileService;
    private final ObjectMapper mapper;
    private static final String DATA_DIR = "C:/data-miplaylist/";
    private static final String DATA_FILE = DATA_DIR + "playlist_data.json";
    
    public PersistenceService() {
        this.fileService = new FileService();
        this.mapper = new ObjectMapper();
        crearDirectorioDatos();
    }
    
    private void crearDirectorioDatos() {
        try {
            java.nio.file.Files.createDirectories(Paths.get(DATA_DIR));
            System.out.println("📁 Directorio de datos creado: " + DATA_DIR);
        } catch (IOException e) {
            System.err.println("❌ Error creando directorio: " + e.getMessage());
        }
    }
    
    public void guardarPlaylist(Playlist playlist) {
        try {
            String json = mapper.writeValueAsString(playlist);
            fileService.guardarArchivo(DATA_FILE, json);
            System.out.println("💾 Playlist guardada en: " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("❌ Error guardando playlist: " + e.getMessage());
        }
    }
    
    public Playlist cargarPlaylist() {
        try {
            String json = fileService.cargarArchivo(DATA_FILE);
            if (json != null && !json.isEmpty()) {
                Playlist playlist = mapper.readValue(json, Playlist.class);
                System.out.println("📀 Playlist cargada desde: " + DATA_FILE);
                System.out.println("🎬 Videos cargados: " + playlist.getVideos().size());
                return playlist;
            }
        } catch (IOException e) {
            System.err.println("❌ Error cargando playlist: " + e.getMessage());
        }
        System.out.println("🆕 No hay datos previos, creando nueva playlist");
        return new Playlist();
    }
}