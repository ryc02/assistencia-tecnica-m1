package com.assistencia.integracao;

import com.assistencia.Dados;
import com.assistencia.dados.Campos;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.service.*;
import static com.assistencia.factory.FabricaDeAtendimentos.aprovacaoService;
import static com.assistencia.factory.FabricaDeClientes.clienteService;
import static com.assistencia.factory.FabricaDeEquipamentos.equipamentoService;
import static com.assistencia.factory.FabricaDeOrcamentos.orcamentoService;
import static com.assistencia.integracao.BancoDeTeste.id;
import static com.assistencia.unitario.FixturesAtendimento.dadosDeOrcamento;
import static com.assistencia.unitario.FixturesCadastro.*;

/** Monta cenários reais no banco H2 em memória (sem mocks) usando as fábricas da aplicação. */
final class CenarioDeIntegracao {
    private CenarioDeIntegracao() { }

    static Long cliente(String cpf) throws Exception {
        Campos dados = dadosDeCliente();
        dados.com("cpf", cpf);
        return id(clienteService().cadastrar(dados));
    }

    static Long equipamento(Long clienteId) throws Exception {
        EquipamentoService servico = equipamentoService();
        return id(servico.cadastrar(dadosDeEquipamento(clienteId)));
    }

    static Long orcamento(Long equipamentoId, String desconto) throws Exception {
        Campos dados = dadosDeOrcamento();
        dados.com("equipamentoId", equipamentoId);
        dados.com("percentualDesconto", desconto);
        OrcamentoService servico = orcamentoService();
        Orcamento criado = servico.cadastrar(dados);
        return id(criado);
    }

    static Long orcamentoNovo(String cpf) throws Exception {
        Long clienteId = cliente(cpf);
        return orcamento(equipamento(clienteId), "0");
    }

    static Long aprovar(Long orcamentoId) throws Exception {
        AprovacaoService servico = aprovacaoService();
        Campos ordem = Dados.de("responsavel", "Técnico Lucas");
        return id(servico.aprovarOrcamento(orcamentoId, ordem, new Campos()));
    }
}
