package br.gov.sp.fatec.projetomaven;

import java.util.Date;
import java.util.HashSet;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.EntityManagerFactory;
import br.gov.sp.fatec.projetomaven.entity.Aluno;
import br.gov.sp.fatec.projetomaven.entity.Professor;
import br.gov.sp.fatec.projetomaven.entity.Trabalho;

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
        aluno.setNomeUsuario("alunobd");
        aluno.setSenha("senha");
        aluno.setRa(1234567891013L);

        Professor professor = new Professor();
        professor.setNomeUsuario("proflabiv");
        professor.setSenha("senhaF0rte");

        Trabalho trabalho = new Trabalho();
        trabalho.setTitulo("Trabalho 2 JPA");
        trabalho.setDataHoraEntrega(new Date());
        trabalho.setLocalArquivo("trabalhos");
        trabalho.setAvaliador(professor);
        trabalho.setAlunos(new HashSet<Aluno>());
        trabalho.getAlunos().add(aluno);

        try{
            manager.getTransaction().begin();
            manager.persist(aluno);
            manager.persist(professor);
            manager.persist(trabalho);
            manager.getTransaction().commit();
        }
        catch(PersistenceException e){
            e.printStackTrace();
            manager.getTransaction().rollback();
        }
       
        manager.clear();

        aluno = manager.find(Aluno.class, aluno.getId());
        System.out.println(aluno.getId());
        System.out.println(aluno.getNomeUsuario());
        for(Trabalho tra: aluno.getTrabalhos()){
            System.out.println(tra.getTitulo());
        }

        manager.clear();
        
        trabalho = manager.find(Trabalho.class, trabalho.getId());
        System.out.println(trabalho.getTitulo());
        for(Aluno alu: trabalho.getAlunos()){
            System.out.println(alu.getNomeUsuario());
        }

        manager.close();
        
    }
}
