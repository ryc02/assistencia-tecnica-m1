package com.assistencia.api.recurso;

import com.assistencia.command.Visao;
import com.assistencia.dados.*;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.service.*;
import java.util.*;

/** Recurso REST /api/clientes: suporta todas as operações de CRUD. */
public class ClienteRecurso implements RecursoConsultavel, RecursoCriavel, RecursoAtualizavel, RecursoExcluivel {
    private final ClienteService servico;
    private final ConsultaCliente consulta;

    public ClienteRecurso(ClienteService servico, ConsultaCliente consulta) {
        this.servico = servico;
        this.consulta = consulta;
    }

    @Override
    public List<Map<String, Object>> listar() throws Exception {
        Registros<Cliente> clientes = consulta.listarTodos();
        return clientes.exportar();
    }

    @Override
    public Map<String, Object> buscar(Long id) throws Exception {
        Cliente cliente = consulta.buscarPorId(id);
        return Visao.de(cliente);
    }

    @Override
    public Map<String, Object> criar(Campos corpo) throws Exception {
        Cliente criado = servico.cadastrar(corpo);
        return Visao.de(criado);
    }

    @Override
    public void atualizar(Long id, Campos corpo) throws Exception {
        servico.atualizar(id, corpo);
    }

    @Override
    public void excluir(Long id) throws Exception {
        servico.excluir(id);
    }
}
