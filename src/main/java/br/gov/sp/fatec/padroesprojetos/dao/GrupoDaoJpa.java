package br.gov.sp.fatec.padroesprojetos.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import br.gov.sp.fatec.padroesprojetos.entity.Grupo;
import br.gov.sp.fatec.padroesprojetos.entity.PersistenceManager;

public class GrupoDaoJpa implements GrupoDao {
    private EntityManager em;
    public GrupoDaoJpa(){
        this(PersistenceManager.getInstance().getEntityManager());
    }
    public GrupoDaoJpa(EntityManager em){
        this.em = em;
    }

    @Override
    public Grupo commitGrupo(Grupo grupo) {
        try{
            em.getTransaction().begin();
            if(grupo.getMestre() != null && grupo.getMestre().getId() == null){
                UsuarioDao usuarioBanco = new UsuarioDaoJpa(em);
                usuarioBanco.commitUsuario(grupo.getMestre());
            }
            if(grupo.getId() == null){
                em.persist(grupo);
            } else {
                em.merge(grupo);
            }
            em.getTransaction().commit();
            return grupo;
        }
        catch(PersistenceException pe){
            pe.printStackTrace();
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar trabalho: ", pe);
        }
    }

    @Override
    public Grupo buscarGrupo(Long id) {
        return em.find(Grupo.class, id);
    }

    @Override
    public void removerGrupo(Long id) {
        Grupo grupo = buscarGrupo(id);
        if(grupo == null){
            throw new RuntimeException("Grupo não cadastrado");
        }
        em.getTransaction().begin();
        em.remove(grupo);
        em.getTransaction().commit();
    }
    
}