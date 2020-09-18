package br.gov.sp.fatec.padroesprojetos.dao;

import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import br.gov.sp.fatec.padroesprojetos.entity.Usuario;
import br.gov.sp.fatec.padroesprojetos.entity.Lutador;
import br.gov.sp.fatec.padroesprojetos.entity.PersistenceManager;

public class LutadorDaoJpa implements LutadorDao {
    private EntityManager em;

    public LutadorDaoJpa(){
        this(PersistenceManager.getInstance().getEntityManager());
    }

    public LutadorDaoJpa(EntityManager em){
        this.em = em;
    }

    @Override
    public Lutador cadastrarLutador(String nome, String raca, String classe, Usuario proprietario) {
        Lutador lutador = new Lutador();
        lutador.setNome(nome);
        lutador.setRaca(raca);
        lutador.setClasse(classe);
        lutador.setProprietario(proprietario);
        lutador.setdadosSuperioridade(3);
        return commitLutador(lutador);
    }

    @Override
    public Lutador buscarLutador(String nome) {
        String jpql = "select l from Lutador l where l.nome = :nome";
        TypedQuery<Lutador> query = em.createQuery(jpql, Lutador.class);
        query.setParameter("nome", nome);
        return query.getSingleResult();
    }

    @Override
    public Lutador commitLutador(Lutador lutador){
        try{
            em.getTransaction().begin();
            salvarLutador(lutador);
            em.getTransaction().commit();
            return lutador;
        }
        catch(PersistenceException pe){
            pe.printStackTrace();
            em.getTransaction().rollback();
            throw new RuntimeException("Ocorreu um erro ao salvar o personagem: ", pe);
        }
    }
    
    @Override
    public Lutador salvarLutador(Lutador lutador){
        if(lutador.getId() == null){
            em.persist(lutador);
        } else {
            em.merge(lutador);
        }   return lutador;
    }


    @Override
    public void removerLutador(String nome) {
        Lutador lutador = buscarLutador(nome);
        if(lutador == null){
            throw new RuntimeException("O personagem solicitado não foi encontrado.");
        }
        em.getTransaction().begin();
        em.remove(lutador);
        em.getTransaction().commit();
    }    
}