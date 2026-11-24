package com.assistencia.command;

import com.assistencia.dados.Campos;
import com.assistencia.model.ficha.FichaTecnica;

/** Id da ordem de serviço à qual a ficha técnica pertence (para voltar à tela de atendimento). */
public final class IdDaOrdem {
    private IdDaOrdem() { }

    public static Long de(FichaTecnica ficha) {
        Campos dados = ficha.exportar();
        return dados.inteiroLongo("ordemServicoId");
    }
}
