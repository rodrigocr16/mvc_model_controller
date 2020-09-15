package br.gov.sp.fatec.padroesprojetos;

import br.gov.sp.fatec.padroesprojetos.entity.Usuario;
import javax.persistence.PersistenceException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;


public class App 
{
    public static void novoUsuario(String nomeUsuario, String senha, String nomeExibicao){
        EntityManagerFactory factory = Persistence
            .createEntityManagerFactory("padroesprojetos");
        EntityManager manager = factory.createEntityManager();
        
        Usuario novoUsuario = new Usuario(nomeUsuario, senha, nomeExibicao);
        try{
            manager.getTransaction().begin();
            manager.persist(novoUsuario);
            manager.getTransaction().commit();
        }
        catch(PersistenceException excep){
            excep.printStackTrace();
            manager.getTransaction().rollback();
        }
        manager.close();
    }

    public static void main( String[] args )
    {   // YOUR CODE HERE, DUMBASS V
        novoUsuario("Robertinho_Sensato", "91N740", "Robertinho");
        
    }
}
