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

/** Testes unitários do cadastro de equipamentos (Cliente 1:N Equipamento). */
@ExtendWith(MockitoExtension.class)
class EquipamentoServiceTest {
    @Test
    @DisplayName("cadastrar: equipamento vinculado a cliente existente")
    void cadastrarComSucesso(@Mock EquipamentoDAO equipamentos, @Mock ClienteDAO clientes) throws Exception {
        // Arrange
        EquipamentoService servico = equipamentos(equipamentos, clientes);
        when(clientes.buscarPorId(null, 1L))
                .thenReturn(cliente(1L));
        // Act
        servico.cadastrar(dadosDeEquipamento(1L));
        // Assert
        verify(equipamentos).inserir(isNull(), any());
    }

    @Test
    @DisplayName("cadastrar: cliente inexistente gera NotFoundException")
    void cadastrarClienteInexistente(@Mock EquipamentoDAO equipamentos, @Mock ClienteDAO clientes) throws Exception {
        // Arrange
        EquipamentoService servico = equipamentos(equipamentos, clientes);
        // Act
        Executable acao = () -> servico.cadastrar(dadosDeEquipamento(9L));
        // Assert
        assertThrows(NotFoundException.class, acao);
        verify(equipamentos, never()).inserir(any(), any());
    }
}
