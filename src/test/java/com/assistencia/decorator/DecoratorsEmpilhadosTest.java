package com.assistencia.decorator;

import java.math.BigDecimal;
import org.junit.jupiter.api.*;
import static com.assistencia.decorator.CustoDoServico.base;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Testes do Decorator com vários adicionais empilhados. */
class DecoratorsEmpilhadosTest {
    @Test
    @DisplayName("decorators empilhados: todos os adicionais somados")
    void decoratorsEmpilhados() {
        // Arrange
        ServicoOrcamento servico = new AtendimentoUrgente(new GarantiaEstendida(
                new PeliculaProtecao(new LimpezaInterna(new BackupDados(base())))));
        // Act
        BigDecimal custo = CustoDoServico.de(servico);
        // Assert (150 + 50 + 40 + 30 + 80 + 60)
        assertEquals(new BigDecimal("410.00"), custo);
    }

    @Test
    @DisplayName("descrição: cada decorator anexa o seu nome na ordem em que foi aplicado")
    void descricaoEncadeada() {
        // Arrange
        ServicoOrcamento servico = new LimpezaInterna(new BackupDados(base()));
        // Act
        String descricao = servico.descrever();
        // Assert
        assertEquals("Serviço técnico, Backup de dados, Limpeza interna", descricao);
    }
}
