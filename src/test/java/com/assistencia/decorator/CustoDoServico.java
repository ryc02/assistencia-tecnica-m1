package com.assistencia.decorator;

import com.assistencia.dados.Campos;
import com.assistencia.model.valor.Dinheiro;
import java.math.BigDecimal;

/** Auxiliar de teste: lê o custo (Dinheiro) de um serviço decorado como BigDecimal. */
final class CustoDoServico {
    private CustoDoServico() { }

    static BigDecimal de(ServicoOrcamento servico) {
        Dinheiro custo = servico.calcularCusto();
        Campos destino = new Campos();
        custo.exportarComo("custo", destino);
        return destino.decimal("custo");
    }

    static ServicoOrcamento base() {
        Dinheiro pecas = Dinheiro.de("100.00");
        return new ServicoBase(pecas, Dinheiro.de("50.00"));
    }
}
