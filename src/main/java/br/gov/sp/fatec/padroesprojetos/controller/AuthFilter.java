package br.gov.sp.fatec.padroesprojetos.controller;

import java.util.Base64;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.UnsupportedEncodingException;

import javax.persistence.NoResultException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.gov.sp.fatec.padroesprojetos.dao.UsuarioDao;
import br.gov.sp.fatec.padroesprojetos.dao.UsuarioDaoJpa;
import br.gov.sp.fatec.padroesprojetos.entity.Usuario;

public class AuthFilter implements Filter {

    private ServletContext context;
    private String realm = "PROTECTED";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        this.context.log("Filtro de autenticacao acessado!");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String metodo = request.getMethod();

        // Verifica se tem o header Authorization
        String authHeader = request.getHeader("Authorization");
        try{
            if (authHeader != null) {
                // Divide o conteúdo do header por espacos
                StringTokenizer st = new StringTokenizer(authHeader);

                // Se possui conteúdo
                if (st.hasMoreTokens()) {
                    String basic = st.nextToken();

                    // Verifica se possui o prefixo Basic
                    if (basic.equalsIgnoreCase("Basic")) {
                        try {
                            // Extrai as credenciais (Base64)
                            String credentials = new String(Base64.getDecoder().decode(st.nextToken()));
                            this.context.log("Credentials: " + credentials); //usuario:senha

                            // Separa as credenciais em usuario e senha
                            Integer p = credentials.indexOf(":");
                            if (p != -1) {
                                String _username = credentials.substring(0, p).trim();
                                String _password = credentials.substring(p + 1).trim();

                                UsuarioDao usuarioDao = new UsuarioDaoJpa();
                                Usuario usuario = usuarioDao.buscarUsuario(_username);
                                String clearance = usuarioDao.getClearance(_username);                            

                                // Se nao bate com configuracao retorna erro
                                if(!usuario.getSenha().equals(_password)) {
                                    unauthorized(response, "Credenciais invalidas");
                                    return;
                                }

                                if(metodo == "DELETE" || metodo == "PUT"){
                                    if(clearance == "Usuario"){
                                        unauthorized(response, "Acesso restrito");
                                        return;
                                    }
                                }

                                // Prossegue com a requisicao
                                chain.doFilter(req, res);
                            } else {
                                unauthorized(response, "Token de autenticacao invalido");
                            }
                        } catch (UnsupportedEncodingException e) {
                            throw new Error("Impossivel recuperar autenticacao", e);
                        }
                    }
                }
            } else {
                unauthorized(response);
            }
        }
        catch(NullPointerException npe){
            response.sendError(400, "Usuario nao encontrado");
            return;
        }
        catch(NoResultException nre){
            response.sendError(404, "Valor buscado inválido");
            return;
        }
    }

    @Override
    public void destroy() {
        // Aqui pode-se desalocar qualquer recurso

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.context = config.getServletContext();
        this.context.log("Filtro inicializado!");
        String paramRealm = config.getInitParameter("realm");
        if (paramRealm != null && paramRealm.length() > 0) {
            this.realm = paramRealm;
        }
    }

    private void unauthorized(HttpServletResponse response, String message) throws IOException {
        response.setHeader("WWW-Authenticate", "Basic realm=\"" + realm + "\"");
        response.sendError(401, message);
    }

    private void unauthorized(HttpServletResponse response) throws IOException {
        unauthorized(response, "Unauthorized");
    }

}