package com.assistencia.api.recurso;

import com.assistencia.command.Visao;
import com.assistencia.dados.*;
import com.assistencia.model.ficha.FichaTecnica;
import com.assistencia.service.*;
import java.util.*;

/** Recurso REST /api/fichas-tecnicas (relacionamento 1:1 com a Ordem de Serviço). */
public class FichaTecnicaRecurso implements RecursoConsultavel, RecursoAtualizavel {
    private final FichaTecnicaService servico;
    private final ConsultaAtendimento consulta;

    public FichaTecnicaRecurso(FichaTecnicaService servico, ConsultaAtendimento consulta) {
        this.servico = servico;
        this.consulta = consulta;
    }

    @Override
    public List<Map<String, Object>> listar() throws Exception {
        Registros<FichaTecnica> fichas = consulta.listarFichas();
        return fichas.exportar();
    }

    @Override
    public Map<String, Object> buscar(Long id) throws Exception {
        FichaTecnica ficha = consulta.ficha(id);
        return Visao.de(ficha);
    }

    @Override
    public void atualizar(Long id, Campos corpo) throws Exception {
        servico.atualizar(id, corpo);
    }
}
