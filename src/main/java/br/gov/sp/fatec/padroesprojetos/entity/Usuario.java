package br.gov.sp.fatec.padroesprojetos.entity;

import java.util.Set;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Table(name = "USU_USUARIO")
public class Usuario { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USU_ID")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "proprietario")
    private Set<Personagem> personagens;
    
    @Column(name = "USU_NOME_USUARIO")
    private String nomeUsuario;

    @Column(name = "USU_SENHA")
    private String senha;

    @Column(name = "USU_NOME_EXIBICAO")
    private String nomeExibicao;


    public Usuario(String nomeUsuario, String senha, String nomeExibicao){
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.nomeExibicao = nomeExibicao;
    }

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

    public String getNomeExibicao() {
        return nomeExibicao;
    }
    public void setNomeExibicao(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }
}