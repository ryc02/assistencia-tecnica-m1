package com.assistencia.factory;

import com.assistencia.command.ICommand;
import com.assistencia.command.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * [Requisito 8: Fábrica Simples (CommandFactory)]
 * Centraliza o registro e a instanciação dos comandos conforme a ação informada.
 * Classificada como fábrica simples conforme a abordagem didática da aula.
 */
public class CommandFactory {

    private static final Map<String, ICommand> comandos = new HashMap<>();

    static {
        // Cliente
        comandos.put("cliente.listar", new ClienteListarCommand());
        comandos.put("cliente.consultar", new ClienteConsultarCommand());
        comandos.put("cliente.inserir", new ClienteInserirCommand());
        comandos.put("cliente.atualizar", new ClienteAtualizarCommand());
        comandos.put("cliente.excluir", new ClienteExcluirCommand());

        // Equipamento
        comandos.put("equipamento.listar", new EquipamentoListarCommand());
        comandos.put("equipamento.consultar", new EquipamentoConsultarCommand());
        comandos.put("equipamento.inserir", new EquipamentoInserirCommand());
        comandos.put("equipamento.atualizar", new EquipamentoAtualizarCommand());
        comandos.put("equipamento.excluir", new EquipamentoExcluirCommand());

        // Orçamento
        comandos.put("orcamento.listar", new OrcamentoListarCommand());
        comandos.put("orcamento.consultar", new OrcamentoConsultarCommand());
        comandos.put("orcamento.inserir", new OrcamentoInserirCommand());
        comandos.put("orcamento.atualizar", new OrcamentoAtualizarCommand());
        comandos.put("orcamento.excluir", new OrcamentoExcluirCommand());
        comandos.put("orcamento.aprovar", new OrcamentoAprovarCommand());
        comandos.put("orcamento.recusar", new OrcamentoRecusarCommand());

        // Ordem de Serviço
        comandos.put("ordemServico.listar", new OrdemServicoListarCommand());
        comandos.put("ordemServico.consultar", new OrdemServicoConsultarCommand());
        comandos.put("ordemServico.inserir", new OrdemServicoInserirCommand());
        comandos.put("ordemServico.atualizar", new OrdemServicoAtualizarCommand());
        comandos.put("ordemServico.excluir", new OrdemServicoExcluirCommand());
        comandos.put("ordemServico.iniciar", new OrdemServicoIniciarCommand());
        comandos.put("ordemServico.concluir", new OrdemServicoConcluirCommand());
        comandos.put("ordemServico.cancelar", new OrdemServicoCancelarCommand());

        // Ficha Técnica
        comandos.put("fichaTecnica.listar", new FichaTecnicaListarCommand());
        comandos.put("fichaTecnica.consultar", new FichaTecnicaConsultarCommand());
        comandos.put("fichaTecnica.atualizar", new FichaTecnicaAtualizarCommand());
    }

    /**
     * Retorna o comando correspondente à ação solicitada ou null se desconhecida.
     */
    public static ICommand createCommand(String acao) {
        if (acao == null) return null;
        return comandos.get(acao);
    }

    /**
     * [Requisito 10 & 11] Verifica se a ação é de alteração de estado (exige HTTP POST).
     */
    public static boolean isPostAction(String acao) {
        if (acao == null) return false;
        return acao.endsWith(".inserir") || acao.endsWith(".atualizar") || acao.endsWith(".excluir")
                || acao.endsWith(".aprovar") || acao.endsWith(".recusar")
                || acao.endsWith(".iniciar") || acao.endsWith(".concluir") || acao.endsWith(".cancelar");
    }
}
