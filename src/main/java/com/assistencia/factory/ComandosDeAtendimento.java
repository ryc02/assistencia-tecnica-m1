package com.assistencia.factory;

import com.assistencia.command.impl.*;
import static com.assistencia.factory.FabricaDeAtendimentos.*;
import static com.assistencia.factory.FabricaDeOrcamentos.consultaOrcamento;

/** Registra os Commands de ordens de serviço e fichas técnicas. */
final class ComandosDeAtendimento {
    private ComandosDeAtendimento() { }

    static void registrar(RegistroDeComandos registro) {
        OrcamentoAprovarCommand aprovar = new OrcamentoAprovarCommand(aprovacaoService());
        registro.registrar("ordemServico.listar",
                new OrdemServicoListarCommand(telasDeAtendimento(), consultaOrcamento()));
        registro.registrar("ordemServico.consultar", new OrdemServicoConsultarCommand(telasDeAtendimento()));
        registro.registrar("ordemServico.inserir", new OrdemServicoInserirCommand(aprovar));
        registro.registrar("ordemServico.atualizar", new OrdemServicoAtualizarCommand(ordemServicoService()));
        registro.registrar("ordemServico.iniciar", new OrdemServicoIniciarCommand(ordemServicoService()));
        registro.registrar("ordemServico.concluir", new OrdemServicoConcluirCommand(ordemServicoService()));
        registro.registrar("ordemServico.cancelar", new OrdemServicoCancelarCommand(ordemServicoService()));
        registro.registrar("ordemServico.excluir", new OrdemServicoExcluirCommand(aprovacaoService()));
        registro.registrar("fichaTecnica.listar", new FichaTecnicaListarCommand(consultaAtendimento()));
        registro.registrar("fichaTecnica.consultar", new FichaTecnicaConsultarCommand(consultaAtendimento()));
        registro.registrar("fichaTecnica.atualizar", new FichaTecnicaAtualizarCommand(fichaTecnicaService()));
    }
}
