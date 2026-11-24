package com.assistencia.factory;

import com.assistencia.dao.*;
import com.assistencia.dao.jdbc.*;
import com.assistencia.infra.*;

/**
 * [Factory - Aula 06 / DIP - Aula 14] Único ponto que conhece as classes concretas de baixo nível (JDBC e
 * transação).
 */
final class Persistencia {
    static final GerenciadorTransacao TRANSACAO = new TransactionManager();
    static final ClienteDAO CLIENTES = new ClienteDAOJDBC();
    static final EquipamentoDAO EQUIPAMENTOS = new EquipamentoDAOJDBC(CLIENTES);
    static final OrcamentoDAO ORCAMENTOS = new OrcamentoDAOJDBC(EQUIPAMENTOS);
    static final OrdemServicoDAO ORDENS = new OrdemServicoDAOJDBC(ORCAMENTOS);
    static final FichaTecnicaDAO FICHAS = new FichaTecnicaDAOJDBC(ORDENS);
    static final UsuarioDAO USUARIOS = new UsuarioDAOJDBC();

    private Persistencia() { }
}
