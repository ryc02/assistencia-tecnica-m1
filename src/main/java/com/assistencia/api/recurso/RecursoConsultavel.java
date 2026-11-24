package com.assistencia.api.recurso;

import java.util.*;

/**

 * [LSP/ISP - Aula 11] Contrato de leitura: GET /api/{recurso} e GET /api/{recurso}/{id}. As capacidades foram

 * separadas em interfaces pequenas para que nenhum recurso precise "herdar" uma operação que não suporta (como

 * o ProdutoDigital herdando calcularFrete).

 */
public interface RecursoConsultavel {
    List<Map<String, Object>> listar() throws Exception;

    Map<String, Object> buscar(Long id) throws Exception;
}
