package br.gov.sp.fatec.padroesprojetos.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ARM_ARMA")
@AttributeOverride(name = "id", column = @Column(name = "ARM_ID"))
@AttributeOverride(name = "nome", column = @Column(name = "ARM_NOME"))
@AttributeOverride(name = "peso", column = @Column(name = "ARM_PESO"))
@AttributeOverride(name = "preco", column = @Column(name = "ARM_PRECO"))
@AttributeOverride(name = "descricao", column = @Column(name = "ARM_DESCRICAO"))

public class Arma extends Item {
    @Column(name = "ARM_DANO")
    public Integer dano;

    @Column(name = "ARM_ATRIBUTO_PROFICIENCIA")
    public String atributoProficiencia;


    public Integer getDano() {
        return dano;
    }
    public void setDano(Integer dano) {
        this.dano = dano;
    }

    public String getAtributoProficiencia() {
        return atributoProficiencia;
    }
    public void setAtributoProficiencia(String atributoProficiencia) {
        this.atributoProficiencia = atributoProficiencia;
    }
}