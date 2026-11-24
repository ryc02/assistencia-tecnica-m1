package com.assistencia.unitario;

import com.assistencia.Dados;
import com.assistencia.dados.Campos;
import com.assistencia.dao.OrdemServicoDAO;
import com.assistencia.exception.ConflictException;
import com.assistencia.service.OrdemServicoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.model.ordem.StatusOrdem.*;
import static com.assistencia.unitario.FixturesAtendimento.ordem;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/** Testes unitários: ordens em estado final não aceitam cancelamento nem alteração. */
@ExtendWith(MockitoExtension.class)
class EstadoFinalDaOrdemTest {
    @Test
    @DisplayName("cancelar: ordem CONCLUIDA já está no estado final")
    void cancelarConcluida(@Mock OrdemServicoDAO ordens) throws Exception {
        // Arrange
        OrdemServicoService servico = new OrdemServicoService(ordens, new TransacaoFake());
        when(ordens.buscarPorIdParaUpdate(null, 1L))
                .thenReturn(ordem(1L, CONCLUIDA));
        // Act
        Executable acao = () -> servico.cancelar(1L);
        // Assert
        assertThrows(ConflictException.class, acao);
    }

    @Test
    @DisplayName("reprogramar: ordem CONCLUIDA não pode ter os dados alterados")
    void reprogramarConcluida(@Mock OrdemServicoDAO ordens) throws Exception {
        // Arrange
        OrdemServicoService servico = new OrdemServicoService(ordens, new TransacaoFake());
        when(ordens.buscarPorIdParaUpdate(null, 1L))
                .thenReturn(ordem(1L, CONCLUIDA));
        Campos novosDados = Dados.de("responsavel", "Outro");
        // Act
        Executable acao = () -> servico.reprogramar(1L, novosDados);
        // Assert
        assertThrows(ConflictException.class, acao);
    }
}
