package com.assistencia.factory;

import com.assistencia.command.ICommand;
import java.util.*;

/** [Calistenia - coleção de primeira classe] Associação entre o nome da ação e o Command. */
public final class RegistroDeComandos {
    private final Map<String, ICommand> comandos = new HashMap<>();

    void registrar(String acao, ICommand comando) {
        comandos.put(acao, comando);
    }

    ICommand comando(String acao) {
        return comandos.get(acao);
    }
}
