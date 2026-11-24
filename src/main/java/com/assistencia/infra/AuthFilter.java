package com.assistencia.infra;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

/** [Segurança] Filtro de autenticação e controle de acesso por cargo, para telas e API REST. */
@WebFilter("/*")
public class AuthFilter implements Filter {
    /** Antes de qualquer getParameter: depois de lido, o corpo do POST não muda mais de codificação. */
    private static final String UTF_8 = "UTF-8";

    @Override
    public void doFilter(ServletRequest requisicaoRecebida, ServletResponse respostaRecebida, FilterChain cadeia)
            throws IOException, ServletException {
        HttpServletRequest requisicao = (HttpServletRequest) requisicaoRecebida;
        HttpServletResponse resposta = (HttpServletResponse) respostaRecebida;
        requisicao.setCharacterEncoding(UTF_8);
        CaminhoDaRequisicao caminho = CaminhoDaRequisicao.de(requisicao);
        if (caminho.estatico()) {
            cadeia.doFilter(requisicao, resposta);
            return;
        }
        if (caminho.daApi()) {
            String recurso = caminho.recursoDaApi();
            FiltroDeApi.filtrar(requisicao, resposta, cadeia, recurso);
            return;
        }
        FiltroDeTelas.filtrar(requisicao, resposta, cadeia);
    }
}
