package br.gov.sp.fatec.padroesprojetos.entity;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Table(name = "LUT_LUTADOR")
@PrimaryKeyJoinColumn(name = "LUT_ID")
public class Lutador extends Personagem {
    public Lutador(Long proprietario, String nome, String raca, String classe) {
        super(proprietario, nome, raca, classe);        
    }

    @Column(name = "LUT_DADOS_SUPERIORIDADE")
    private Integer dadosSuperioridade;


    public Integer getdadosSuperioridade() {
        return dadosSuperioridade;
    }
    public void setdadosSuperioridade(Integer dadosSuperioridade) {
        this.dadosSuperioridade = dadosSuperioridade;
    }
}