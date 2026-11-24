package com.assistencia.builder;

import com.assistencia.dados.Campos;

/** [Builder - Aula 06] Base dos builders: acumula os campos numa coleção e entrega a entidade no build(). */
public abstract class BuilderDeEntidade<B extends BuilderDeEntidade<B, T>, T> {
    private final Campos campos = new Campos();

    /** Copia todos os campos recebidos (formulário, JSON ou banco de dados). */
    public B dados(Campos dados) {
        campos.comTodos(dados);
        return proprio();
    }

    public B id(Long id) {
        return com("id", id);
    }

    /** A validação dos dados acontece aqui, na criação da entidade. */
    public T build() {
        return construir(campos);
    }

    protected B com(String chave, Object valor) {
        campos.com(chave, valor);
        return proprio();
    }

    protected abstract B proprio();

    protected abstract T construir(Campos campos);
}
