package br.gov.sp.fatec.padroesprojetos;

import br.gov.sp.fatec.padroesprojetos.dao.GrupoDaoJpa;
import br.gov.sp.fatec.padroesprojetos.dao.LutadorDaoJpa;
import br.gov.sp.fatec.padroesprojetos.dao.UsuarioDaoJpa;
import br.gov.sp.fatec.padroesprojetos.entity.Grupo;
import br.gov.sp.fatec.padroesprojetos.entity.Personagem;
import br.gov.sp.fatec.padroesprojetos.entity.Usuario;

import javax.persistence.EntityManagerFactory;

import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;


public class App 
{
    public static void main( String[] args )
    {   
        EntityManagerFactory factory = Persistence
            .createEntityManagerFactory("padroesprojetos");
        EntityManager manager = factory.createEntityManager();
        
        /* SEGMENTO DEDICADO A PARTE DE USUÁRIO */
        // CREATE
        UsuarioDaoJpa usuarioBanco = new UsuarioDaoJpa(manager);
        usuarioBanco.cadastrarUsuario("jovemnerd", "senha", "Alexandre Menezes");
        usuarioBanco.cadastrarUsuario("azaghal", "senha", "Deive Pazos");
        usuarioBanco.cadastrarUsuario("tucano", "senha", "Fernando Russel");
        usuarioBanco.cadastrarUsuario("jotape", "senha", "Joao Paulo");
        usuarioBanco.cadastrarUsuario("vinceglotto", "senha", "Eduardo Spohr");
        usuarioBanco.cadastrarUsuario("caquinho", "senha", "Carlos Voltor");
        usuarioBanco.cadastrarUsuario("rex2099", "senha", "Tiago Lamarca");

        // READ
        System.out.println(usuarioBanco.buscarUsuario("jovemnerd").getNomeExibicao());
        
        // UPDATE
        Usuario usuario = new Usuario();
        usuario = usuarioBanco.buscarUsuario("azaghal");
        usuario.setSenha("senhaF0rt£");
        usuarioBanco.commitUsuario(usuario);

        // DELETE
        usuarioBanco.removerUsuario("vinceglotto");


        /* SEGMENTO DEDICADO A PARTE DE PERSONAGENS */
        // CREATE
        LutadorDaoJpa lutadorBanco = new LutadorDaoJpa(manager);
        //FeiticeiroDaoJpa feiticeiroBanco = new FeiticeiroDaoJpa(manager);
        //feiticeiroBanco.cadastrarFeiticeiro("Feldon", "Elfo", "Druida", usuarioBanco.buscarUsuario(""));
        //feiticeiroBanco.cadastrarFeiticeiro("Nilperto", "Humano", "Bardo", usuarioBanco.buscarUsuario(""));
        lutadorBanco.cadastrarLutador("Rufus Cave", "Humano", "Bárbaro", usuarioBanco.buscarUsuario("rex2099"));
        lutadorBanco.cadastrarLutador("Roshton Cave", "Humano", "Bárbaro", usuarioBanco.buscarUsuario("caquinho"));
        lutadorBanco.cadastrarLutador("Ruprest Ruprestson", "Doppleganger", "Ladino", usuarioBanco.buscarUsuario("azaghal"));
        

        /* SEGMENTO DEDICADO A PARTE DO GRUPO */
        // CREATE
        Grupo grupo = new Grupo();
        grupo.setIntegrantes(new HashSet<Personagem>());
        grupo.setMestre(usuarioBanco.buscarUsuario("jovemnerd"));
        grupo.getIntegrantes().add(lutadorBanco.buscarLutador("Rufus Cave"));
        grupo.getIntegrantes().add(lutadorBanco.buscarLutador("Roshton Cave"));
        grupo.getIntegrantes().add(lutadorBanco.buscarLutador("Ruprest Ruprestson"));

        GrupoDaoJpa grupoBanco = new GrupoDaoJpa(manager);
        grupoBanco.commitGrupo(grupo);
    }
}
