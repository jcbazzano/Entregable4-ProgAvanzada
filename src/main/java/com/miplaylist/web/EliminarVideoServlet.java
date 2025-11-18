package com.miplaylist.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miplaylist.service.PlaylistService;

@WebServlet("/playlist/eliminar")
public class EliminarVideoServlet extends HttpServlet {
    private PlaylistService playlistService;
    private ObjectMapper mapper;
    
    @Override
    public void init() throws ServletException {
        this.playlistService = new PlaylistService();
        this.mapper = new ObjectMapper();
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String idEliminar = req.getParameter("id");
            playlistService.eliminarVideo(idEliminar);
            response.put("success", true);
            response.put("message", "Video eliminado exitosamente");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar video: " + e.getMessage());
        }
        
        resp.setContentType("application/json");
        mapper.writeValue(resp.getWriter(), response);
    }
}