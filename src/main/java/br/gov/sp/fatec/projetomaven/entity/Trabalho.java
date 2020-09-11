package br.gov.sp.fatec.projetomaven.entity;

import java.util.Set;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Table(name = "tra_trabalho")
@Entity
public class Trabalho {
    
    @Column(name = "tra_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tra_titulo")
    private String titulo;

    @Column(name = "tra_data_hora_entrega")
    private Date dataHoraEntrega;

    @Column(name = "tra_local_arquivo")
    private String localArquivo;

    /*
        ISTO É UM OBJETO, NÃO É MAPEADO DIRETAMENTE, DEVE-SE
        USAR COMANDOS QUE FAÇAM A JUNÇÃO DOS OBJETOS! O FETCH
        TYPE PODE SER EAGER OU LAZY, COM NOMES INTUITIVOS!
    */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pro_avaliador_id")
    private Professor avaliador;

    /*
        ABAIXO TEMOS UMA LIGAÇÃO FEITA POR TABELA RELACIONAL
        ONDE UTILIZAMOS A FUNÇÃO DE JOIN TABLE:
    */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ent_entrega",
        joinColumns = { @JoinColumn(name = "tra_id")},
        inverseJoinColumns = { @JoinColumn(name = "alu_id")}
    )
    private Set<Aluno> alunos;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDataHoraEntrega() {
        return dataHoraEntrega;
    }
    public void setDataHoraEntrega(Date dataHoraEntrega) {
        this.dataHoraEntrega = dataHoraEntrega;
    }

    public String getLocalArquivo() {
        return localArquivo;
    }
    public void setLocalArquivo(String localArquivo) {
        this.localArquivo = localArquivo;
    }

    public Professor getAvaliador() {
        return avaliador;
    }
    public void setAvaliador(Professor avaliador) {
        this.avaliador = avaliador;
    }

    public Set<Aluno> getAlunos() {
        return alunos;
    }
    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }
}