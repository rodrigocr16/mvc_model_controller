package br.gov.sp.fatec.padroesprojetos;

import java.util.HashSet;

import br.gov.sp.fatec.padroesprojetos.entity.Admin;
import br.gov.sp.fatec.padroesprojetos.entity.Grupo;
import br.gov.sp.fatec.padroesprojetos.entity.Usuario;
import br.gov.sp.fatec.padroesprojetos.dao.GrupoDaoJpa;
import br.gov.sp.fatec.padroesprojetos.dao.LutadorDaoJpa;
import br.gov.sp.fatec.padroesprojetos.dao.UsuarioDaoJpa;
import br.gov.sp.fatec.padroesprojetos.entity.Personagem;
import br.gov.sp.fatec.padroesprojetos.dao.FeiticeiroDaoJpa;

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
        
        // SEGMENTO DEDICADO A PARTE DE USUÁRIO
        // CREATE
        UsuarioDaoJpa usuarioBanco = new UsuarioDaoJpa(manager);
        usuarioBanco.cadastrarUsuario("usuario1", "senha", "Ana");
        usuarioBanco.cadastrarUsuario("usuario2", "senha", "Bernardo");
        usuarioBanco.cadastrarUsuario("usuario3", "senha", "Claudia");
        usuarioBanco.cadastrarUsuario("usuario4", "senha", "Daniel");
        usuarioBanco.cadastrarUsuario("usuario5", "senha", "Eliana");

        // READ
        System.out.println(usuarioBanco.buscarUsuario("usuario1").getNomeExibicao());
        
        // UPDATE
        Usuario usuario = new Usuario();
        usuario = usuarioBanco.buscarUsuario("usuario4");
        usuario.setNomeExibicao("Danilo");
        usuarioBanco.commitUsuario(usuario);

        // DELETE
        usuarioBanco.removerUsuario("usuario5");

        /*
        // SEGMENTO DEDICADO A PARTE DE PERSONAGENS
        // CREATE
        LutadorDaoJpa lutadorBanco = new LutadorDaoJpa(manager);
        FeiticeiroDaoJpa feiticeiroBanco = new FeiticeiroDaoJpa(manager);

        feiticeiroBanco.cadastrarFeiticeiro("Andrew", "Elfo", "Clérigo", usuarioBanco.buscarUsuario("usuario2"));
        feiticeiroBanco.cadastrarFeiticeiro("Elidor", "Halfling", "Clérigo", usuarioBanco.buscarUsuario("usuario3"));
        feiticeiroBanco.cadastrarFeiticeiro("Astrid", "Meio-Elfo", "Clérigo", usuarioBanco.buscarUsuario("usuario4"));

        lutadorBanco.cadastrarLutador("Rufus Cave", "Humano", "Bárbaro", usuarioBanco.buscarUsuario("usuario3"));
        

        // SEGMENTO DEDICADO A PARTE DO GRUPO
        // CREATE
        Grupo grupo = new Grupo();
        GrupoDaoJpa grupoBanco = new GrupoDaoJpa(manager);

        grupo.setIntegrantes(new HashSet<Personagem>());
        grupo.setMestre(usuarioBanco.buscarUsuario("usuario1"));
        grupo.setNomeGrupo("Duque Cave");
        grupo.getIntegrantes().add(lutadorBanco.buscarLutador("Rufus Cave"));
        grupoBanco.commitGrupo(grupo);

        grupo = new Grupo();
        grupo.setIntegrantes(new HashSet<Personagem>());
        grupo.setMestre(usuarioBanco.buscarUsuario("usuario1"));
        grupo.setNomeGrupo("The A-Men");
        grupo.getIntegrantes().add(feiticeiroBanco.buscarFeiticeiro("Andrew"));
        grupo.getIntegrantes().add(feiticeiroBanco.buscarFeiticeiro("Elidor"));
        grupo.getIntegrantes().add(feiticeiroBanco.buscarFeiticeiro("Astrid"));
        grupoBanco.commitGrupo(grupo);

        System.out.print("The A-Men id: " + grupoBanco.buscarGrupo(1l, "The A-Men").getId());
        
        */

        // SEGMENTO DEDICADO À INCLUSÃO DE ADMINISTRADORES

        Admin admin = new Admin();
        admin.setNomeUsuario("admin1");
        admin.setSenha("adm1n");
        admin.setNomeExibicao("Administradora Amanda");
        admin.setClassificacao(5);

        manager.getTransaction().begin();
        if(usuario.getId() == null){
            manager.persist(admin);
        } else {
            manager.merge(admin);
        }
        manager.getTransaction().commit();


        admin.setNomeUsuario("admin2");
        admin.setSenha("adm1n");
        admin.setNomeExibicao("Administrador Bruno");
        admin.setClassificacao(5);

        manager.getTransaction().begin();
        if(usuario.getId() == null){
            manager.persist(admin);
        } else {
            manager.merge(admin);
        }
        manager.getTransaction().commit();
    }
}
