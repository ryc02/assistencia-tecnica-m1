package com.assistencia.unitario;

import com.assistencia.Dados;
import com.assistencia.builder.*;
import com.assistencia.dados.Campos;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.model.equipamento.Equipamento;
import com.assistencia.model.usuario.Usuario;
import com.assistencia.seguranca.CriptografiaSenha;

/** Clientes, equipamentos e usuários fictícios usados na etapa Arrange dos testes. */
public final class FixturesCadastro {
    private FixturesCadastro() { }

    public static Campos dadosDeCliente() {
        return Dados.de("nome", "Ana Maria", "cpf", "111.222.333-44", "email", "ana@email.com",
                "telefone", "9999-1111", "logradouro", "Rua A", "numero", "10", "bairro", "Centro",
                "cidade", "Mogi das Cruzes");
    }

    public static Cliente cliente(Long id) {
        return new ClienteBuilder()
                .dados(dadosDeCliente())
                .id(id)
                .build();
    }

    public static Campos dadosDeEquipamento(Long clienteId) {
        return Dados.de("clienteId", clienteId, "tipo", "Notebook", "marca", "Dell", "modelo", "G15",
                "voltagem", "BIVOLT");
    }

    public static Equipamento equipamento(Long id, Cliente cliente) {
        return new EquipamentoBuilder()
                .dados(dadosDeEquipamento(1L))
                .id(id)
                .cliente(cliente)
                .build();
    }

    public static Usuario usuario(Long id, String cargo, String senha) {
        String hash = CriptografiaSenha.gerarHash(senha);
        Campos dados = Dados.de("id", id, "nome", "Usuário " + id, "email", "u" + id + "@m1.com",
                "senha", hash, "cargo", cargo);
        return Usuario.de(dados);
    }
}
