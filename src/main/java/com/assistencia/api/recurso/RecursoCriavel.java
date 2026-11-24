package com.assistencia.api.recurso;

import com.assistencia.dados.Campos;
import java.util.Map;

/** [LSP/ISP] POST /api/{recurso}: devolve o recurso criado (HTTP 201). */
public interface RecursoCriavel {
    Map<String, Object> criar(Campos corpo) throws Exception;
}
