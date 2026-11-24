package com.assistencia.builder;

import com.assistencia.dados.Campos;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.model.equipamento.Equipamento;
import java.time.LocalDateTime;

/** [Builder - Aula 06] Constrói Equipamento vinculado a um Cliente, validando voltagem e campos obrigatórios. */
public class EquipamentoBuilder extends BuilderDeEntidade<EquipamentoBuilder, Equipamento> {
    public EquipamentoBuilder cliente(Cliente cliente) {
        return com("cliente", cliente);
    }

    public EquipamentoBuilder dataCadastro(LocalDateTime dataCadastro) {
        return com("dataCadastro", dataCadastro);
    }

    public EquipamentoBuilder tipo(String tipo) {
        return com("tipo", tipo);
    }

    public EquipamentoBuilder marca(String marca) {
        return com("marca", marca);
    }

    public EquipamentoBuilder modelo(String modelo) {
        return com("modelo", modelo);
    }

    public EquipamentoBuilder voltagem(String voltagem) {
        return com("voltagem", voltagem);
    }

    @Override
    protected EquipamentoBuilder proprio() {
        return this;
    }

    @Override
    protected Equipamento construir(Campos campos) {
        return Equipamento.de(campos);
    }
}
