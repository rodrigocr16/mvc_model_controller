package br.gov.sp.fatec.padroesprojetos.dao;

import br.gov.sp.fatec.padroesprojetos.entity.Grupo;

public interface GrupoDao {
    public Grupo commitGrupo(Grupo grupo);

    public Grupo buscarGrupo(Long id);

    public void removerGrupo(Long id);
}