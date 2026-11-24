package com.assistencia.service;

import com.assistencia.dados.*;
import java.util.*;
import java.util.function.BiConsumer;

/** Dados que uma tela (JSP) precisa exibir, já exportados das entidades. */
public final class Tela {
    private final Campos atributos = new Campos();

    public Tela com(String chave, Object valor) {
        atributos.com(chave, valor);
        return this;
    }

    public Tela comEntidade(String chave, Entidade entidade) {
        if (entidade == null) {
            return this;
        }
        Campos dados = entidade.exportar();
        return com(chave, dados.comoMapa());
    }

    public Tela comRegistros(String chave, Registros<? extends Entidade> registros) {
        List<Map<String, Object>> lista = registros.exportar();
        return com(chave, lista);
    }

    public void publicar(BiConsumer<String, Object> destino) {
        atributos.paraCada(destino);
    }
}
