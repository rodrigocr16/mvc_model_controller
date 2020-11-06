package br.gov.sp.fatec.padroesprojetos;

import java.util.HashSet;

import br.gov.sp.fatec.padroesprojetos.entity.Admin;

//import br.gov.sp.fatec.padroesprojetos.entity.Arma;
//import br.gov.sp.fatec.padroesprojetos.entity.Equipamento;

import br.gov.sp.fatec.padroesprojetos.entity.Grupo;
import br.gov.sp.fatec.padroesprojetos.entity.Usuario;
import br.gov.sp.fatec.padroesprojetos.dao.GrupoDaoJpa;
import br.gov.sp.fatec.padroesprojetos.dao.LutadorDaoJpa;
import br.gov.sp.fatec.padroesprojetos.dao.UsuarioDaoJpa;
import br.gov.sp.fatec.padroesprojetos.entity.Personagem;
import br.gov.sp.fatec.padroesprojetos.dao.FeiticeiroDaoJpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.DiscriminatorValue;
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
        usuarioBanco.cadastrarUsuario("rodrigocr16", "caramelo123", "Rodrigosaurus");
        usuarioBanco.cadastrarUsuario("otaviobj", "bj_means_bowser_jr", "Uruck");
        usuarioBanco.cadastrarUsuario("malakianw", ":okhand:", "Peita Treta");
        usuarioBanco.cadastrarUsuario("dartlol", "lloyds2", "Tchurusbango");
        usuarioBanco.cadastrarUsuario("mstriss", "garai123", "Tchurusbago");
        usuarioBanco.cadastrarUsuario("felipin", "rainbow6", "AirsoftGuy1");

        // READ
        System.out.println(usuarioBanco.buscarUsuario("rodrigocr16").getNomeExibicao());
        
        // UPDATE
        Usuario usuario = new Usuario();
        usuario = usuarioBanco.buscarUsuario("mstriss");
        usuario.setNomeExibicao("A_Simplicio");
        usuarioBanco.commitUsuario(usuario);

        // DELETE
        usuarioBanco.removerUsuario("felipin");


        /* SEGMENTO DEDICADO A PARTE DE PERSONAGENS */
        // CREATE
        LutadorDaoJpa lutadorBanco = new LutadorDaoJpa(manager);
        FeiticeiroDaoJpa feiticeiroBanco = new FeiticeiroDaoJpa(manager);

        feiticeiroBanco.cadastrarFeiticeiro("Uruck", "Elfo", "Clérigo", usuarioBanco.buscarUsuario("otaviobj"));
        feiticeiroBanco.cadastrarFeiticeiro("Andrew", "Elfo", "Clérigo", usuarioBanco.buscarUsuario("dartlol"));
        feiticeiroBanco.cadastrarFeiticeiro("Elidor", "Halfling", "Clérigo", usuarioBanco.buscarUsuario("malakianw"));
        feiticeiroBanco.cadastrarFeiticeiro("Astrid", "Meio-Elfo", "Clérigo", usuarioBanco.buscarUsuario("mstriss"));

        lutadorBanco.cadastrarLutador("Rufus Cave", "Humano", "Bárbaro", usuarioBanco.buscarUsuario("malakianw"));
        

        /* SEGMENTO DEDICADO A PARTE DO GRUPO */
        // CREATE
        Grupo grupo = new Grupo();
        GrupoDaoJpa grupoBanco = new GrupoDaoJpa(manager);

        grupo.setIntegrantes(new HashSet<Personagem>());
        grupo.setMestre(usuarioBanco.buscarUsuario("rodrigocr16"));
        grupo.setNomeGrupo("Duque Cave");
        grupo.getIntegrantes().add(lutadorBanco.buscarLutador("Rufus Cave"));
        grupoBanco.commitGrupo(grupo);

        grupo = new Grupo();
        grupo.setIntegrantes(new HashSet<Personagem>());
        grupo.setMestre(usuarioBanco.buscarUsuario("rodrigocr16"));
        grupo.setNomeGrupo("The A-Men");
        grupo.getIntegrantes().add(feiticeiroBanco.buscarFeiticeiro("Uruck"));
        grupo.getIntegrantes().add(feiticeiroBanco.buscarFeiticeiro("Elidor"));
        grupo.getIntegrantes().add(feiticeiroBanco.buscarFeiticeiro("Andrew"));
        grupo.getIntegrantes().add(feiticeiroBanco.buscarFeiticeiro("Astrid"));
        grupoBanco.commitGrupo(grupo);

        System.out.print("The A-Men id: " + grupoBanco.buscarGrupo(1l, "The A-Men").getId());
        
        Admin admin = new Admin();
        admin.setNomeUsuario("donoDaBola");
        admin.setSenha("senha-forte");
        admin.setNomeExibicao("O Poderoso Admin");
        admin.setClassificacao(5);

        manager.getTransaction().begin();
        if(usuario.getId() == null){
            manager.persist(admin);
        } else {
            manager.merge(admin);
        }
        manager.getTransaction().commit();

        String hello = admin.getClass().getAnnotation(DiscriminatorValue.class).value();
        System.out.print("A consulta retornou: '" + hello + "'!");
        
    }
}
