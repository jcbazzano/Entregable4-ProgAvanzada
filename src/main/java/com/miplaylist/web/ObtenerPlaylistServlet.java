package com.miplaylist.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miplaylist.model.Video;
import com.miplaylist.service.PlaylistService;

@WebServlet("/playlist")
public class ObtenerPlaylistServlet extends HttpServlet {
    private PlaylistService playlistService;
    private ObjectMapper mapper;
    
    @Override
    public void init() throws ServletException {
        this.playlistService = new PlaylistService();
        this.mapper = new ObjectMapper();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            List<Video> videos = playlistService.getPlaylist().getVideos();
            String json = mapper.writeValueAsString(videos);
            resp.getWriter().write(json);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al obtener videos: " + e.getMessage());
            mapper.writeValue(resp.getWriter(), error);
        }
    }
}