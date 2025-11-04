package com.miplaylist.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {
    
    public void guardarArchivo(String nombreArchivo, String contenido) throws IOException {
        Path path = Paths.get(nombreArchivo);
        Files.writeString(path, contenido);
    }
    
    public String cargarArchivo(String nombreArchivo) throws IOException {
        Path path = Paths.get(nombreArchivo);
        if (Files.exists(path)) {
            return Files.readString(path);
        }
        return null;
    }
}