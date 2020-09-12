package br.gov.sp.fatec.projetomaven.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.InheritanceType;


@Entity
@Table(name = "usu_usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_id")
    private Long id;
    
    @Column(name = "usu_nome_usuario")
    private String nomeUsuario;

    @Column(name = "usu_senha")
    private String senha;


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
}