package com.assistencia.unitario;

import com.assistencia.dao.*;
import com.assistencia.exception.ConflictException;
import com.assistencia.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.model.orcamento.StatusOrcamento.*;
import static com.assistencia.unitario.FixturesAtendimento.orcamento;
import static com.assistencia.unitario.ServicosComMocks.gestao;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/** Testes unitários: orçamento com ordem de serviço não pode ser excluído. */
@ExtendWith(MockitoExtension.class)
class ExclusaoDeOrcamentoTest {
    @Test
    @DisplayName("excluir: orçamento com ordem de serviço gera ConflictException")
    void excluirComOrdem(@Mock OrcamentoDAO orcamentos, @Mock EquipamentoDAO equipamentos) throws Exception {
        // Arrange
        GestaoOrcamentoService servico = gestao(orcamentos, equipamentos);
        when(orcamentos.buscarPorId(null, 1L))
                .thenReturn(orcamento(1L, PENDENTE));
        when(orcamentos.possuiOrdemDeServico(null, 1L))
                .thenReturn(true);
        // Act
        Executable acao = () -> servico.excluir(1L);
        // Assert
        assertThrows(ConflictException.class, acao);
    }
}
