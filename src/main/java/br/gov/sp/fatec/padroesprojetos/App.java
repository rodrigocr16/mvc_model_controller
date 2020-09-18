package br.gov.sp.fatec.padroesprojetos;

import br.gov.sp.fatec.padroesprojetos.dao.LutadorDaoJpa;
import br.gov.sp.fatec.padroesprojetos.dao.UsuarioDaoJpa;
import br.gov.sp.fatec.padroesprojetos.entity.Usuario;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;


public class App 
{
    public static void main( String[] args )
    {   
        EntityManagerFactory factory = Persistence
            .createEntityManagerFactory("padroesprojetos");
        EntityManager manager = factory.createEntityManager();
        
        // CREATE
        UsuarioDaoJpa usuarioBanco = new UsuarioDaoJpa(manager);
        usuarioBanco.cadastrarUsuario("usuario01", "senha", "jhon doe");
        usuarioBanco.cadastrarUsuario("usuario02", "senha", "jane doe");
        usuarioBanco.cadastrarUsuario("usuario03", "senha", "jack doe");

        // READ
        System.out.println(usuarioBanco.buscarUsuario("usuario01").getNomeExibicao());
        
        // UPDATE
        Usuario usuario = new Usuario();
        usuario = usuarioBanco.buscarUsuario("usuario03");
        usuario.setSenha("senhaF0rt£");
        usuarioBanco.commitUsuario(usuario);

        // DELETE
        usuarioBanco.removerUsuario("usuario02");


        /* SEGMENTO DEDICADO A PARTE DE PERSONAGEM - LUTADOR */

        // CREATE
        LutadorDaoJpa lutadorBanco = new LutadorDaoJpa(manager);
        lutadorBanco.cadastrarLutador("Roshton Cave", "Humano", "Bárbaro", usuarioBanco.buscarUsuario("usuario01"));
        
    }
}
