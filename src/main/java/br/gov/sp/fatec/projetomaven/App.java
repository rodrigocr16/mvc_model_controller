package br.gov.sp.fatec.projetomaven;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import br.gov.sp.fatec.projetomaven.entity.Aluno;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("avaliacao"); // Nome da persistency unit
        EntityManager manager = factory.createEntityManager();
        
        Aluno aluno = new Aluno();
        aluno.setNomeUsuario("aluno");
        aluno.setSenha("senha");
        aluno.setRa(1234567891011L);

        try{
            manager.getTransaction().begin();
            manager.persist(aluno);
            manager.getTransaction().commit();
        }
        catch(Exception e){
            manager.getTransaction().rollback();
        }
        System.out.println(aluno.getId());
        manager.close();
    }
}
