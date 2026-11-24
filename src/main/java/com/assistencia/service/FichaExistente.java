package com.assistencia.service;

import com.assistencia.dao.FichaTecnicaDAO;
import com.assistencia.model.ficha.FichaTecnica;
import java.sql.Connection;

/** Busca uma ficha técnica que precisa existir (id obrigatório e 404 quando não encontrada). */
final class FichaExistente {
    private FichaExistente() { }

    static FichaTecnica buscar(FichaTecnicaDAO fichas, Connection conexao, Long id) throws Exception {
        Existencia.exigirId(id, "ficha técnica");
        FichaTecnica ficha = fichas.buscarPorId(conexao, id);
        return Existencia.exigir(ficha, "Ficha Técnica não encontrada.");
    }
}
