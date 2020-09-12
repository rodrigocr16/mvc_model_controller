package br.gov.sp.fatec.projetomaven.entity;

import java.util.Set;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
@Table(name = "pro_professor")
@PrimaryKeyJoinColumn(name = "pro_id")
public class Professor extends Usuario{
    @Column(name = "pro_titulo")
    private String titulo;

    /*
        NÃO UTILIZE O FETCHTYPE.EAGER DOS DOIS LADOS DA
        RELAÇÃO, POIS ISTO PODE CAUSAR UM LOOP INFINITO!

        SE NÃO HOUVER CHAVE ESTRANGEIRA NÃO UTILIZE JOIN
        COLUMN, MAS SIM A PROPRIEDADE "MAPPEDBY" EM FETCH
    */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "avaliador")
    private Set<Trabalho> trabalhosAvaliados;

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}