package com.assistencia.model.orcamento;

import com.assistencia.dados.*;
import com.assistencia.decorator.*;
import java.util.List;
import static java.util.stream.Collectors.toList;

/** [Calistenia - coleção de primeira classe] Adicionais escolhidos no orçamento. */
public final class Adicionais implements Exportavel {
    private final List<TipoAdicional> tipos;

    private Adicionais(List<TipoAdicional> tipos) {
        this.tipos = tipos;
    }

    public static Adicionais de(Campos dados) {
        Object informados = dados.valor("adicionais");
        List<String> nomes = NomesDeAdicionais.ler(informados);
        return new Adicionais(converter(nomes));
    }

    private static List<TipoAdicional> converter(List<String> nomes) {
        return nomes.stream()
                .map(TipoAdicional::deNome)
                .collect(toList());
    }

    /** Envolve o serviço com um decorator para cada adicional escolhido. */
    ServicoOrcamento decorar(ServicoOrcamento servico) {
        return tipos.stream()
                .reduce(servico, AdicionalFactory::decorar, (primeiro, segundo) -> segundo);
    }

    @Override
    public void exportar(Campos destino) {
        List<String> nomes = tipos.stream()
                .map(Enum::name)
                .collect(toList());
        destino.com("adicionais", nomes);
    }
}
