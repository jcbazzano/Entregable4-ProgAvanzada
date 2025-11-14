package com.miplaylist.service;

import java.io.IOException;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miplaylist.model.Playlist;

public class PersistenceService {
    private final FileService fileService;
    private final ObjectMapper mapper;
    private final String dataDir;
    private final String dataFile;
    
    public PersistenceService() {
        this.fileService = new FileService();
        this.mapper = new ObjectMapper();

        String dir = System.getProperty("playlist.data.dir", "C:/data-miplaylist/");
        if (!dir.endsWith("/") && !dir.endsWith("\\")) {
            dir = dir + "/";
        }
        
        this.dataDir = dir;
        this.dataFile = this.dataDir + "playlist_data.json";

        crearDirectorioDatos();
    }
    
    private void crearDirectorioDatos() {
        try {
            java.nio.file.Files.createDirectories(Paths.get(dataDir));
            System.out.println("Directorio de datos (playlist): " + dataDir);
        } catch (IOException e) {
            System.err.println("Error creando directorio: " + e.getMessage());
        }
    }
    
    public void guardarPlaylist(Playlist playlist) {
        try {
            String json = mapper.writeValueAsString(playlist);
            fileService.guardarArchivo(dataFile, json);
            System.out.println("Playlist guardada en: " + dataFile);
        } catch (IOException e) {
            System.err.println("Error guardando playlist: " + e.getMessage());
        }
    }
    
    public Playlist cargarPlaylist() {
        try {
            String json = fileService.cargarArchivo(dataFile);
            if (json == null || json.isBlank()) {
                System.out.println("No hay archivo o está vacío, creando nueva playlist");
                return new Playlist();
            }
            
            Playlist playlist = mapper.readValue(json, Playlist.class);
            int size = playlist.getVideos() == null ? 0 : playlist.getVideos().size();
            System.out.println("Playlist cargada desde: " + dataFile);
            System.out.println("Videos cargados: " + size);
            return playlist;
            
        } catch (IOException e) {
            System.err.println("Error cargando playlist, se usará una vacía: " + e.getMessage());
            return new Playlist();  
        }
    }
}
