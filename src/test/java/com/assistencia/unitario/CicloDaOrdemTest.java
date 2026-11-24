package com.assistencia.unitario;

import com.assistencia.dados.Campos;
import com.assistencia.dao.OrdemServicoDAO;
import com.assistencia.exception.ConflictException;
import com.assistencia.model.ordem.OrdemServico;
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

/** Testes unitários das transições da ordem de serviço (ABERTA, EM_ANDAMENTO, CONCLUIDA). */
@ExtendWith(MockitoExtension.class)
class CicloDaOrdemTest {
    @Test
    @DisplayName("iniciar: ABERTA passa para EM_ANDAMENTO")
    void iniciarOrdemAberta(@Mock OrdemServicoDAO ordens) throws Exception {
        // Arrange
        OrdemServicoService servico = new OrdemServicoService(ordens, new TransacaoFake());
        when(ordens.buscarPorIdParaUpdate(null, 1L))
                .thenReturn(ordem(1L, ABERTA));
        // Act
        OrdemServico iniciada = servico.iniciar(1L);
        // Assert
        Campos dados = iniciada.exportar();
        assertEquals(EM_ANDAMENTO, dados.valor("status"));
    }

    @Test
    @DisplayName("concluir: ordem ABERTA não pode ser concluída sem iniciar")
    void concluirSemIniciar(@Mock OrdemServicoDAO ordens) throws Exception {
        // Arrange
        OrdemServicoService servico = new OrdemServicoService(ordens, new TransacaoFake());
        when(ordens.buscarPorIdParaUpdate(null, 1L))
                .thenReturn(ordem(1L, ABERTA));
        // Act
        Executable acao = () -> servico.concluir(1L);
        // Assert
        assertThrows(ConflictException.class, acao);
    }
}
