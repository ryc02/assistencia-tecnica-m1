package com.assistencia.unitario;

import com.assistencia.dados.Campos;
import com.assistencia.dao.*;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.model.orcamento.StatusOrcamento.*;
import static com.assistencia.unitario.FixturesAtendimento.orcamento;
import static com.assistencia.unitario.ServicosComMocks.gestao;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/** Testes unitários: orçamento PENDENTE pode ser recusado. */
@ExtendWith(MockitoExtension.class)
class RecusaDeOrcamentoTest {
    @Test
    @DisplayName("recusar: orçamento PENDENTE passa para RECUSADO")
    void recusarPendente(@Mock OrcamentoDAO orcamentos, @Mock EquipamentoDAO equipamentos) throws Exception {
        // Arrange
        GestaoOrcamentoService servico = gestao(orcamentos, equipamentos);
        when(orcamentos.buscarPorIdParaUpdate(null, 1L))
                .thenReturn(orcamento(1L, PENDENTE));
        // Act
        Orcamento recusado = servico.recusar(1L);
        // Assert
        Campos dados = recusado.exportar();
        assertEquals(RECUSADO, dados.valor("status"));
    }
}
