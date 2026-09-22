package com.assistencia.service;

import com.assistencia.builder.EquipamentoBuilder;
import com.assistencia.dao.ClienteDAO;
import com.assistencia.dao.EquipamentoDAO;
import com.assistencia.dao.OrcamentoDAO;
import com.assistencia.dao.jdbc.ClienteDAOJDBC;
import com.assistencia.dao.jdbc.EquipamentoDAOJDBC;
import com.assistencia.dao.jdbc.OrcamentoDAOJDBC;
import com.assistencia.exception.ConflictException;
import com.assistencia.exception.NotFoundException;
import com.assistencia.exception.ValidationException;
import com.assistencia.infra.TransactionManager;
import com.assistencia.model.Cliente;
import com.assistencia.model.Equipamento;
import com.assistencia.model.Orcamento;

import java.util.List;

/**
 * [Requisito 4: RF02 Gerenciar Equipamentos]
 * Regras de negócio do cadastro de equipamentos e integridade com Cliente e Orçamentos.
 */
public class EquipamentoService {

    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAOJDBC();
    private final ClienteDAO clienteDAO = new ClienteDAOJDBC();
    private final OrcamentoDAO orcamentoDAO = new OrcamentoDAOJDBC();

    public Equipamento cadastrar(Long clienteId, String tipo, String marca, String modelo,
                                  String numeroSerie, String cor, String voltagem, String descricao) throws Exception {
        return TransactionManager.executeInTransaction(conn -> {
            // [RF02] Exige um cliente existente
            Cliente cliente = clienteDAO.buscarPorId(conn, clienteId);
            if (cliente == null) {
                throw new NotFoundException("Cliente não encontrado com ID: " + clienteId);
            }

            Equipamento eq = new EquipamentoBuilder()
                    .cliente(cliente)
                    .tipo(tipo)
                    .marca(marca)
                    .modelo(modelo)
                    .numeroSerie(numeroSerie)
                    .cor(cor)
                    .voltagem(voltagem)
                    .descricao(descricao)
                    .build();

            return equipamentoDAO.inserir(conn, eq);
        });
    }

    public void atualizar(Long id, Long clienteId, String tipo, String marca, String modelo,
                          String numeroSerie, String cor, String voltagem, String descricao) throws Exception {
        if (id == null) {
            throw new ValidationException("ID do equipamento é obrigatório.");
        }

        TransactionManager.executeInTransaction(conn -> {
            Equipamento existente = equipamentoDAO.buscarPorId(conn, id);
            if (existente == null) {
                throw new NotFoundException("Equipamento não encontrado.");
            }

            Cliente cliente = clienteDAO.buscarPorId(conn, clienteId);
            if (cliente == null) {
                throw new NotFoundException("Cliente não encontrado.");
            }

            // [Requisito 6] Não permitir alteração de cliente do equipamento quando este já possuir orçamento
            List<Orcamento> orcamentos = orcamentoDAO.listarPorEquipamento(conn, id);
            if (!orcamentos.isEmpty() && !existente.getCliente().getId().equals(clienteId)) {
                throw new ConflictException("Não é permitido alterar o cliente proprietário do equipamento pois ele já possui orçamento(s) cadastrado(s).");
            }

            Equipamento eq = new EquipamentoBuilder()
                    .id(id)
                    .cliente(cliente)
                    .tipo(tipo)
                    .marca(marca)
                    .modelo(modelo)
                    .numeroSerie(numeroSerie)
                    .cor(cor)
                    .voltagem(voltagem)
                    .descricao(descricao)
                    .dataCadastro(existente.getDataCadastro())
                    .build();

            equipamentoDAO.atualizar(conn, eq);
            return null;
        });
    }

    public void excluir(Long id) throws Exception {
        if (id == null) throw new ValidationException("ID é obrigatório.");

        TransactionManager.executeInTransaction(conn -> {
            Equipamento eq = equipamentoDAO.buscarPorId(conn, id);
            if (eq == null) throw new NotFoundException("Equipamento não encontrado.");

            // [Requisito 6] Chave estrangeira impede exclusão de equipamento com orçamentos
            List<Orcamento> orcamentos = orcamentoDAO.listarPorEquipamento(conn, id);
            if (!orcamentos.isEmpty()) {
                throw new ConflictException("Não é possível excluir o equipamento pois ele possui " + orcamentos.size() + " orçamento(s) associado(s).");
            }

            equipamentoDAO.excluir(conn, id);
            return null;
        });
    }

    public Equipamento buscarPorId(Long id) throws Exception {
        if (id == null) throw new ValidationException("ID é obrigatório.");
        return TransactionManager.executeInTransaction(conn -> {
            Equipamento eq = equipamentoDAO.buscarPorId(conn, id);
            if (eq == null) throw new NotFoundException("Equipamento não encontrado com ID: " + id);
            return eq;
        });
    }

    public List<Equipamento> listarTodos() throws Exception {
        return TransactionManager.executeInTransaction(equipamentoDAO::listarTodos);
    }

    public List<Equipamento> listarPorCliente(Long clienteId) throws Exception {
        return TransactionManager.executeInTransaction(conn -> equipamentoDAO.listarPorCliente(conn, clienteId));
    }
}
