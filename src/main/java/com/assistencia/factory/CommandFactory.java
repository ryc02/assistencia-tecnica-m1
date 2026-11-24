package com.assistencia.factory;

import com.assistencia.command.ICommand;

/** [Factory Method + Command - Aula 07] Entrega o Command correspondente à ação pedida. */
public final class CommandFactory {
    private static final RegistroDeComandos REGISTRO = new RegistroDeComandos();

    static {
        ComandosDeCliente.registrar(REGISTRO);
        ComandosDeEquipamento.registrar(REGISTRO);
        ComandosDeOrcamento.registrar(REGISTRO);
        ComandosDeAtendimento.registrar(REGISTRO);
        ComandosDeAcesso.registrar(REGISTRO);
    }

    private CommandFactory() { }

    /** Devolve o Command da ação ou null quando a ação não existe. */
    public static ICommand criar(String acao) {
        return REGISTRO.comando(acao);
    }
}
