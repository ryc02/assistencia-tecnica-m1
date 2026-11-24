package com.assistencia.model.valor;

import com.assistencia.dados.*;
import com.assistencia.exception.ValidationException;
import java.util.Map;
import java.util.function.Consumer;

/** Relacionamento com outra entidade (1:N ou 1:1), por exemplo o Cliente dono de um Equipamento. */
public final class Vinculo<T extends Entidade> {
    private final Chave chave;
    private final T alvo;

    private Vinculo(String chave, T alvo) {
        this.chave = new Chave(chave);
        this.alvo = alvo;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Entidade> Vinculo<T> obrigatorio(Campos dados, String chave, String mensagem) {
        Object alvo = dados.valor(chave);
        if (!(alvo instanceof Entidade)) {
            throw new ValidationException(mensagem);
        }
        return new Vinculo<>(chave, (T) alvo);
    }

    public boolean referencia(Long id) {
        return alvo.possuiId(id);
    }

    public void executar(Consumer<T> acao) {
        acao.accept(alvo);
    }

    public void exportar(Campos destino) {
        Campos dadosDoAlvo = alvo.exportar();
        Object idDoAlvo = dadosDoAlvo.valor("id");
        Map<String, Object> aninhado = dadosDoAlvo.comoMapa();
        chave.gravar(destino, aninhado);
        Chave chaveDoId = chave.comSufixo("Id");
        chaveDoId.gravar(destino, idDoAlvo);
    }
}
