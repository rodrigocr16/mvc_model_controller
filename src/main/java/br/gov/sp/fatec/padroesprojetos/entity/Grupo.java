package br.gov.sp.fatec.padroesprojetos.entity;

import java.util.Set;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Table(name = "GRU_GRUPO")
@Entity
public class Grupo {
    
    @Column(name = "GRU_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GRU_MESTRE")
    private Usuario mestre;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "INT_INTEGRANTES",
        joinColumns = { @JoinColumn(name = "GRU_ID")},
        inverseJoinColumns = { @JoinColumn(name = "PER_ID")}
    )
    private Set<Personagem> integrantes;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

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
}