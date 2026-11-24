package com.assistencia.factory;

import com.assistencia.service.*;
import static com.assistencia.factory.Persistencia.*;

/** [Factory] Monta os Services de aprovação, ordens de serviço e fichas técnicas (DIP). */
public final class FabricaDeAtendimentos {
    private static final RepositorioAprovacao APROVACAO =
            new RepositorioAprovacao(ORCAMENTOS, new RegistroDeAtendimento(ORDENS, FICHAS));
    private static final LeituraDeAtendimento LEITURA = new LeituraDeAtendimento(ORDENS, FICHAS);
    private static final AprovacaoService APROVACAO_SERVICO = new AprovacaoService(APROVACAO, TRANSACAO);
    private static final OrdemServicoService ORDEM = new OrdemServicoService(ORDENS, TRANSACAO);
    private static final FichaTecnicaService FICHA = new FichaTecnicaService(FICHAS, TRANSACAO);
    private static final ConsultaAtendimento CONSULTA = new ConsultaAtendimento(LEITURA, TRANSACAO);
    private static final TelasDeAtendimento TELAS = new TelasDeAtendimento(LEITURA, TRANSACAO);

    private FabricaDeAtendimentos() { }

    public static AprovacaoService aprovacaoService() {
        return APROVACAO_SERVICO;
    }

    public static OrdemServicoService ordemServicoService() {
        return ORDEM;
    }

    public static FichaTecnicaService fichaTecnicaService() {
        return FICHA;
    }

    public static ConsultaAtendimento consultaAtendimento() {
        return CONSULTA;
    }

    public static TelasDeAtendimento telasDeAtendimento() {
        return TELAS;
    }
}
