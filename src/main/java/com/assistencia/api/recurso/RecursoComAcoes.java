package com.assistencia.api.recurso;

import com.assistencia.dados.Campos;
import java.util.Map;

/**

 * [LSP/ISP] PATCH /api/{recurso}/{id}/{acao}: transições do processo de negócio (ex.: aprovar orçamento,

 * iniciar ordem de serviço). Devolve o recurso após a ação.

 */
public interface RecursoComAcoes {
    Map<String, Object> executarAcao(Long id, String acao, Campos corpo) throws Exception;
}
