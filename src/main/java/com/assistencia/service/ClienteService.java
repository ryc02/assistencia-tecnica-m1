package com.assistencia.service;

import com.assistencia.builder.ClienteBuilder;
import com.assistencia.dao.ClienteDAO;
import com.assistencia.dao.EquipamentoDAO;
import com.assistencia.dao.jdbc.ClienteDAOJDBC;
import com.assistencia.dao.jdbc.EquipamentoDAOJDBC;
import com.assistencia.exception.ConflictException;
import com.assistencia.exception.NotFoundException;
import com.assistencia.exception.ValidationException;
import com.assistencia.infra.TransactionManager;
import com.assistencia.model.Cliente;
import com.assistencia.model.Equipamento;

import java.util.List;

/**
 * [Requisito 4: RF01 Gerenciar Clientes]
 * Regras de negócio do cadastro de clientes, validação de CPF único e integridade de dependentes.
 */
public class ClienteService {

    private final ClienteDAO clienteDAO = new ClienteDAOJDBC();
    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAOJDBC();

    public Cliente cadastrar(String nome, String cpf, String email, String telefone,
                              String logradouro, String numero, String bairro, String cidade) throws Exception {
        Cliente cliente = new ClienteBuilder()
                .nome(nome)
                .cpf(cpf)
                .email(email)
                .telefone(telefone)
                .logradouro(logradouro)
                .numero(numero)
                .bairro(bairro)
                .cidade(cidade)
                .build();

        return TransactionManager.executeInTransaction(conn -> {
            // [RF01] Validação de CPF único
            Cliente existente = clienteDAO.buscarPorCpf(conn, cliente.getCpf());
            if (existente != null) {
                throw new ConflictException("Já existe um cliente cadastrado com o CPF informados.");
            }
            return clienteDAO.inserir(conn, cliente);
        });
    }

    public void atualizar(Long id, String nome, String cpf, String email, String telefone,
                          String logradouro, String numero, String bairro, String cidade) throws Exception {
        if (id == null) {
            throw new ValidationException("ID do cliente é obrigatório para atualização.");
        }

        Cliente cliente = new ClienteBuilder()
                .id(id)
                .nome(nome)
                .cpf(cpf)
                .email(email)
                .telefone(telefone)
                .logradouro(logradouro)
                .numero(numero)
                .bairro(bairro)
                .cidade(cidade)
                .build();

        TransactionManager.executeInTransaction(conn -> {
            Cliente existente = clienteDAO.buscarPorId(conn, id);
            if (existente == null) {
                throw new NotFoundException("Cliente não encontrado com ID: " + id);
            }

            // [RF01] Validação de CPF único ao alterar
            Cliente outroCpf = clienteDAO.buscarPorCpf(conn, cliente.getCpf());
            if (outroCpf != null && !outroCpf.getId().equals(id)) {
                throw new ConflictException("O CPF informado já pertence a outro cliente.");
            }

            cliente.setDataCadastro(existente.getDataCadastro());
            clienteDAO.atualizar(conn, cliente);
            return null;
        });
    }

    public void excluir(Long id) throws Exception {
        if (id == null) {
            throw new ValidationException("ID do cliente é obrigatório.");
        }

        TransactionManager.executeInTransaction(conn -> {
            Cliente c = clienteDAO.buscarPorId(conn, id);
            if (c == null) {
                throw new NotFoundException("Cliente não encontrado.");
            }

            // [Requisito 6 / T15] Restrição de Chave Estrangeira: impede exclusão de cliente com equipamentos
            List<Equipamento> equipamentos = equipamentoDAO.listarPorCliente(conn, id);
            if (!equipamentos.isEmpty()) {
                throw new ConflictException("Não é possível excluir o cliente pois ele possui " + equipamentos.size() + " equipamento(s) cadastrado(s).");
            }

            clienteDAO.excluir(conn, id);
            return null;
        });
    }

    public Cliente buscarPorId(Long id) throws Exception {
        if (id == null) throw new ValidationException("ID é obrigatório.");
        return TransactionManager.executeInTransaction(conn -> {
            Cliente c = clienteDAO.buscarPorId(conn, id);
            if (c == null) throw new NotFoundException("Cliente não encontrado com ID: " + id);
            return c;
        });
    }

    public List<Cliente> listarTodos() throws Exception {
        return TransactionManager.executeInTransaction(clienteDAO::listarTodos);
    }
}
