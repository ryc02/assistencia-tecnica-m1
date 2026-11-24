package com.assistencia.api.recurso;

import com.assistencia.command.Visao;
import com.assistencia.dados.*;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.service.*;
import java.util.*;

/**

 * Recurso REST /api/orcamentos: consulta, cadastro e alteração (aceita "adicionais": [...]). A exclusão e as

 * ações (aprovar/recusar) ficam em AcoesDeOrcamentoRecurso.

 */
public class OrcamentoRecurso implements RecursoConsultavel, RecursoCriavel, RecursoAtualizavel {
    private final OrcamentoService servico;
    private final ConsultaOrcamento consulta;

    public OrcamentoRecurso(OrcamentoService servico, ConsultaOrcamento consulta) {
        this.servico = servico;
        this.consulta = consulta;
    }

    @Override
    public List<Map<String, Object>> listar() throws Exception {
        Registros<Orcamento> orcamentos = consulta.listarTodos();
        return orcamentos.exportar();
    }

    @Override
    public Map<String, Object> buscar(Long id) throws Exception {
        Orcamento orcamento = consulta.buscarPorId(id);
        return Visao.de(orcamento);
    }

    @Override
    public Map<String, Object> criar(Campos corpo) throws Exception {
        Orcamento criado = servico.cadastrar(corpo);
        return Visao.de(criado);
    }

    @Override
    public void atualizar(Long id, Campos corpo) throws Exception {
        servico.atualizar(id, corpo);
    }
}
