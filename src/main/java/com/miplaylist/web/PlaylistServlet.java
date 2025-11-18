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
    
    // @Override
    // protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
    //         throws IOException {
    //     String action = req.getParameter("action");
    //     Map<String, Object> response = new HashMap<>();
        
    //     try {
    //         switch (action) {
    //             case "agregar":
    //                 String nombre = req.getParameter("nombre");
    //                 String link = req.getParameter("link");
    //                 playlistService.agregarVideo(nombre, link);
    //                 response.put("success", true);
    //                 break;
                    
    //             case "eliminar":
    //                 String idEliminar = req.getParameter("id");
    //                 playlistService.eliminarVideo(idEliminar);
    //                 response.put("success", true);
    //                 break;
                    
    //             case "like":
    //                 String idLike = req.getParameter("id");
    //                 playlistService.darLike(idLike);
    //                 response.put("success", true);
    //                 break;
                    
    //             case "favorito":
    //                 String idFavorito = req.getParameter("id");
    //                 playlistService.toggleFavorito(idFavorito);
    //                 response.put("success", true);
    //                 break;
                    
    //             default:
    //                 response.put("success", false);
    //                 response.put("message", "Acción no válida");
    //         }
    //     } catch (Exception e) {
    //         response.put("success", false);
    //         response.put("message", e.getMessage());
    //     }
        
    //     resp.setContentType("application/json");
    //     mapper.writeValue(resp.getWriter(), response);
    // }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        Map<String, Object> response = handleAction(action, req);
        sendJsonResponse(resp, response);
    }

    private Map<String, Object> handleAction(String action, HttpServletRequest req) {
        try {
            switch (action) {
                case "agregar": return handleAgregar(req);
                case "eliminar": return handleEliminar(req);
                case "like": return handleLike(req);
                case "favorito": return handleFavorito(req);
                default: return createErrorResponse("Acción no válida");
            }
        } catch (Exception e) {
            return createErrorResponse(e.getMessage());
        }
    }

    private Map<String, Object> handleAgregar(HttpServletRequest req) {
        String nombre = req.getParameter("nombre");
        String link = req.getParameter("link");
        playlistService.agregarVideo(nombre, link);
        return createSuccessResponse("Video agregado exitosamente");
    }

    private Map<String, Object> handleEliminar(HttpServletRequest req) {
        String id = req.getParameter("id");
        playlistService.eliminarVideo(id);
        return createSuccessResponse("Video eliminado exitosamente");
    }

    private Map<String, Object> handleLike(HttpServletRequest req) {
        String id = req.getParameter("id");
        playlistService.darLike(id);
        return createSuccessResponse("Like agregado exitosamente");
    }

    private Map<String, Object> handleFavorito(HttpServletRequest req) {
        String id = req.getParameter("id");
        playlistService.toggleFavorito(id);
        return createSuccessResponse("Favorito actualizado exitosamente");
    }

    private Map<String, Object> createSuccessResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        return response;
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return response;
    }

    private void sendJsonResponse(HttpServletResponse resp, Map<String, Object> response) 
            throws IOException {
        resp.setContentType("application/json");
        mapper.writeValue(resp.getWriter(), response);
    }
    
}