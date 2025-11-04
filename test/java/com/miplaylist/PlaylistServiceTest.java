package com.miplaylist;

import static org.junit.Assert.assertEquals;
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
}