package br.gov.sp.fatec.padroesprojetos.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Inheritance;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.InheritanceType;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "PER_PERSONAGEM")
public class Personagem { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PER_ID")
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USU_ID")
    @Column(name = "PER_PROPRIETARIO")
    private Long proprietario;

    @Column(name = "PER_NOME")
    private String nome;

    @Column(name = "PER_RACA")
    private String raca;

    @Column(name = "PER_CLASSE")
    private String classe;

    public Personagem(Long proprietario, String nome, String raca, String classe){
        this.proprietario = proprietario;
        this.nome = nome;
        this.raca = raca;
        this.classe = classe;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getProprietario() {
        return proprietario;
    }
    public void setProprietario(Long proprietario) {
        this.proprietario = proprietario;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }
    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getClasse() {
        return classe;
    }
    public void setClasse(String classe) {
        this.classe = classe;
    }
}