package br.gov.sp.fatec.padroesprojetos.dao;

import br.gov.sp.fatec.padroesprojetos.entity.Feiticeiro;
import br.gov.sp.fatec.padroesprojetos.entity.Usuario;

public interface FeiticeiroDao {
    public Feiticeiro cadastrarFeiticeiro(String nome, String raca, String classe, Usuario proprietario);

    public Feiticeiro buscarFeiticeiro(String nome);

    public Feiticeiro salvarFeiticeiro(Feiticeiro personagem);

    public Feiticeiro commitFeiticeiro(Feiticeiro personagem);
    
    public void removerFeiticeiro(String nome);
}