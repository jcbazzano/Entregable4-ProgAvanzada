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
public class PlaylistServlet extends HttpServlet {
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
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException {
        String action = req.getParameter("action");
        Map<String, Object> response = new HashMap<>();
        
        try {
            switch (action) {
                case "agregar":
                    String nombre = req.getParameter("nombre");
                    String link = req.getParameter("link");
                    playlistService.agregarVideo(nombre, link);
                    response.put("success", true);
                    break;
                    
                case "eliminar":
                    String idEliminar = req.getParameter("id");
                    playlistService.eliminarVideo(idEliminar);
                    response.put("success", true);
                    break;
                    
                case "like":
                    String idLike = req.getParameter("id");
                    playlistService.darLike(idLike);
                    response.put("success", true);
                    break;
                    
                case "favorito":
                    String idFavorito = req.getParameter("id");
                    playlistService.toggleFavorito(idFavorito);
                    response.put("success", true);
                    break;
                    
                default:
                    response.put("success", false);
                    response.put("message", "Acción no válida");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        
        resp.setContentType("application/json");
        mapper.writeValue(resp.getWriter(), response);
    }
}