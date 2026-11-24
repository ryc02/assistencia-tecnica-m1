package com.assistencia.factory;

import com.assistencia.api.recurso.*;

/** [Factory - Aula 06/07] Recursos REST registrados pelo nome usado na URL. */
public final class RecursoFactory {
    public static final Registro<RecursoConsultavel> CONSULTAVEIS = new Registro<>();
    public static final Registro<RecursoCriavel> CRIAVEIS = new Registro<>();
    public static final Registro<RecursoAtualizavel> ATUALIZAVEIS = new Registro<>();
    public static final Registro<RecursoExcluivel> EXCLUIVEIS = new Registro<>();
    public static final Registro<RecursoComAcoes> COM_ACOES = new Registro<>();

    static {
        RecursosDeCadastro.registrar();
        RecursosDeAtendimento.registrar();
    }

    private RecursoFactory() { }
}
