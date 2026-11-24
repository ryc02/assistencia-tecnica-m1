package com.assistencia.service;

import com.assistencia.dados.Campos;
import com.assistencia.dao.OrdemServicoDAO;
import com.assistencia.infra.GerenciadorTransacao;
import com.assistencia.model.ordem.OrdemServico;
import java.sql.Connection;
import java.util.function.Consumer;
import static com.assistencia.model.ordem.MudancaDeOrdem.*;

/** [RF06] Alteração e transições de estado da ordem de serviço (iniciar, concluir, cancelar). */
public class OrdemServicoService {
    private final OrdemServicoDAO ordens;
    private final GerenciadorTransacao transacao;

    public OrdemServicoService(OrdemServicoDAO ordens, GerenciadorTransacao transacao) {
        this.ordens = ordens;
        this.transacao = transacao;
    }

    public OrdemServico reprogramar(Long id, Campos dados) throws Exception {
        return aplicar(id, ordem -> ordem.reprogramar(dados));
    }

    public OrdemServico iniciar(Long id) throws Exception {
        return aplicar(id, ordem -> ordem.mudar(INICIAR));
    }

    public OrdemServico concluir(Long id) throws Exception {
        return aplicar(id, ordem -> ordem.mudar(CONCLUIR));
    }

    public OrdemServico cancelar(Long id) throws Exception {
        return aplicar(id, ordem -> ordem.mudar(CANCELAR));
    }

    private OrdemServico aplicar(Long id, Consumer<OrdemServico> mudanca) throws Exception {
        return transacao.executar(conexao -> aplicar(conexao, id, mudanca));
    }

    private OrdemServico aplicar(Connection conexao, Long id, Consumer<OrdemServico> mudanca) throws Exception {
        OrdemServico ordem = OrdemExistente.paraAtualizar(ordens, conexao, id);
        mudanca.accept(ordem);
        ordens.atualizar(conexao, ordem);
        return ordem;
    }
}
