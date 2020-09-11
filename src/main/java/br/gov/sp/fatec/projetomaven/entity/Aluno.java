package br.gov.sp.fatec.projetomaven.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Table(name = "alu_aluno")
@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alu_id")
    private Long id;
    
    @Column(name = "alu_nome_usuario")
    private String nomeUsuario;

    @Column(name = "alu_senha")
    private String senha;
    
    @Column(name = "alu_ra")
    private Long ra;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getRa() {
        return ra;
    }
    public void setRa(Long ra) {
        this.ra = ra;
    }

    
}