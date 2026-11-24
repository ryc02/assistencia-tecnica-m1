package com.assistencia.unitario;

import com.assistencia.infra.GerenciadorTransacao;

/**

 * Implementação de teste da abstração GerenciadorTransacao (DIP): executa a ação diretamente, sem abrir conexão

 * com o banco de dados.

 */
public class TransacaoFake implements GerenciadorTransacao {
    @Override
    public <T> T executar(Acao<T> acao) throws Exception {
        return acao.executar(null);
    }
}
