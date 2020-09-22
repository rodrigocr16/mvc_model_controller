package br.gov.sp.fatec.padroesprojetos.entity;

import java.util.Set;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.AttributeOverride;


@Entity
@Table(name = "GRU_GRUPO")
@AttributeOverride(name = "id", column = @Column(name = "GRU_ID"))
public class Grupo extends GeraId {
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GRU_MESTRE")
    private Usuario mestre;

    @Column(name = "GRU_NOME")
    private String nomeGrupo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "INT_INTEGRANTE",
        joinColumns = { @JoinColumn(name = "GRU_ID")},
        inverseJoinColumns = { @JoinColumn(name = "PER_ID")}
    )
    private Set<Personagem> integrantes;


    public Usuario getMestre() {
        return mestre;
    }
    public void setMestre(Usuario mestre) {
        this.mestre = mestre;
    }

    public Set<Personagem> getIntegrantes() {
        return integrantes;
    }
    public void setIntegrantes(Set<Personagem> integrantes) {
        this.integrantes = integrantes;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }
    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }
}