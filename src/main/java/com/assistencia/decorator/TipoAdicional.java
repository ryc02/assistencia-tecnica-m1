package com.assistencia.decorator;

import com.assistencia.dados.Campos;
import com.assistencia.exception.ValidationException;
import com.assistencia.model.valor.Dinheiro;

/**
 * Tabela de adicionais oferecidos pela assistência técnica (equivalente à tabela de condimentos da Cafeteria Café
 * Lindo).
 */
public enum TipoAdicional {
    BACKUP_DADOS("Backup de dados", "50.00"),
    LIMPEZA_INTERNA("Limpeza interna", "40.00"),
    PELICULA_PROTECAO("Película de proteção", "30.00"),
    GARANTIA_ESTENDIDA("Garantia estendida", "80.00"),
    ATENDIMENTO_URGENTE("Atendimento urgente", "60.00");

    private final Descricao descricao;
    private final Dinheiro valor;

    TipoAdicional(String descricao, String quantia) {
        this.descricao = new Descricao(descricao);
        valor = Dinheiro.de(quantia);
    }

    public static TipoAdicional deNome(String nome) {
        String maiusculo = nome.toUpperCase();
        try {
            return valueOf(maiusculo);
        } catch (IllegalArgumentException excecao) {
            throw new ValidationException("Adicional inválido: " + nome);
        }
    }

    String anexarDescricao(String descricaoAnterior) {
        return descricao.anexarA(descricaoAnterior);
    }

    Dinheiro acrescentarValor(Dinheiro custoAnterior) {
        return custoAnterior.somar(valor);
    }

    public void exportar(Campos destino) {
        destino.com("nome", name());
        descricao.gravarEm(destino);
        valor.exportarComo("valor", destino);
    }
}
