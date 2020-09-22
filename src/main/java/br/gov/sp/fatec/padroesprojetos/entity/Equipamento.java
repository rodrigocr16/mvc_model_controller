package br.gov.sp.fatec.padroesprojetos.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EQU_EQUIPAMENTO")
@AttributeOverride(name = "id", column = @Column(name = "EQU_ID"))
@AttributeOverride(name = "nome", column = @Column(name = "EQU_NOME"))
@AttributeOverride(name = "peso", column = @Column(name = "EQU_PESO"))
@AttributeOverride(name = "preco", column = @Column(name = "EQU_PRECO"))
@AttributeOverride(name = "descricao", column = @Column(name = "EQU_DESCRICAO"))

public class Equipamento extends Item {
    @Column(name = "EQU_CA")
    public Integer classeDeArmadura;

    @Column(name = "EQU_TIPO_ARMADURA")
    public String tipoArmadura;


    public Integer getClasseDeArmadura() {
        return classeDeArmadura;
    }
    public void setClasseDeArmadura(Integer classeDeArmadura) {
        this.classeDeArmadura = classeDeArmadura;
    }

    public String getTipoArmadura() {
        return tipoArmadura;
    }
    public void setTipoArmadura(String tipoArmadura) {
        this.tipoArmadura = tipoArmadura;
    }
}