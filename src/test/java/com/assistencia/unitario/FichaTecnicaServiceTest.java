package com.assistencia.unitario;

import com.assistencia.Dados;
import com.assistencia.dados.Campos;
import com.assistencia.dao.FichaTecnicaDAO;
import com.assistencia.exception.NotFoundException;
import com.assistencia.service.FichaTecnicaService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.model.ordem.StatusOrdem.ABERTA;
import static com.assistencia.unitario.FixturesAtendimento.ficha;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/** Testes unitários da ficha técnica (relacionamento 1:1 com a ordem de serviço). */
@ExtendWith(MockitoExtension.class)
class FichaTecnicaServiceTest {
    @Test
    @DisplayName("atualizar: grava o laudo da ficha existente")
    void atualizarFicha(@Mock FichaTecnicaDAO fichas) throws Exception {
        // Arrange
        FichaTecnicaService servico = new FichaTecnicaService(fichas, new TransacaoFake());
        when(fichas.buscarPorId(null, 1L))
                .thenReturn(ficha(1L, ABERTA));
        Campos laudo = Dados.de("acessorios", "Carregador");
        // Act
        servico.atualizar(1L, laudo);
        // Assert
        verify(fichas).atualizar(isNull(), any());
    }

    @Test
    @DisplayName("atualizar: ficha inexistente gera NotFoundException")
    void atualizarInexistente(@Mock FichaTecnicaDAO fichas) throws Exception {
        // Arrange
        FichaTecnicaService servico = new FichaTecnicaService(fichas, new TransacaoFake());
        // Act
        Executable acao = () -> servico.atualizar(9L, new Campos());
        // Assert
        assertThrows(NotFoundException.class, acao);
    }
}
