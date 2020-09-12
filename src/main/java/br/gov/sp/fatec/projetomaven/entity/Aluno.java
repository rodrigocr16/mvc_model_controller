package br.gov.sp.fatec.projetomaven.entity;

import java.util.Set;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
@Table(name = "alu_aluno")
@PrimaryKeyJoinColumn(name = "alu_id")
public class Aluno extends Usuario {

    @Column(name = "alu_ra")
    private Long ra;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "alunos")
    private Set<Trabalho> trabalhos;

    public Long getRa() {
        return ra;
    }
    public void setRa(Long ra) {
        this.ra = ra;
    }

    public Set<Trabalho> getTrabalhos() {
        return trabalhos;
    }
    public void setTrabalhos(Set<Trabalho> trabalhos) {
        this.trabalhos = trabalhos;
    }
}