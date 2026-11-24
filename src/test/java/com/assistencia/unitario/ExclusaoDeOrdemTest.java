package com.assistencia.unitario;

import com.assistencia.dao.*;
import com.assistencia.service.AprovacaoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.model.orcamento.StatusOrcamento.APROVADO;
import static com.assistencia.model.ordem.StatusOrdem.*;
import static com.assistencia.unitario.FixturesAtendimento.*;
import static com.assistencia.unitario.ServicosComMocks.aprovacao;
import static org.mockito.Mockito.*;

/** Testes unitários: excluir ordem ABERTA remove a ficha e a ordem. */
@ExtendWith(MockitoExtension.class)
class ExclusaoDeOrdemTest {
    @Test
    @DisplayName("excluir: ordem ABERTA remove a ficha e a ordem")
    void excluirOrdemAberta(@Mock OrcamentoDAO orcamentos, @Mock OrdemServicoDAO ordens,
            @Mock FichaTecnicaDAO fichas) throws Exception {
        // Arrange
        AprovacaoService servico = aprovacao(orcamentos, ordens, fichas);
        when(ordens.buscarPorIdParaUpdate(null, 5L))
                .thenReturn(ordem(5L, ABERTA));
        when(orcamentos.buscarPorIdParaUpdate(null, 1L))
                .thenReturn(orcamento(1L, APROVADO));
        // Act
        servico.excluirOrdemAberta(5L);
        // Assert
        verify(fichas).excluirPorOrdemServicoId(null, 5L);
        verify(ordens).excluir(null, 5L);
    }
}
