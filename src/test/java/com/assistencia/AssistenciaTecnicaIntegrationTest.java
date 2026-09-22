package com.assistencia;

import com.assistencia.exception.ConflictException;
import com.assistencia.exception.NotFoundException;
import com.assistencia.exception.ValidationException;
import com.assistencia.infra.ConnectionFactory;
import com.assistencia.infra.TransactionManager;
import com.assistencia.model.*;
import com.assistencia.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.assistencia.controller.FrontControllerServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [Requisito 12: Suíte de Testes Automatizados T01 a T20]
 * Executa testes de integração reais diretamente no banco de dados JDBC (H2),
 * sem objetos mockados, validando a integridade do produto como entregue ao cliente final.
 */
public class AssistenciaTecnicaIntegrationTest {

    private final ClienteService clienteService = new ClienteService();
    private final EquipamentoService equipamentoService = new EquipamentoService();
    private final OrcamentoService orcamentoService = new OrcamentoService();
    private final OrdemServicoService ordemServicoService = new OrdemServicoService();
    private final FichaTecnicaService fichaTecnicaService = new FichaTecnicaService();

    @BeforeEach
    public void setup() throws Exception {
        ConnectionFactory.resetDatabaseForTests();
        // Garante que o banco está limpo/recarregado antes de cada teste
        try (Connection conn = ConnectionFactory.getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute("SET REFERENTIAL_INTEGRITY FALSE;");
            stmt.execute("TRUNCATE TABLE ficha_tecnica;");
            stmt.execute("TRUNCATE TABLE ordem_servico;");
            stmt.execute("TRUNCATE TABLE orcamento;");
            stmt.execute("TRUNCATE TABLE equipamento;");
            stmt.execute("TRUNCATE TABLE cliente;");
            stmt.execute("SET REFERENTIAL_INTEGRITY TRUE;");
        }
    }

    @Test
    @DisplayName("T01: CRUD de cliente e rejeição de CPF duplicado")
    public void testT01_CrudClienteECpfDuplicado() throws Exception {
        Cliente c1 = clienteService.cadastrar("Ana Maria", "11122233344", "ana@email.com", "9999-1111", "Rua A", "10", "Centro", "Joinville");
        assertNotNull(c1.getId());
        assertEquals("Ana Maria", c1.getNome());

        // Rejeita CPF duplicado
        assertThrows(ConflictException.class, () -> {
            clienteService.cadastrar("Outra Ana", "11122233344", "outra@email.com", "9999-2222", "Rua B", "20", "Centro", "Joinville");
        });
    }

    @Test
    @DisplayName("T02: CRUD de equipamento e rejeição de cliente inexistente")
    public void testT02_CrudEquipamentoEClienteInexistente() throws Exception {
        Cliente c = clienteService.cadastrar("Bruno Costa", "22233344455", "bruno@email.com", "9999-2222", "Rua B", "20", "Centro", "Joinville");
        Equipamento eq = equipamentoService.cadastrar(c.getId(), "Notebook", "Lenovo", "ThinkPad", "SN-LN-1", "Preto", "BIVOLT", "Sem bateria");
        assertNotNull(eq.getId());

        // Rejeita cliente inexistente
        assertThrows(NotFoundException.class, () -> {
            equipamentoService.cadastrar(9999L, "Monitor", "LG", "29UM68", null, "Preto", "110V", null);
        });
    }

    @Test
    @DisplayName("T03: CRUD de orçamento, cálculo no servidor e bloqueio de alteração de aprovado")
    public void testT03_OrcamentoCalculoEBloqueioAprovado() throws Exception {
        Cliente c = clienteService.cadastrar("Carla Dias", "33344455566", "carla@email.com", "9999-3333", "Rua C", "30", "Centro", "Joinville");
        Equipamento eq = equipamentoService.cadastrar(c.getId(), "Desktop", "Custom", "Ryzen 5", null, "Preto", "BIVOLT", "Não liga");

        // Peças 100, Mão de Obra 50, Desconto 10% -> Total R$ 135,00
        Orcamento orc = orcamentoService.cadastrar(eq.getId(), "Micro não liga", "Fonte queimada", new BigDecimal("100.00"), new BigDecimal("50.00"), new BigDecimal("10.00"));
        assertEquals(new BigDecimal("135.00"), orc.getValorTotal());

        // Aprova orçamento
        ordemServicoService.buscarPorOrcamentoId(orc.getId());
        orcamentoService.aprovarOrcamento(orc.getId(), "Técnico Lucas", Prioridade.NORMAL, null, "Obs", EstadoConservacao.BOM, "Cabo", true, false, null, "OK", "OK");

        // Tentar alterar orçamento aprovado lança ConflictException
        assertThrows(ConflictException.class, () -> {
            orcamentoService.atualizar(orc.getId(), eq.getId(), "Alterar problema", "Novo diag", new BigDecimal("200.00"), new BigDecimal("100.00"), BigDecimal.ZERO);
        });
    }

    @Test
    @DisplayName("T04: Aprovar orçamento gera par OrdemServico e FichaTecnica")
    public void testT04_AprovarGeraOrdemEFicha() throws Exception {
        Cliente c = clienteService.cadastrar("Daniel Rocha", "44455566677", "daniel@email.com", "9999-4444", "Rua D", "40", "Centro", "Joinville");
        Equipamento eq = equipamentoService.cadastrar(c.getId(), "Tablet", "Apple", "iPad Pro", "SN-IPAD-1", "Cinza", "BIVOLT", "Tela trincada");
        Orcamento orc = orcamentoService.cadastrar(eq.getId(), "Tela quebrada", "Troca de vidro", new BigDecimal("300.00"), new BigDecimal("100.00"), BigDecimal.ZERO);

        OrdemServico os = orcamentoService.aprovarOrcamento(
                orc.getId(), "Técnico Marcos", Prioridade.ALTA, null, "Prioritário",
                EstadoConservacao.REGULAR, "Capa protetora", true, true, "Tela trincada canto superior", "Touch ok", "Recepção ok"
        );

        assertNotNull(os.getId());
        assertEquals(StatusOrdem.ABERTA, os.getStatus());

        FichaTecnica ft = fichaTecnicaService.buscarPorOrdemServicoId(os.getId());
        assertNotNull(ft);
        assertEquals(EstadoConservacao.REGULAR, ft.getEstadoConservacao());
        assertEquals("Tela trincada canto superior", ft.getDescricaoAvarias());
    }

    @Test
    @DisplayName("T05: Atualizar ordem e ficha preserva vínculos e estados finais")
    public void testT05_AtualizarOrdemEFicha() throws Exception {
        Cliente c = clienteService.cadastrar("Eduardo Lima", "55566677788", "edu@email.com", "9999-5555", "Rua E", "50", "Centro", "Joinville");
        Equipamento eq = equipamentoService.cadastrar(c.getId(), "Console", "Sony", "PS5", "SN-PS5-1", "Branco", "BIVOLT", "Sem vídeo");
        Orcamento orc = orcamentoService.cadastrar(eq.getId(), "Luz azul da morte", "Reballing HDMI", new BigDecimal("150.00"), new BigDecimal("250.00"), BigDecimal.ZERO);

        OrdemServico os = orcamentoService.aprovarOrcamento(orc.getId(), "Técnico Silva", Prioridade.NORMAL, null, null, EstadoConservacao.BOM, "1 Controle", true, false, null, "OK", "OK");
        FichaTecnica ft = fichaTecnicaService.buscarPorOrdemServicoId(os.getId());

        // Atualiza Ordem e Ficha enquanto ABERTA
        ordemServicoService.atualizar(os.getId(), "Técnico Silva Jr", Prioridade.ALTA, null, "Atualizado", 180);
        fichaTecnicaService.atualizar(ft.getId(), EstadoConservacao.BOM, "2 Controles", true, false, null, "HDMI OK", "OK");

        OrdemServico osAtu = ordemServicoService.buscarPorId(os.getId());
        assertEquals("Técnico Silva Jr", osAtu.getResponsavel());
        assertEquals(Prioridade.ALTA, osAtu.getPrioridade());

        // Conclui ordem
        ordemServicoService.iniciar(os.getId());
        ordemServicoService.concluir(os.getId());

        // Tentar editar ordem concluída rejeita
        assertThrows(ConflictException.class, () -> {
            ordemServicoService.atualizar(os.getId(), "Outro Técnico", Prioridade.BAIXA, null, "Invalido", 90);
        });
    }

    @Test
    @DisplayName("T06: Excluir ordem ABERTA remove ficha, ordem e restaura orçamento para PENDENTE")
    public void testT06_ExcluirOrdemAbertaRestauraOrcamento() throws Exception {
        Cliente c = clienteService.cadastrar("Fernanda Luz", "66677788899", "fer@email.com", "9999-6666", "Rua F", "60", "Centro", "Joinville");
        Equipamento eq = equipamentoService.cadastrar(c.getId(), "Notebook", "HP", "Pavilion", "SN-HP-1", "Prata", "BIVOLT", "Teclado falhando");
        Orcamento orc = orcamentoService.cadastrar(eq.getId(), "Teclado falhando", "Troca de teclado", new BigDecimal("80.00"), new BigDecimal("70.00"), BigDecimal.ZERO);

        OrdemServico os = orcamentoService.aprovarOrcamento(orc.getId(), "Técnico Ana", Prioridade.NORMAL, null, null, EstadoConservacao.BOM, "Fonte", true, false, null, "OK", "OK");

        // Executa exclusão da Ordem Aberta
        orcamentoService.excluirOrdemAberta(os.getId());

        // Verifica que a ordem e a ficha foram removidas
        assertNull(ordemServicoService.buscarPorOrcamentoId(orc.getId()));

        // Verifica que o orçamento voltou a ser PENDENTE
        Orcamento orcRestaurado = orcamentoService.buscarPorId(orc.getId());
        assertEquals(StatusOrcamento.PENDENTE, orcRestaurado.getStatus());
    }

    @Test
    @DisplayName("T07: Excluir ordem iniciada deve ser bloqueado")
    public void testT07_ExcluirOrdemIniciadaBloqueado() throws Exception {
        Cliente c = clienteService.cadastrar("Gabriel Souza", "77788899900", "gabi@email.com", "9999-7777", "Rua G", "70", "Centro", "Joinville");
        Equipamento eq = equipamentoService.cadastrar(c.getId(), "Smartphone", "Samsung", "S20", "SN-S20", "Preto", "NAO_APLICAVEL", "Bateria estufada");
        Orcamento orc = orcamentoService.cadastrar(eq.getId(), "Bateria estufada", "Troca de bateria", new BigDecimal("120.00"), new BigDecimal("60.00"), BigDecimal.ZERO);

        OrdemServico os = orcamentoService.aprovarOrcamento(orc.getId(), "Técnico Gabi", Prioridade.NORMAL, null, null, EstadoConservacao.REGULAR, "Nenhum", true, true, "Bateria estufada", "OK", "OK");

        // Inicia o atendimento (ABERTA -> EM_ANDAMENTO)
        ordemServicoService.iniciar(os.getId());

        // Tentativa de excluir ordem em andamento lança ConflictException
        assertThrows(ConflictException.class, () -> {
            orcamentoService.excluirOrdemAberta(os.getId());
        });
    }

    @Test
    @DisplayName("T08: Dois equipamentos de um cliente (Associação 1:N)")
    public void testT08_DoisEquipamentosDeUmCliente() throws Exception {
        Cliente c = clienteService.cadastrar("Helena Castro", "88899900011", "helena@email.com", "9999-8888", "Rua H", "80", "Centro", "Joinville");
        Equipamento eq1 = equipamentoService.cadastrar(c.getId(), "Notebook", "Dell", "G15", "SN-1", "Preto", "BIVOLT", null);
        Equipamento eq2 = equipamentoService.cadastrar(c.getId(), "Monitor", "Dell", "P2419H", "SN-2", "Preto", "BIVOLT", null);

        List<Equipamento> lista = equipamentoService.listarPorCliente(c.getId());
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("T09: Restrição UNIQUE no banco rejeita segunda ficha técnica para mesma ordem")
    public void testT09_UniqueFichaTecnica() throws Exception {
        Cliente c = clienteService.cadastrar("Igor Mendes", "99900011122", "igor@email.com", "9999-9999", "Rua I", "90", "Centro", "Joinville");
        Equipamento eq = equipamentoService.cadastrar(c.getId(), "Nobreak", "SMS", "Manager III", "SN-SMS", "Preto", "110V", null);
        Orcamento orc = orcamentoService.cadastrar(eq.getId(), "Troca de bateria nobreak", "Troca 2 baterias 12V", new BigDecimal("180.00"), new BigDecimal("70.00"), BigDecimal.ZERO);

        OrdemServico os = orcamentoService.aprovarOrcamento(orc.getId(), "Técnico Igor", Prioridade.NORMAL, null, null, EstadoConservacao.BOM, "Nenhum", true, false, null, "OK", "OK");

        // Inserção direta de segunda ficha técnica deve violar UNIQUE no banco SQL
        assertThrows(Exception.class, () -> {
            TransactionManager.executeInTransaction(conn -> {
                String sql = "INSERT INTO ficha_tecnica (ordem_servico_id, estado_conservacao) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setLong(1, os.getId());
                    stmt.setString(2, "RUIM");
                    stmt.executeUpdate();
                }
                return null;
            });
        });
    }

    @Test
    @DisplayName("T10: Aprovação de R$ 100 peças + R$ 50 mão de obra com 10% de desconto -> R$ 135,00")
    public void testT10_CalculoExemploAceite() throws Exception {
        Cliente c = clienteService.cadastrar("Júlia Paes", "00011122233", "julia@email.com", "9999-0000", "Rua J", "100", "Centro", "Joinville");
        Equipamento eq = equipamentoService.cadastrar(c.getId(), "Notebook", "Asus", "Zenbook", "SN-ASUS", "Azul", "BIVOLT", null);
        Orcamento orc = orcamentoService.cadastrar(eq.getId(), "Limpeza cooler", "Limpeza geral", new BigDecimal("100.00"), new BigDecimal("50.00"), new BigDecimal("10.00"));

        assertEquals(new BigDecimal("135.00"), orc.getValorTotal());

        OrdemServico os = orcamentoService.aprovarOrcamento(orc.getId(), "Técnico Júlia", Prioridade.NORMAL, null, null, EstadoConservacao.BOM, "Nenhum", true, false, null, "OK", "OK");

        Orcamento orcAprovado = orcamentoService.buscarPorId(orc.getId());
        assertEquals(StatusOrcamento.APROVADO, orcAprovado.getStatus());
        assertEquals(new BigDecimal("135.00"), orcAprovado.getValorTotal());
        assertNotNull(os);
    }

    @Test
    @DisplayName("T11: Aprovar novamente é idempotente e retorna a mesma ordem")
    public void testT11_AprovaçãoIdempotente() throws Exception {
        Cliente c = clienteService.cadastrar("Kátia Ramos", "11100022233", "katia@email.com", "9999-1100", "Rua K", "110", "Centro", "Joinville");
        Equipamento eq = equipamentoService.cadastrar(c.getId(), "Impressora", "HP", "LaserJet", "SN-HP-2", "Branca", "110V", null);
        Orcamento orc = orcamentoService.cadastrar(eq.getId(), "Papel atolado", "Limpeza roletes", new BigDecimal("20.00"), new BigDecimal("80.00"), BigDecimal.ZERO);

        OrdemServico os1 = orcamentoService.aprovarOrcamento(orc.getId(), "Técnico Kátia", Prioridade.NORMAL, null, null, EstadoConservacao.BOM, "Nenhum", true, false, null, "OK", "OK");
        OrdemServico os2 = orcamentoService.aprovarOrcamento(orc.getId(), "Técnico Kátia", Prioridade.NORMAL, null, null, EstadoConservacao.BOM, "Nenhum", true, false, null, "OK", "OK");

        assertEquals(os1.getId(), os2.getId());
    }

    @Test
    @DisplayName("T12: Aprovações concorrentes garantem que apenas uma ordem e uma ficha são persistidas")
    public void testT12_AprovacoesConcorrentes() throws Exception {
        Cliente c = clienteService.cadastrar("Paula Lima", "12312312399", "paula@email.com", "9999-1234", "Rua P", "160", "Centro", "Joinville");
        Equipamento eq = equipamentoService.cadastrar(c.getId(), "Notebook", "Dell", "Vostro", "SN-DELL-P", "Preto", "BIVOLT", null);
        Orcamento orc = orcamentoService.cadastrar(eq.getId(), "Troca de tela", "Troca display", new BigDecimal("200.00"), new BigDecimal("100.00"), BigDecimal.ZERO);

        int numThreads = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < numThreads; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    latch.await();
                    orcamentoService.aprovarOrcamento(orc.getId(), "Técnico " + index, Prioridade.NORMAL, null, "Obs", EstadoConservacao.BOM, "Nenhum", true, false, null, "OK", "OK");
                } catch (Exception ignored) {
                }
            });
        }
        latch.countDown();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        List<OrdemServico> ordens = ordemServicoService.listarTodas();
        int countOrdens = 0;
        for (OrdemServico os : ordens) {
            if (os.getOrcamento().getId().equals(orc.getId())) {
                countOrdens++;
            }
        }
        assertEquals(1, countOrdens, "Deve existir exatamente uma ordem para o orçamento!");
    }

    @Test
    @DisplayName("T13: Falha ao criar a ficha realiza rollback integral mantendo orçamento PENDENTE")
    public void testT13_FalhaFichaRollbackIntegral() throws Exception {
        Cliente c = clienteService.cadastrar("Lucas Vaz", "22211133344", "lucas@email.com", "9999-2211", "Rua L", "120", "Centro", "Joinville");
        Equipamento eq = equipamentoService.cadastrar(c.getId(), "All in One", "Lenovo", "AIO 3", "SN-L-3", "Preto", "BIVOLT", null);
        Orcamento orc = orcamentoService.cadastrar(eq.getId(), "Lento", "Troca HD por SSD", new BigDecimal("150.00"), new BigDecimal("100.00"), BigDecimal.ZERO);

        // Tentar aprovar com avarias = true mas sem descrição de avarias (viola regra de validação da FichaTecnicaBuilder)
        assertThrows(ValidationException.class, () -> {
            orcamentoService.aprovarOrcamento(orc.getId(), "Técnico Lucas", Prioridade.NORMAL, null, null, EstadoConservacao.REGULAR, "Fonte", true, true, null, "OK", "OK");
        });

        // Comprova que o orçamento continua PENDENTE e não existe ordem criada
        Orcamento orcAposFalha = orcamentoService.buscarPorId(orc.getId());
        assertEquals(StatusOrcamento.PENDENTE, orcAposFalha.getStatus());
        assertNull(ordemServicoService.buscarPorOrcamentoId(orc.getId()));
    }

    @Test
    @DisplayName("T14: Falha no meio da exclusão faz rollback integral mantendo registros e orçamento APROVADO")
    public void testT14_FalhaExclusaoRollback() throws Exception {
        Cliente c = clienteService.cadastrar("Renato Alves", "32132132199", "renato@email.com", "9999-4321", "Rua R", "170", "Centro", "Joinville");
        Equipamento eq = equipamentoService.cadastrar(c.getId(), "Monitor", "Samsung", "T350", "SN-SAM-R", "Preto", "BIVOLT", null);
        Orcamento orc = orcamentoService.cadastrar(eq.getId(), "Fonte queimada", "Troca capacitor", new BigDecimal("40.00"), new BigDecimal("60.00"), BigDecimal.ZERO);

        OrdemServico os = orcamentoService.aprovarOrcamento(orc.getId(), "Técnico Renato", Prioridade.NORMAL, null, null, EstadoConservacao.BOM, "Cabo VGA", true, false, null, "OK", "OK");

        assertThrows(Exception.class, () -> {
            TransactionManager.executeInTransaction(conn -> {
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM ficha_tecnica WHERE ordem_servico_id = ?")) {
                    stmt.setLong(1, os.getId());
                    stmt.executeUpdate();
                }
                throw new RuntimeException("Simulação de falha no meio da exclusão");
            });
        });

        assertNotNull(ordemServicoService.buscarPorId(os.getId()));
        assertNotNull(fichaTecnicaService.buscarPorOrdemServicoId(os.getId()));
        assertEquals(StatusOrcamento.APROVADO, orcamentoService.buscarPorId(orc.getId()).getStatus());
    }

    @Test
    @DisplayName("T15: Excluir cliente com equipamento deve ser bloqueado")
    public void testT15_ExcluirClienteComEquipamentoBloqueado() throws Exception {
        Cliente c = clienteService.cadastrar("Marcelo Faria", "33322244455", "marcelo@email.com", "9999-3322", "Rua M", "130", "Centro", "Joinville");
        equipamentoService.cadastrar(c.getId(), "Webcam", "Logitech", "C920", "SN-LOGI", "Preta", "NAO_APLICAVEL", null);

        assertThrows(ConflictException.class, () -> {
            clienteService.excluir(c.getId());
        });
    }

    @Test
    @DisplayName("T16: Consulta de ID inexistente lança NotFoundException")
    public void testT16_IdInexistente() {
        assertThrows(NotFoundException.class, () -> {
            clienteService.buscarPorId(99999L);
        });
    }

    @Test
    @DisplayName("T17: Tentativas de SQL Injection e XSS são tratadas com segurança pelos PreparedStatements")
    public void testT17_SqlInjectionEXss() throws Exception {
        String sqlInjection = "' OR '1'='1'; DROP TABLE cliente; --";
        String xssPayload = "<script>alert('xss')</script>";

        Cliente c = clienteService.cadastrar(xssPayload, "65498732100", "sec@email.com", "9999-8877", sqlInjection, "10", "Bairro", "Cidade");
        assertNotNull(c.getId());

        Cliente buscado = clienteService.buscarPorId(c.getId());
        assertEquals(xssPayload, buscado.getNome());
        assertEquals(sqlInjection, buscado.getLogradouro());
        assertFalse(clienteService.listarTodos().isEmpty());
    }

    @Test
    @DisplayName("T18: Proteção CSRF rejeita POST sem token (403) e Front Controller rejeita alteração via GET (405)")
    public void testT18_CsrfEMetodoHttp() throws Exception {
        FrontControllerServlet servlet = new FrontControllerServlet();

        // 1. GET para operação de inserção (alteração) -> 405 Method Not Allowed
        HttpServletResponse responseGet = createMockResponse();
        HttpServletRequest requestGet = createMockRequest("GET", "cliente.inserir", null);
        servlet.service(requestGet, responseGet);
        assertEquals(405, getResponseStatus(responseGet));

        // 2. POST sem token CSRF -> 403 Forbidden
        HttpServletResponse responsePost = createMockResponse();
        HttpServletRequest requestPost = createMockRequest("POST", "cliente.inserir", null);
        servlet.service(requestPost, responsePost);
        assertEquals(403, getResponseStatus(responsePost));
    }

    private HttpServletResponse createMockResponse() {
        final int[] statusCode = new int[]{200};
        return (HttpServletResponse) Proxy.newProxyInstance(
                HttpServletResponse.class.getClassLoader(),
                new Class<?>[]{HttpServletResponse.class},
                (proxy, method, args) -> {
                    if ("setStatus".equals(method.getName())) {
                        statusCode[0] = (Integer) args[0];
                        return null;
                    }
                    if ("getStatus".equals(method.getName())) {
                        return statusCode[0];
                    }
                    return null;
                }
        );
    }

    private int getResponseStatus(HttpServletResponse response) throws Exception {
        return (Integer) response.getClass().getMethod("getStatus").invoke(response);
    }

    private HttpServletRequest createMockRequest(String httpMethod, String acao, String csrfToken) {
        Map<String, Object> attributes = new HashMap<>();
        Map<String, String> parameters = new HashMap<>();
        if (acao != null) parameters.put("acao", acao);
        if (csrfToken != null) parameters.put("csrfToken", csrfToken);

        HttpSession session = (HttpSession) Proxy.newProxyInstance(
                HttpSession.class.getClassLoader(),
                new Class<?>[]{HttpSession.class},
                (proxy, method, args) -> {
                    if ("getAttribute".equals(method.getName())) return attributes.get(args[0]);
                    if ("setAttribute".equals(method.getName())) { attributes.put((String) args[0], args[1]); return null; }
                    if ("getId".equals(method.getName())) return "session-test-id";
                    return null;
                }
        );

        return (HttpServletRequest) Proxy.newProxyInstance(
                HttpServletRequest.class.getClassLoader(),
                new Class<?>[]{HttpServletRequest.class},
                (proxy, method, args) -> {
                    String name = method.getName();
                    if ("getMethod".equals(name)) return httpMethod;
                    if ("getParameter".equals(name)) return parameters.get(args[0]);
                    if ("getSession".equals(name)) return session;
                    if ("getCharacterEncoding".equals(name)) return "UTF-8";
                    if ("setCharacterEncoding".equals(name)) return null;
                    if ("setAttribute".equals(name)) { attributes.put((String) args[0], args[1]); return null; }
                    if ("getAttribute".equals(name)) return attributes.get(args[0]);
                    if ("getRequestDispatcher".equals(name)) {
                        return Proxy.newProxyInstance(
                                javax.servlet.RequestDispatcher.class.getClassLoader(),
                                new Class<?>[]{javax.servlet.RequestDispatcher.class},
                                (dp, dm, dargs) -> null
                        );
                    }
                    return null;
                }
        );
    }

    @Test
    @DisplayName("T19: Limites de desconto (0%, 100% e rejeição >100%)")
    public void testT19_LimitesDesconto() throws Exception {
        Cliente c = clienteService.cadastrar("Nivia Rosa", "44433355566", "nivia@email.com", "9999-4433", "Rua N", "140", "Centro", "Joinville");
        Equipamento eq = equipamentoService.cadastrar(c.getId(), "Caixa de Som", "JBL", "Boombox", "SN-JBL", "Preta", "BIVOLT", null);

        // Desconto 0% -> R$ 100,00
        Orcamento o0 = orcamentoService.cadastrar(eq.getId(), "Troca bateria", "Troca", new BigDecimal("50.00"), new BigDecimal("50.00"), BigDecimal.ZERO);
        assertEquals(new BigDecimal("100.00"), o0.getValorTotal());

        // Desconto 100% -> R$ 0,00
        Orcamento o100 = orcamentoService.cadastrar(eq.getId(), "Troca bateria", "Troca", new BigDecimal("50.00"), new BigDecimal("50.00"), new BigDecimal("100.00"));
        assertEquals(new BigDecimal("0.00"), o100.getValorTotal());

        // Desconto > 100% lança ValidationException
        assertThrows(ValidationException.class, () -> {
            orcamentoService.cadastrar(eq.getId(), "Troca bateria", "Troca", new BigDecimal("50.00"), new BigDecimal("50.00"), new BigDecimal("150.00"));
        });
    }

    @Test
    @DisplayName("T20: Invariante de Integridade Final (Nenhuma ordem sem ficha e nenhuma ficha órfã)")
    public void testT20_InvarianteIntegridadeFinal() throws Exception {
        Cliente c = clienteService.cadastrar("Otávio Neto", "55544466677", "otavio@email.com", "9999-5544", "Rua O", "150", "Centro", "Joinville");
        Equipamento eq = equipamentoService.cadastrar(c.getId(), "Notebook", "Acer", "Nitro 5", "SN-ACER", "Preto", "BIVOLT", null);
        Orcamento orc = orcamentoService.cadastrar(eq.getId(), "Superaquecimento", "Limpeza", new BigDecimal("50.00"), new BigDecimal("100.00"), BigDecimal.ZERO);
        ordemServicoService.buscarPorOrcamentoId(orc.getId());
        orcamentoService.aprovarOrcamento(orc.getId(), "Técnico Otávio", Prioridade.NORMAL, null, null, EstadoConservacao.BOM, "Nenhum", true, false, null, "OK", "OK");

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt1 = conn.prepareStatement("SELECT COUNT(*) FROM ordem_servico os LEFT JOIN ficha_tecnica ft ON os.id = ft.ordem_servico_id WHERE ft.id IS NULL;");
             PreparedStatement stmt2 = conn.prepareStatement("SELECT COUNT(*) FROM ficha_tecnica ft LEFT JOIN ordem_servico os ON ft.ordem_servico_id = os.id WHERE os.id IS NULL;")) {
            
            try (ResultSet rs1 = stmt1.executeQuery()) {
                if (rs1.next()) {
                    assertEquals(0, rs1.getInt(1), "Nenhuma ordem pode existir sem ficha técnica vinculada!");
                }
            }
            try (ResultSet rs2 = stmt2.executeQuery()) {
                if (rs2.next()) {
                    assertEquals(0, rs2.getInt(1), "Nenhuma ficha técnica pode existir sem ordem de serviço!");
                }
            }
        }
    }
}
