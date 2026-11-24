package com.assistencia.factory;

import com.assistencia.command.impl.*;
import static com.assistencia.factory.FabricaDeAtendimentos.*;
import static com.assistencia.factory.FabricaDeOrcamentos.*;

/** Registra os Commands de orçamentos (incluindo a aprovação, que gera a ordem de serviço). */
final class ComandosDeOrcamento {
    private ComandosDeOrcamento() { }

    static void registrar(RegistroDeComandos registro) {
        registro.registrar("orcamento.listar", new OrcamentoListarCommand(telasDeOrcamento()));
        registro.registrar("orcamento.consultar",
                new OrcamentoConsultarCommand(telasDeOrcamento(), telasDeAtendimento()));
        registro.registrar("orcamento.inserir", new OrcamentoInserirCommand(orcamentoService()));
        registro.registrar("orcamento.atualizar", new OrcamentoAtualizarCommand(orcamentoService()));
        registro.registrar("orcamento.excluir", new OrcamentoExcluirCommand(gestaoOrcamento()));
        registro.registrar("orcamento.recusar", new OrcamentoRecusarCommand(gestaoOrcamento()));
        registro.registrar("orcamento.aprovar", new OrcamentoAprovarCommand(aprovacaoService()));
    }
}
