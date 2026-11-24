package com.assistencia.infra;

import java.sql.*;
import java.util.Optional;

/**

 * Endereço do banco e credenciais: variáveis DB_URL, DATABASE_FILE, DB_USER e DB_PASSWORD (nunca fixas no

 * código); sem elas, usa o H2 em arquivo ./data/assistenciadb.

 */
final class ConfiguracaoConexao {
    private final EnderecoDoBanco endereco;
    private final Credencial credencial;

    private ConfiguracaoConexao(String url, Credencial credencial) {
        this.endereco = new EnderecoDoBanco(url);
        this.credencial = credencial;
    }

    static ConfiguracaoConexao atual(String urlDeTeste) {
        Optional<String> teste = Optional.ofNullable(urlDeTeste);
        String endereco = teste.orElseGet(ConfiguracaoConexao::urlDoAmbiente);
        return new ConfiguracaoConexao(endereco, Credencial.doAmbiente());
    }

    Connection conectar() throws SQLException {
        return endereco.conectar(credencial);
    }

    private static String urlDoAmbiente() {
        String arquivo = Ambiente.ou("DATABASE_FILE", "./data/assistenciadb");
        String padrao = "jdbc:h2:file:" + arquivo + ";DB_CLOSE_DELAY=-1;MODE=MySQL;AUTO_SERVER=TRUE";
        return Ambiente.ou("DB_URL", padrao);
    }
}
