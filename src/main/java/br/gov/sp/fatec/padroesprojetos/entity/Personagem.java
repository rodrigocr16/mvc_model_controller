package br.gov.sp.fatec.padroesprojetos.entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Table(name = "PER_PERSONAGEM")
public class Personagem { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PER_ID")
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USU_ID")
    @Column(name = "PER_USUARIO")
    private Long idUsuario;

    @Column(name = "PER_NOME")
    private String nome;

    @Column(name = "USU_RACA")
    private String raca;

    @Column(name = "USU_CLASSE")
    private String classe;

    public Personagem(Long idUsuario, String nome, String raca, String classe){
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.raca = raca;
        this.classe = classe;
    }

    
}