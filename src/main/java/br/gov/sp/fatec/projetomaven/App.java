package br.gov.sp.fatec.projetomaven;

import java.util.Set;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManagerFactory;
import br.gov.sp.fatec.projetomaven.entity.Aluno;
import br.gov.sp.fatec.projetomaven.entity.Trabalho;
import br.gov.sp.fatec.projetomaven.entity.Professor;

public class App 
{
    public static void main( String[] args )
    {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("projeto_maven"); // Nome da persistency unit
        EntityManager manager = factory.createEntityManager();
        
        Aluno aluno = new Aluno();
        aluno.setNomeUsuario("alunotemp");
        aluno.setSenha("senha");
        aluno.setRa(1234567891013L);

        Professor professor = new Professor();
        professor.setNomeUsuario("professortemp");
        professor.setSenha("senhaF0rte");

        Trabalho trabalho = new Trabalho();
        trabalho.setTitulo("trabalhotemp");
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
       
        // FAZ UMA BUSCA E RETORNA OS RESULTADOS
        String aux = "select t from Trabalho t where t.titulo like :titulo";
        TypedQuery<Trabalho> query = manager.createQuery(aux, Trabalho.class);
        query.setParameter("titulo", "%JPA%");

        List<Trabalho> resultados = query.getResultList();
        for(Trabalho trab: resultados){
            System.out.println("Título: " + trab.getTitulo());
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

        // REMOVE OS REGISTROS, PERMITINDO RE-EXECUÇÃO
        try{
            manager.getTransaction().begin();
            professor = trabalho.getAvaliador();
            trabalho.setAvaliador(null);
            Set<Aluno> alunos = trabalho.getAlunos();
            trabalho.setAlunos(null);
            manager.remove(trabalho);
            manager.remove(professor);
            for(Aluno alu: alunos){
                manager.remove(alu);
            }
            manager.getTransaction().commit();
        }
        catch(PersistenceException e){
            e.printStackTrace();
            manager.getTransaction().rollback();
        }

        manager.close();
    }
}
