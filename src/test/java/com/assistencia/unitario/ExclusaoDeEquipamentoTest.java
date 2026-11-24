package com.assistencia.unitario;

import com.assistencia.dao.*;
import com.assistencia.exception.ConflictException;
import com.assistencia.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.unitario.FixturesCadastro.*;
import static com.assistencia.unitario.ServicosComMocks.equipamentos;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/** Testes unitários da alteração (mesmo dono) e da exclusão de equipamentos. */
@ExtendWith(MockitoExtension.class)
class ExclusaoDeEquipamentoTest {
    @Test
    @DisplayName("atualizar: mantendo o mesmo cliente, os dados são alterados")
    void atualizarMesmoDono(@Mock EquipamentoDAO equipamentos, @Mock ClienteDAO clientes) throws Exception {
        // Arrange
        EquipamentoService servico = equipamentos(equipamentos, clientes);
        when(equipamentos.buscarPorId(null, 1L))
                .thenReturn(equipamento(1L, cliente(1L)));
        when(clientes.buscarPorId(null, 1L))
                .thenReturn(cliente(1L));
        // Act
        servico.atualizar(1L, dadosDeEquipamento(1L));
        // Assert
        verify(equipamentos).atualizar(isNull(), any());
    }

    @Test
    @DisplayName("excluir: equipamento com orçamento gera ConflictException")
    void excluirComOrcamento(@Mock EquipamentoDAO equipamentos, @Mock ClienteDAO clientes) throws Exception {
        // Arrange
        EquipamentoService servico = equipamentos(equipamentos, clientes);
        when(equipamentos.buscarPorId(null, 1L))
                .thenReturn(equipamento(1L, cliente(1L)));
        when(equipamentos.contarOrcamentos(null, 1L))
                .thenReturn(3L);
        // Act
        Executable acao = () -> servico.excluir(1L);
        // Assert
        assertThrows(ConflictException.class, acao);
    }
}
