package com.assistencia.decorator;

import java.math.BigDecimal;
import org.junit.jupiter.api.*;
import static com.assistencia.decorator.CustoDoServico.base;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Testes do padrão Decorator: cada adicional embrulha o serviço e soma sua parte. */
class AdicionalDecoratorTest {
    @Test
    @DisplayName("serviço base: custo = peças + mão de obra")
    void servicoBase() {
        // Arrange
        ServicoOrcamento servico = base();
        // Act
        BigDecimal custo = CustoDoServico.de(servico);
        // Assert
        assertEquals(new BigDecimal("150.00"), custo);
    }

    @Test
    @DisplayName("um decorator: backup acrescenta R$ 50,00")
    void umDecorator() {
        // Arrange
        ServicoOrcamento servico = new BackupDados(base());
        // Act
        BigDecimal custo = CustoDoServico.de(servico);
        // Assert
        assertEquals(new BigDecimal("200.00"), custo);
    }
}
