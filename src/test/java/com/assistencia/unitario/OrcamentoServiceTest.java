package com.assistencia.unitario;

import com.assistencia.dados.Campos;
import com.assistencia.dao.*;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.service.*;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.unitario.FixturesAtendimento.*;
import static com.assistencia.unitario.FixturesCadastro.*;
import static com.assistencia.unitario.ServicosComMocks.orcamentos;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsSecondArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/** Testes unitários do cadastro de orçamentos com adicionais (Decorator). */
@ExtendWith(MockitoExtension.class)
class OrcamentoServiceTest {
    @Test
    @DisplayName("cadastrar: total = (peças + mão de obra + adicionais) com desconto")
    void cadastrarComAdicionais(@Mock OrcamentoDAO orcamentos, @Mock EquipamentoDAO equipamentos) throws Exception {
        // Arrange
        OrcamentoService servico = orcamentos(orcamentos, equipamentos);
        when(equipamentos.buscarPorId(null, 1L))
                .thenReturn(equipamento(1L, cliente(1L)));
        when(orcamentos.inserir(isNull(), any()))
                .then(returnsSecondArg());
        Campos dados = dadosDeOrcamento();
        dados.com("percentualDesconto", "10");
        List<String> adicionais = asList("BACKUP_DADOS", "LIMPEZA_INTERNA");
        dados.com("adicionais", adicionais);
        // Act
        Orcamento criado = servico.cadastrar(dados);
        // Assert ((100 + 50 + 50 + 40) * 0,90)
        Campos exportado = criado.exportar();
        assertEquals(new BigDecimal("216.00"), exportado.decimal("valorTotal"));
    }
}
