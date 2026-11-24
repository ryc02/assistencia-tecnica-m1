package com.assistencia.unitario;

import com.assistencia.Dados;
import com.assistencia.dados.Campos;
import com.assistencia.dao.*;
import com.assistencia.model.ordem.OrdemServico;
import com.assistencia.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.model.orcamento.StatusOrcamento.*;
import static com.assistencia.model.ordem.StatusOrdem.ABERTA;
import static com.assistencia.unitario.FixturesAtendimento.*;
import static com.assistencia.unitario.ServicosComMocks.aprovacao;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsSecondArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/** Testes unitários da aprovação: Orçamento 1:1 Ordem de Serviço 1:1 Ficha Técnica. */
@ExtendWith(MockitoExtension.class)
class AprovacaoServiceTest {
    @Test
    @DisplayName("aprovar: gera ordem ABERTA e ficha técnica na mesma transação")
    void aprovarGeraOrdemEFicha(@Mock OrcamentoDAO orcamentos, @Mock OrdemServicoDAO ordens,
            @Mock FichaTecnicaDAO fichas) throws Exception {
        // Arrange
        AprovacaoService servico = aprovacao(orcamentos, ordens, fichas);
        when(orcamentos.buscarPorIdParaUpdate(null, 1L))
                .thenReturn(orcamento(1L, PENDENTE));
        when(ordens.inserir(isNull(), any()))
                .then(returnsSecondArg());
        Campos dadosDaOrdem = Dados.de("responsavel", "Lucas");
        // Act
        OrdemServico gerada = servico.aprovarOrcamento(1L, dadosDaOrdem, new Campos());
        // Assert
        Campos dados = gerada.exportar();
        assertEquals(ABERTA, dados.valor("status"));
        verify(fichas).inserir(isNull(), any());
    }
}
