package com.assistencia.api;

import com.assistencia.api.json.*;
import com.assistencia.exception.ValidationException;
import java.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

/** Testes do leitor e do escritor JSON usados pela API REST (sem framework). */
class JsonTest {
    @Test
    @DisplayName("ler: objeto com texto, número, lógico e lista")
    void lerObjeto() {
        // Arrange
        String json = "{\"nome\":\"Ana\",\"ativo\":true,\"adicionais\":[\"BACKUP_DADOS\"]}";
        // Act
        Map<String, Object> lido = LeitorJson.lerObjeto(json);
        // Assert
        assertEquals("Ana", lido.get("nome"));
        assertEquals(true, lido.get("ativo"));
        List<String> esperados = asList("BACKUP_DADOS");
        assertEquals(esperados, lido.get("adicionais"));
    }

    @Test
    @DisplayName("ler: corpo que não é objeto gera ValidationException")
    void lerInvalido() {
        // Act
        Executable acao = () -> LeitorJson.lerObjeto("[1, 2]");
        // Assert
        assertThrows(ValidationException.class, acao);
    }

    @Test
    @DisplayName("escrever: escapa aspas e os sinais < e > (proteção contra XSS)")
    void escreverComEscape() {
        // Arrange
        Map<String, Object> dados = Map.of("nome", "<b>\"Ana\"</b>");
        // Act
        String json = EscritorJson.escrever(dados);
        // Assert
        assertEquals("{\"nome\":\"\\u003cb\\u003e\\\"Ana\\\"\\u003c/b\\u003e\"}", json);
    }
}
