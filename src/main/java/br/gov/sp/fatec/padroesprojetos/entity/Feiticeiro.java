package br.gov.sp.fatec.padroesprojetos.entity;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Table(name = "FEI_FEITICEIRO")
@PrimaryKeyJoinColumn(name = "FEI_ID")
public class Feiticeiro extends Personagem {
    public Feiticeiro(Long proprietario, String nome, String raca, String classe) {
        super(proprietario, nome, raca, classe);        
    }

    @Column(name = "FEI_MAGIAS_CONHECIDAS")
    private Integer magiasConhecidas;

    @Column(name = "FEI_MODIFICADOR_HABILIDADE")
    private Integer modificadorHabilidade;


    public Integer getMagiasConhecidas() {
        return magiasConhecidas;
    }
    public void setMagiasConhecidas(Integer magiasConhecidas) {
        this.magiasConhecidas = magiasConhecidas;
    }

    public Integer getModificadorHabilidade() {
        return modificadorHabilidade;
    }
    public void setModificadorHabilidade(Integer modificadorHabilidade) {
        this.modificadorHabilidade = modificadorHabilidade;
    } 
}