package com.assistencia.api.recurso;

import com.assistencia.command.Visao;
import com.assistencia.dados.*;
import com.assistencia.model.equipamento.Equipamento;
import com.assistencia.service.*;
import java.util.*;

/** Recurso REST /api/equipamentos: CRUD (relacionamento Cliente 1:N Equipamento). */
public class EquipamentoRecurso implements RecursoConsultavel, RecursoCriavel, RecursoAtualizavel, RecursoExcluivel {
    private final EquipamentoService servico;
    private final ConsultaEquipamento consulta;

    public EquipamentoRecurso(EquipamentoService servico, ConsultaEquipamento consulta) {
        this.servico = servico;
        this.consulta = consulta;
    }

    @Override
    public List<Map<String, Object>> listar() throws Exception {
        Registros<Equipamento> equipamentos = consulta.listarTodos();
        return equipamentos.exportar();
    }

    @Override
    public Map<String, Object> buscar(Long id) throws Exception {
        Equipamento equipamento = consulta.buscarPorId(id);
        return Visao.de(equipamento);
    }

    @Override
    public Map<String, Object> criar(Campos corpo) throws Exception {
        Equipamento criado = servico.cadastrar(corpo);
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
