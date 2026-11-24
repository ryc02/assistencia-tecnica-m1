package com.assistencia.dados;

/**

 * Objeto que sabe escrever os próprios dados em um Campos ("Diga, não pergunte"): ninguém lê o estado interno

 * dele por getters.

 */
public interface Exportavel {
    void exportar(Campos destino);
}
