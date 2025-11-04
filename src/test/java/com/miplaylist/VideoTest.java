package com.miplaylist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.miplaylist.model.Video;

public class VideoTest {
    
    @Test
    public void testVideoCreation() {
        Video video = new Video("Test Video", "https://youtube.com/watch?v=test123");
        assertNotNull(video.getId());
        assertEquals("Test Video", video.getNombre());
        assertEquals("https://youtube.com/watch?v=test123", video.getLink());
        assertEquals(0, video.getLikes());
        assertFalse(video.isFavorito());
    }
    
    @Test
    public void testAddLike() {
        Video video = new Video("Test Video", "https://youtube.com/watch?v=test123");
        video.addLike();
        assertEquals(1, video.getLikes());
    }
    
    @Test
    public void testToggleFavorito() {
        Video video = new Video("Test Video", "https://youtube.com/watch?v=test123");
        video.toggleFavorito();
        assertTrue(video.isFavorito());
        video.toggleFavorito();
        assertFalse(video.isFavorito());
    }
}