package com.assistencia.dados;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Leituras tipadas de um conjunto de campos; a subclasse Campos guarda os valores. */
public abstract class LeituraDeCampos {
    public abstract Object valor(String chave);

    public String texto(String chave) {
        return Conversor.texto(valor(chave));
    }

    public Long inteiroLongo(String chave) {
        return ConversorNumerico.inteiroLongo(valor(chave), chave);
    }

    public Integer inteiro(String chave) {
        return ConversorNumerico.inteiro(valor(chave), chave);
    }

    public BigDecimal decimal(String chave) {
        return ConversorNumerico.decimal(valor(chave), chave);
    }

    public Boolean logico(String chave) {
        return Conversor.logico(valor(chave));
    }

    public LocalDateTime dataHora(String chave) {
        return ConversorDeTempo.dataHora(valor(chave), chave);
    }

    public <E extends Enum<E>> E opcaoOuPadrao(String chave, E padrao) {
        return ConversorDeOpcao.opcao(valor(chave), chave, padrao);
    }
}
