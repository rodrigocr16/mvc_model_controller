package br.gov.sp.fatec.padroesprojetos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "ITE_ITEM")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Item extends GeraId {

    @Column(name = "ITE_NOME")
    public String nome;

    @Column(name = "ITE_PESO")
    public Float peso;

    @Column(name = "ITE_PRECO")
    public Float preco;

    @Column(name = "ITE_DESCRICAO")
    public String descricao;


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPeso() {
        return peso;
    }
    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getPreco() {
        return preco;
    }
    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}