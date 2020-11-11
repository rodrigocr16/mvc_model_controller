package br.gov.sp.fatec.padroesprojetos.dao;

import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import br.gov.sp.fatec.padroesprojetos.entity.Usuario;
import br.gov.sp.fatec.padroesprojetos.entity.Admin;
import br.gov.sp.fatec.padroesprojetos.entity.PersistenceManager;

public class UsuarioDaoJpa implements UsuarioDao {
    private EntityManager em;
    public UsuarioDaoJpa(){
        this(PersistenceManager.getInstance().getEntityManager());
    }
    public UsuarioDaoJpa(EntityManager em){
        this.em = em;
    }

    @Override
    public Usuario cadastrarUsuario(String nomeUsuario, String senha, String nomeExibicao) {
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(nomeUsuario);
        usuario.setSenha(senha);
        usuario.setNomeExibicao(nomeExibicao);
        return commitUsuario(usuario);
    }

    @Override
    public Usuario buscarUsuario(String nomeUsuario) {
        String jpql = "select u from Usuario u where u.nomeUsuario = :nomeUsuario";
        TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
        query.setParameter("nomeUsuario", nomeUsuario);
        return query.getSingleResult();
    }

    @Override
    public Usuario commitUsuario(Usuario usuario){
        try{
            em.getTransaction().begin();
            salvarUsuario(usuario);
            em.getTransaction().commit();
            return usuario;
        }
        catch(PersistenceException pe){
            pe.printStackTrace();
            em.getTransaction().rollback();
            throw new RuntimeException("Ocorreu um erro ao salvar o usuário: ", pe);
        }
    }
    
    @Override
    public Usuario salvarUsuario(Usuario usuario){
        if(usuario.getId() == null){
            em.persist(usuario);
        } else {
            em.merge(usuario);
        }   return usuario;
    }


    @Override
    public void removerUsuario(String nomeUsuario) {
        Usuario usuario = buscarUsuario(nomeUsuario);
        if(usuario == null){
            throw new RuntimeException("O usuário solicitado não foi encontrado.");
        }
        em.getTransaction().begin();
        em.remove(usuario);
        em.getTransaction().commit();
    }

    @Override
    public String getClearance(String nomeUsuario) {
        Usuario usuario = buscarUsuario(nomeUsuario);
        String clearance = new String();

        if(usuario instanceof Admin){
            clearance = "Administrador";
        } else {
            clearance = "Usuario";
        }
        
        return clearance;
    }
}
