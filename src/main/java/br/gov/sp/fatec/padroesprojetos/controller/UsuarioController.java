package br.gov.sp.fatec.padroesprojetos.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.gov.sp.fatec.padroesprojetos.dao.UsuarioDao;
import br.gov.sp.fatec.padroesprojetos.entity.Usuario;
import br.gov.sp.fatec.padroesprojetos.dao.UsuarioDaoJpa;

public class UsuarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // Recupera o parâmetro id (de usuario?nomeUsuario=<valor>)
        //String nomeUsuario = String.valueOf(req.getParameter("nomeUsuario"));
        String nomeUsuario = req.getParameter("nomeUsuario");

        // Busca usuario com o nome de usuario
        UsuarioDao usuarioDao = new UsuarioDaoJpa();
        Usuario usuario = usuarioDao.buscarUsuario(nomeUsuario);
        
        // Usamos o Jackson para transformar o objeto em um JSON (String)
        ObjectMapper mapper = new ObjectMapper();
        String usuarioJson = mapper.writeValueAsString(usuario);
        
        // Formatamos a resposta
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        PrintWriter out = resp.getWriter();
        out.print(usuarioJson);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // Recuperamos o corpo da requisição e transformamos o JSON em objeto
        ObjectMapper mapper = new ObjectMapper();
        Usuario usuario = mapper.readValue(req.getReader(), Usuario.class);

        // Salvamos no Banco de Dados
        UsuarioDao usuarioDao = new UsuarioDaoJpa();
        usuarioDao.commitUsuario(usuario);

        // Retornamos o registro gerado
        String usuarioJson = mapper.writeValueAsString(usuario);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // O código 201 requer que retornemos um header de Location
        resp.setStatus(201);
        String location = req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/usuario?nomeUsuario="
                + usuario.getNomeUsuario();
        resp.setHeader("Location", location);
        PrintWriter out = resp.getWriter();
        out.print(usuarioJson);
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // Recupera o parâmetro id (de usuario?nomeUsuario=<valor>)
        //String nomeUsuario = String.valueOf(req.getParameter("nomeUsuario"));
        String nomeUsuario = req.getParameter("nomeUsuario");

        // Recuperamos o corpo da requisição e transformamos o JSON em objeto
        ObjectMapper mapper = new ObjectMapper();
        Usuario up_usuario = mapper.readValue(req.getReader(), Usuario.class);

        // Salvamos no Banco de Dados
        UsuarioDao usuarioDao = new UsuarioDaoJpa();
        usuarioDao.updateUsuario(nomeUsuario, up_usuario);

        // Formatamos a resposta
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        PrintWriter out = resp.getWriter();
        out.print("");
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // Recupera o parâmetro id (de usuario?nomeUsuario=<valor>)
        //String nomeUsuario = String.valueOf(req.getParameter("nomeUsuario"));
        String nomeUsuario = req.getParameter("nomeUsuario");

        // Busca usuario com o nome de usuario
        UsuarioDao usuarioDao = new UsuarioDaoJpa();
        usuarioDao.removerUsuario(nomeUsuario);
        
        // Formatamos a resposta
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        PrintWriter out = resp.getWriter();
        out.print("");
        out.flush();
    }

}