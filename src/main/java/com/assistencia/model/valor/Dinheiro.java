package com.assistencia.model.valor;

import com.assistencia.dados.Campos;
import com.assistencia.exception.ValidationException;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

/** Valor monetário com 2 casas decimais (arredondamento HALF_UP). */
public final class Dinheiro {
    private final BigDecimal valor;

    private Dinheiro(BigDecimal quantia) {
        valor = quantia.setScale(2, HALF_UP);
    }

    public static Dinheiro de(String quantia) {
        return new Dinheiro(new BigDecimal(quantia));
    }

    public static Dinheiro naoNegativo(Campos dados, String chave, String rotulo) {
        BigDecimal informado = dados.decimal(chave);
        if (informado == null) {
            return new Dinheiro(ZERO);
        }
        if (informado.signum() < 0) {
            throw new ValidationException(rotulo + " não pode ser negativo.");
        }
        return new Dinheiro(informado);
    }

    public Dinheiro somar(Dinheiro outro) {
        return outro.somadoA(valor);
    }

    public Dinheiro descontar(Percentual percentual) {
        return new Dinheiro(percentual.descontoSobre(valor));
    }

    public void exportarComo(String chave, Campos destino) {
        destino.com(chave, valor);
    }

    private Dinheiro somadoA(BigDecimal parcela) {
        return new Dinheiro(parcela.add(valor));
    }
}
