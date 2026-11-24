package com.assistencia.service;

import com.assistencia.dados.Campos;
import com.assistencia.exception.ConflictException;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.model.equipamento.Equipamento;
import java.sql.Connection;

/** Regra de negócio: equipamento que já tem orçamento não pode mudar de cliente nem ser excluído. */
final class TrocaDeDono {
    private TrocaDeDono() { }

    static void exigirSemOrcamentos(RepositorioEquipamento repositorio, Connection conexao, Long id) throws Exception {
        repositorio.existente(conexao, id);
        Long orcamentos = repositorio.contarOrcamentos(conexao, id);
        if (orcamentos > 0) {
            throw new ConflictException("Não é possível excluir o equipamento pois ele possui " + orcamentos
                    + " orçamento(s).");
        }
    }

    static Cliente novoDono(RepositorioEquipamento repositorio, Connection conexao, Equipamento equipamento,
                            Campos dados) throws Exception {
        Long clienteId = dados.inteiroLongo("clienteId");
        Cliente dono = repositorio.dono(conexao, clienteId);
        exigirPermitida(repositorio, conexao, equipamento, clienteId);
        return dono;
    }

    private static void exigirPermitida(RepositorioEquipamento repositorio, Connection conexao, Equipamento equipamento,
                                Long novoClienteId) throws Exception {
        if (equipamento.pertenceAoCliente(novoClienteId)) {
            return;
        }
        Long equipamentoId = IdDaEntidade.de(equipamento);
        Long orcamentos = repositorio.contarOrcamentos(conexao, equipamentoId);
        if (orcamentos > 0) {
            throw new ConflictException("Não é permitido alterar o cliente proprietário do equipamento pois ele "
                    + "já possui orçamento(s) cadastrado(s).");
        }
    }
}
