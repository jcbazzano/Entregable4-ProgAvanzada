package com.miplaylist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.miplaylist.service.PlaylistService;

public class PlaylistServiceTest {
    private PlaylistService playlistService;
    
    @Before
    public void setUp() {
        playlistService = new PlaylistService();
    }
    
    @Test
    public void testAgregarVideo() {
        playlistService.agregarVideo("Test Video", "https://youtube.com/watch?v=test123");
        assertEquals(1, playlistService.getPlaylist().getVideos().size());
    }
    
    @Test
    public void testEliminarVideo() {
        playlistService.agregarVideo("Test Video", "https://youtube.com/watch?v=test123");
        String videoId = playlistService.getPlaylist().getVideos().get(0).getId();
        
        playlistService.eliminarVideo(videoId);
        assertEquals(0, playlistService.getPlaylist().getVideos().size());
    }
    
    @Test
    public void testDarLike() {
        playlistService.agregarVideo("Test Video", "https://youtube.com/watch?v=test123");
        String videoId = playlistService.getPlaylist().getVideos().get(0).getId();
        
        playlistService.darLike(videoId);
        assertEquals(1, playlistService.getPlaylist().getVideos().get(0).getLikes());
    }
    
    @Test
    public void testToggleFavorito() {
        playlistService.agregarVideo("Test Video", "https://youtube.com/watch?v=test123");
        String videoId = playlistService.getPlaylist().getVideos().get(0).getId();
        
        playlistService.toggleFavorito(videoId);
        assertTrue(playlistService.getPlaylist().getVideos().get(0).isFavorito());
        
        playlistService.toggleFavorito(videoId);
        assertFalse(playlistService.getPlaylist().getVideos().get(0).isFavorito());
    }
    
    @Test
    public void testPersistencia() {
        // Test que verifica que los datos se mantienen entre instancias
        playlistService.agregarVideo("Video Persistente", "https://youtube.com/watch?v=persist");
        int videosAntes = playlistService.getPlaylist().getVideos().size();
        
        // Simular recreación del servicio (como reiniciar la app)
        PlaylistService nuevoService = new PlaylistService();
        int videosDespues = nuevoService.getPlaylist().getVideos().size();
        
        assertEquals(videosAntes, videosDespues);
    }
}