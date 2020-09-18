package br.gov.sp.fatec.padroesprojetos.dao;

import br.gov.sp.fatec.padroesprojetos.entity.Lutador;
import br.gov.sp.fatec.padroesprojetos.entity.Usuario;

public interface LutadorDao {
    public Lutador cadastrarLutador(String nome, String raca, String classe, Usuario proprietario);

    public Lutador buscarLutador(String nome);

    public Lutador salvarLutador(Lutador personagem);

    public Lutador commitLutador(Lutador personagem);
    
    public void removerLutador(String nome);
}