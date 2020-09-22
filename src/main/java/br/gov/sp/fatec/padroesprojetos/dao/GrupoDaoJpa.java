package br.gov.sp.fatec.padroesprojetos.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

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
            throw new RuntimeException("Erro ao salvar grupo: ", pe);
        }
    }

    @Override
    public Grupo buscarGrupo(Long mestreId, String nomeGrupo) {
        String jpql = "SELECT g FROM Grupo g INNER JOIN g.mestre m where m.id = :mestreId and g.nomeGrupo = :nomeGrupo";
        TypedQuery<Grupo> query = (TypedQuery<Grupo>) em.createQuery(jpql, Grupo.class);
        query.setParameter("mestreId", mestreId);
        query.setParameter("nomeGrupo", nomeGrupo);
        return query.getSingleResult();
    }

    /*
    public List<Aluno> executeQuery(String nome, String email) {
        System.out.println("-- executing query ALUNO --");
        String queryText = "select a " +
                "from Aluno a " +
                "inner join a.orientador o where o.nomeUsuario = :nomeUsuario and o.email = :email";
        TypedQuery<Aluno> query = (TypedQuery<Aluno>) manager.createQuery(queryText, Aluno.class);
        query.setParameter("nomeUsuario", nome);
        query.setParameter("email", email);
        List<Aluno> resultado = query.getResultList();
        resultado.forEach(System.out::println);
        return resultado;
    }
    */

    @Override
    public void removerGrupo(Long mestreId, String nomeGrupo) {
        Grupo grupo = buscarGrupo(mestreId, nomeGrupo);
        if(grupo == null){
            throw new RuntimeException("Grupo não cadastrado");
        }
        em.getTransaction().begin();
        em.remove(grupo);
        em.getTransaction().commit();
    }
    
}