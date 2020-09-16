package br.gov.sp.fatec.padroesprojetos;

import br.gov.sp.fatec.padroesprojetos.dao.UsuarioDaoJpa;
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
        

        UsuarioDaoJpa usuarioDaoJpa = new UsuarioDaoJpa(manager);
        usuarioDaoJpa.cadastrarUsuario("MelhorPinto", "DonaPinta123", "Robertinho Sensato");

                
        
    }
}
