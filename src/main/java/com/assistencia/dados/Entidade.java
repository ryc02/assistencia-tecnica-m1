package com.assistencia.dados;

/**

 * Contrato comum das entidades: identidade e exportação dos dados (em forma de Campos) para persistência e

 * apresentação.

 */
public interface Entidade {
    Campos exportar();

    boolean possuiId(Long id);
}
