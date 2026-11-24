package com.assistencia.unitario;

import com.assistencia.dados.Campos;
import com.assistencia.dao.ClienteDAO;
import com.assistencia.exception.ConflictException;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.service.ClienteService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.unitario.FixturesCadastro.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsSecondArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/** Testes unitários do cadastro de clientes (CPF único). */
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {
    @Test
    @DisplayName("cadastrar: CPF novo é salvo somente com os dígitos")
    void cadastrarComSucesso(@Mock ClienteDAO clientes) throws Exception {
        // Arrange
        ClienteService servico = new ClienteService(clientes, new TransacaoFake());
        when(clientes.inserir(isNull(), any()))
                .then(returnsSecondArg());
        // Act
        Cliente criado = servico.cadastrar(dadosDeCliente());
        // Assert
        Campos dados = criado.exportar();
        assertEquals("11122233344", dados.texto("cpf"));
    }

    @Test
    @DisplayName("cadastrar: CPF já cadastrado gera ConflictException e não insere")
    void cadastrarCpfDuplicado(@Mock ClienteDAO clientes) throws Exception {
        // Arrange
        ClienteService servico = new ClienteService(clientes, new TransacaoFake());
        when(clientes.buscarPorCpf(null, "11122233344"))
                .thenReturn(cliente(9L));
        // Act
        Executable acao = () -> servico.cadastrar(dadosDeCliente());
        // Assert
        assertThrows(ConflictException.class, acao);
        verify(clientes, never()).inserir(any(), any());
    }
}
