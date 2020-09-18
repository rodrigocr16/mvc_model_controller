package br.gov.sp.fatec.padroesprojetos;

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
        UsuarioDaoJpa usuarioDaoJpa = new UsuarioDaoJpa(manager);
        usuarioDaoJpa.cadastrarUsuario("usuario01", "senha", "jhon doe");
        usuarioDaoJpa.cadastrarUsuario("usuario02", "senha", "jane doe");
        usuarioDaoJpa.cadastrarUsuario("usuario03", "senha", "jack doe");

        // READ
        System.out.println(usuarioDaoJpa.buscarUsuario("usuario01").getNomeExibicao());
        
        // UPDATE
        Usuario usuario = new Usuario();
        usuario = usuarioDaoJpa.buscarUsuario("usuario03");
        usuario.setSenha("senhaF0rt£");
        usuarioDaoJpa.commitUsuario(usuario);

        // DELETE
        usuarioDaoJpa.removerUsuario("usuario02");
    }
}
