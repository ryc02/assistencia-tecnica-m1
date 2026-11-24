package com.assistencia.api.recurso;

import com.assistencia.command.Visao;
import com.assistencia.dados.*;
import com.assistencia.model.ordem.OrdemServico;
import com.assistencia.service.*;
import java.util.*;

/** Recurso REST /api/ordens-servico. */
public class OrdemServicoRecurso implements RecursoConsultavel, RecursoAtualizavel {
    private final OrdemServicoService servico;
    private final ConsultaAtendimento consulta;

    public OrdemServicoRecurso(OrdemServicoService servico, ConsultaAtendimento consulta) {
        this.servico = servico;
        this.consulta = consulta;
    }

    @Override
    public List<Map<String, Object>> listar() throws Exception {
        Registros<OrdemServico> ordens = consulta.listarOrdens();
        return ordens.exportar();
    }

    @Override
    public Map<String, Object> buscar(Long id) throws Exception {
        OrdemServico ordem = consulta.ordem(id);
        return Visao.de(ordem);
    }

    @Override
    public void atualizar(Long id, Campos corpo) throws Exception {
        servico.reprogramar(id, corpo);
    }
}
