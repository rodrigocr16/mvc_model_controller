package br.gov.sp.fatec.padroesprojetos.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Administrador")
public class Admin extends Usuario {
    @Column(name = "ADM_CLASSIFICACAO")
    private Integer classificacao;


    public Integer getClassificacao() {
        return classificacao;
    }
    public void setClassificacao(Integer classificacao) {
        this.classificacao = classificacao;
    }
}