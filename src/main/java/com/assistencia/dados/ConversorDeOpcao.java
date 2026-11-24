package com.assistencia.dados;

import com.assistencia.exception.ValidationException;

/** Converte o nome de uma opção (enum) recebida como texto, usando um valor padrão quando vazio. */
public final class ConversorDeOpcao {
    private ConversorDeOpcao() { }

    public static <E extends Enum<E>> E opcao(Object valor, String chave, E padrao) {
        if (!Conversor.preenchido(valor)) {
            return padrao;
        }
        String nome = Conversor.texto(valor);
        return procurar(nome, chave, padrao);
    }

    private static <E extends Enum<E>> E procurar(String nome, String chave, E padrao) {
        String maiusculo = nome.toUpperCase();
        try {
            Class<E> tipo = padrao.getDeclaringClass();
            return Enum.valueOf(tipo, maiusculo);
        } catch (IllegalArgumentException excecao) {
            throw new ValidationException("Opção inválida para " + chave + ": " + nome);
        }
    }
}
