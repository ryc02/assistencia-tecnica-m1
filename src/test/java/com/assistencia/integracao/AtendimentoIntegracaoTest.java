package com.assistencia.integracao;

import com.assistencia.Dados;
import com.assistencia.dados.Campos;
import com.assistencia.exception.ConflictException;
import com.assistencia.infra.ConnectionFactory;
import com.assistencia.model.ficha.FichaTecnica;
import com.assistencia.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import static com.assistencia.factory.FabricaDeAtendimentos.*;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T05 (atualizações preservam vínculos e estados finais). */
class AtendimentoIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T05: atualizar ficha preserva o vínculo e ordem concluída não aceita alteração")
    void t05VinculosEEstadosFinais() throws Exception {
        // Arrange
        Long ordemId = aprovar(orcamentoNovo("34534534511"));
        ConsultaAtendimento consulta = consultaAtendimento();
        FichaTecnicaService fichas = fichaTecnicaService();
        OrdemServicoService ordens = ordemServicoService();
        Campos laudo = Dados.de("acessoriosEntregues", "Carregador");
        Campos novaResponsabilidade = Dados.de("responsavel", "Outro");
        Long fichaId = id(consulta.fichaDaOrdem(ordemId));
        // Act
        FichaTecnica atualizada = fichas.atualizar(fichaId, laudo);
        ordens.iniciar(ordemId);
        ordens.concluir(ordemId);
        Executable reprogramar = () -> ordens.reprogramar(ordemId, novaResponsabilidade);
        // Assert
        assertEquals(ordemId, valor(atualizada, "ordemServicoId"));
        assertThrows(ConflictException.class, reprogramar);
    }
}
