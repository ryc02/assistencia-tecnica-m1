package com.assistencia.decorator;

import com.assistencia.exception.ValidationException;
import java.math.BigDecimal;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import static com.assistencia.decorator.CustoDoServico.base;
import static com.assistencia.decorator.TipoAdicional.GARANTIA_ESTENDIDA;
import static org.junit.jupiter.api.Assertions.*;

/** Testes da fábrica que escolhe o decorator pelo tipo de adicional (Factory + Decorator). */
class AdicionalFactoryTest {
    @Test
    @DisplayName("decorar: devolve o decorator correspondente ao tipo")
    void decorarPeloTipo() {
        // Arrange
        ServicoOrcamento servico = base();
        // Act
        ServicoOrcamento decorado = AdicionalFactory.decorar(servico, GARANTIA_ESTENDIDA);
        // Assert
        assertInstanceOf(GarantiaEstendida.class, decorado);
        assertEquals(new BigDecimal("230.00"), CustoDoServico.de(decorado));
    }

    @Test
    @DisplayName("deNome: nome de adicional desconhecido gera ValidationException")
    void nomeDesconhecido() {
        // Act
        Executable acao = () -> TipoAdicional.deNome("LAVAGEM");
        // Assert
        assertThrows(ValidationException.class, acao);
    }
}
