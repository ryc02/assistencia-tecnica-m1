package com.assistencia.unitario;

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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/** Testes unitários: aprovar duas vezes devolve a mesma ordem de serviço. */
@ExtendWith(MockitoExtension.class)
class AprovacaoIdempotenteTest {
    @Test
    @DisplayName("aprovar: segunda chamada devolve a mesma ordem (idempotente)")
    void aprovarIdempotente(@Mock OrcamentoDAO orcamentos, @Mock OrdemServicoDAO ordens,
            @Mock FichaTecnicaDAO fichas) throws Exception {
        // Arrange
        AprovacaoService servico = aprovacao(orcamentos, ordens, fichas);
        OrdemServico existente = ordem(5L, ABERTA);
        when(orcamentos.buscarPorIdParaUpdate(null, 1L))
                .thenReturn(orcamento(1L, APROVADO));
        when(ordens.buscarPorOrcamentoId(null, 1L))
                .thenReturn(existente);
        when(fichas.buscarPorOrdemServicoId(null, 5L))
                .thenReturn(ficha(1L, ABERTA));
        // Act
        OrdemServico devolvida = servico.aprovarOrcamento(1L, new Campos(), new Campos());
        // Assert
        assertEquals(existente, devolvida);
        verify(ordens, never()).inserir(any(), any());
    }
}
