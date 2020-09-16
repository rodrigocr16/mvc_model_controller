package br.gov.sp.fatec.padroesprojetos.entity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/*
    SINGLETON - UMA CLASSE ONDE DENTRO DELA HÁ UM ATRIBUTO
    DESTA CLASSE. ESTE ATRIBUTO É PRIVADO E ESTÁTICO (PER-
    TENCENTE À CLASSE, PODENDO SER ALTERADO SEM QUE HAJA
    INSTANCIAMENTO, E QUE POSSUI CONSTRUTOR PRIVADO)
*/

public class PersistenceManager {
    private static PersistenceManager instance;
    private PersistenceManager(){}
    
    protected EntityManagerFactory emf;

    public static PersistenceManager getInstance(){
        if(instance == null){
            instance = new PersistenceManager();
        }   return instance;
    }

    public EntityManagerFactory getEntityManagerFactory(){
        if(emf == null){
            createEntityManagerFactory();
        }   return emf;
    }

    public EntityManager getEntityManager(){
        if(emf == null){
            createEntityManagerFactory();
        }   return emf.createEntityManager();
    }

    private void createEntityManagerFactory(){
        emf = Persistence.createEntityManagerFactory("padroesprojetos");
    }
}