package br.gov.sp.fatec.padroesprojetos.dao;

import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import br.gov.sp.fatec.padroesprojetos.entity.Usuario;
import br.gov.sp.fatec.padroesprojetos.entity.Feiticeiro;
import br.gov.sp.fatec.padroesprojetos.entity.PersistenceManager;

public class FeiticeiroDaoJpa implements FeiticeiroDao {
    private EntityManager em;
    public FeiticeiroDaoJpa(){
        this(PersistenceManager.getInstance().getEntityManager());
    }
    public FeiticeiroDaoJpa(EntityManager em){
        this.em = em;
    }

    @Override
    public Feiticeiro cadastrarFeiticeiro(String nome, String raca, String classe, Usuario proprietario) {
        Feiticeiro feiticeiro = new Feiticeiro();
        feiticeiro.setNome(nome);
        feiticeiro.setRaca(raca);
        feiticeiro.setClasse(classe);
        feiticeiro.setProprietario(proprietario);
        feiticeiro.setMagiasConhecidas(3);
        feiticeiro.setModificadorHabilidade(5);
        return commitFeiticeiro(feiticeiro);
    }

    @Override
    public Feiticeiro buscarFeiticeiro(String nome) {
        String jpql = "select l from Feiticeiro l where l.nome = :nome";
        TypedQuery<Feiticeiro> query = em.createQuery(jpql, Feiticeiro.class);
        query.setParameter("nome", nome);
        return query.getSingleResult();
    }

    @Override
    public Feiticeiro commitFeiticeiro(Feiticeiro feiticeiro){
        try{
            em.getTransaction().begin();
            salvarFeiticeiro(feiticeiro);
            em.getTransaction().commit();
            return feiticeiro;
        }
        catch(PersistenceException pe){
            pe.printStackTrace();
            em.getTransaction().rollback();
            throw new RuntimeException("Ocorreu um erro ao salvar o personagem '"
                + feiticeiro.getNome() + "': ", pe);
        }
    }
    
    @Override
    public Feiticeiro salvarFeiticeiro(Feiticeiro feiticeiro){
        if(feiticeiro.getId() == null){
            em.persist(feiticeiro);
        } else {
            em.merge(feiticeiro);
        }   return feiticeiro;
    }


    @Override
    public void removerFeiticeiro(String nome) {
        Feiticeiro feiticeiro = buscarFeiticeiro(nome);
        if(feiticeiro == null){
            throw new RuntimeException("O personagem solicitado não foi encontrado.");
        }
        em.getTransaction().begin();
        em.remove(feiticeiro);
        em.getTransaction().commit();
    }    
}