package com.assistencia.unitario;

import com.assistencia.dao.*;
import com.assistencia.service.*;

/** Monta os services reais com DAOs simulados (Mockito) e a TransacaoFake, graças ao DIP. */
public final class ServicosComMocks {
    private ServicosComMocks() { }

    public static EquipamentoService equipamentos(EquipamentoDAO equipamentos, ClienteDAO clientes) {
        return new EquipamentoService(new RepositorioEquipamento(equipamentos, clientes), new TransacaoFake());
    }

    public static OrcamentoService orcamentos(OrcamentoDAO orcamentos, EquipamentoDAO equipamentos) {
        return new OrcamentoService(new RepositorioOrcamento(orcamentos, equipamentos), new TransacaoFake());
    }

    public static GestaoOrcamentoService gestao(OrcamentoDAO orcamentos, EquipamentoDAO equipamentos) {
        return new GestaoOrcamentoService(new RepositorioOrcamento(orcamentos, equipamentos), new TransacaoFake());
    }

    public static AprovacaoService aprovacao(OrcamentoDAO orcamentos, OrdemServicoDAO ordens, FichaTecnicaDAO fichas) {
        RegistroDeAtendimento registro = new RegistroDeAtendimento(ordens, fichas);
        return new AprovacaoService(new RepositorioAprovacao(orcamentos, registro), new TransacaoFake());
    }
}
