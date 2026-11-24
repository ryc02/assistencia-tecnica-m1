package com.assistencia.service;

import com.assistencia.builder.EquipamentoBuilder;
import com.assistencia.dados.Campos;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.model.equipamento.Equipamento;
import java.sql.Connection;
import java.time.LocalDateTime;

/** Monta o Equipamento (via Builder) para cadastro ou alteração, preservando a data de cadastro original. */
final class EquipamentoMontado {
    private EquipamentoMontado() { }

    static Equipamento novo(Campos dados, Cliente dono) {
        return montar(dados, null, dono, null);
    }

    static Equipamento atualizado(RepositorioEquipamento repositorio, Connection conexao, Long id, Campos dados)
            throws Exception {
        Equipamento existente = repositorio.existente(conexao, id);
        Cliente dono = TrocaDeDono.novoDono(repositorio, conexao, existente, dados);
        Campos atuais = existente.exportar();
        return montar(dados, id, dono, atuais.dataHora("dataCadastro"));
    }

    private static Equipamento montar(Campos dados, Long id, Cliente dono, LocalDateTime dataCadastro) {
        return new EquipamentoBuilder()
                .dados(dados)
                .id(id)
                .cliente(dono)
                .dataCadastro(dataCadastro)
                .build();
    }
}
