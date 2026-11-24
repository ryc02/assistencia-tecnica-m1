package com.assistencia.unitario;

import com.assistencia.dao.*;
import com.assistencia.exception.*;
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

/** Testes unitários: equipamento com orçamento não pode trocar de dono. */
@ExtendWith(MockitoExtension.class)
class TrocaDeDonoTest {
    @Test
    @DisplayName("atualizar: trocar o dono de equipamento com orçamento gera ConflictException")
    void trocarDonoComOrcamento(@Mock EquipamentoDAO equipamentos, @Mock ClienteDAO clientes) throws Exception {
        // Arrange
        EquipamentoService servico = equipamentos(equipamentos, clientes);
        when(equipamentos.buscarPorId(null, 1L))
                .thenReturn(equipamento(1L, cliente(1L)));
        when(clientes.buscarPorId(null, 2L))
                .thenReturn(cliente(2L));
        when(equipamentos.contarOrcamentos(null, 1L))
                .thenReturn(1L);
        // Act
        Executable acao = () -> servico.atualizar(1L, dadosDeEquipamento(2L));
        // Assert
        assertThrows(ConflictException.class, acao);
    }
}
