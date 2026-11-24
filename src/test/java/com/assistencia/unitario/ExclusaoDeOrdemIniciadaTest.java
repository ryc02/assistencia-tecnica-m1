package com.assistencia.unitario;

import com.assistencia.dao.*;
import com.assistencia.exception.ConflictException;
import com.assistencia.service.AprovacaoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.model.ordem.StatusOrdem.*;
import static com.assistencia.unitario.FixturesAtendimento.*;
import static com.assistencia.unitario.ServicosComMocks.aprovacao;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/** Testes unitários: ordem EM_ANDAMENTO não pode ser excluída. */
@ExtendWith(MockitoExtension.class)
class ExclusaoDeOrdemIniciadaTest {
    @Test
    @DisplayName("excluir: ordem EM_ANDAMENTO gera ConflictException")
    void excluirOrdemEmAndamento(@Mock OrcamentoDAO orcamentos, @Mock OrdemServicoDAO ordens,
            @Mock FichaTecnicaDAO fichas) throws Exception {
        // Arrange
        AprovacaoService servico = aprovacao(orcamentos, ordens, fichas);
        when(ordens.buscarPorIdParaUpdate(null, 5L))
                .thenReturn(ordem(5L, EM_ANDAMENTO));
        // Act
        Executable acao = () -> servico.excluirOrdemAberta(5L);
        // Assert
        assertThrows(ConflictException.class, acao);
    }
}
