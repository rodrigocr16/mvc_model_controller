package br.gov.sp.fatec.padroesprojetos.entity;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.AttributeOverride;


@Entity
@Table(name = "PER_PERSONAGEM")
@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "PER_ID"))
public class Personagem extends GeraId { 

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PER_PROPRIETARIO")
    private Usuario proprietario;

    @Column(name = "PER_NOME")
    private String nome;

    @Column(name = "PER_RACA")
    private String raca;

    @Column(name = "PER_CLASSE")
    private String classe;


    public Usuario getProprietario() {
        return proprietario;
    }
    public void setProprietario(Usuario proprietario) {
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