package com.assistencia.unitario;

import com.assistencia.dao.*;
import com.assistencia.exception.ConflictException;
import com.assistencia.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.model.orcamento.StatusOrcamento.APROVADO;
import static com.assistencia.unitario.FixturesAtendimento.*;
import static com.assistencia.unitario.FixturesCadastro.*;
import static com.assistencia.unitario.ServicosComMocks.orcamentos;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/** Testes unitários: orçamento APROVADO não pode ser alterado. */
@ExtendWith(MockitoExtension.class)
class AlteracaoDeOrcamentoTest {
    @Test
    @DisplayName("atualizar: orçamento APROVADO não pode ser alterado")
    void atualizarAprovado(@Mock OrcamentoDAO orcamentos, @Mock EquipamentoDAO equipamentos) throws Exception {
        // Arrange
        OrcamentoService servico = orcamentos(orcamentos, equipamentos);
        when(orcamentos.buscarPorId(null, 1L))
                .thenReturn(orcamento(1L, APROVADO));
        // Act
        Executable acao = () -> servico.atualizar(1L, dadosDeOrcamento());
        // Assert
        assertThrows(ConflictException.class, acao);
    }
}
