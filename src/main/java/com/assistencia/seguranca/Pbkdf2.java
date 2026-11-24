package com.assistencia.seguranca;

import java.security.GeneralSecurityException;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;

/** Cálculo do hash PBKDF2WithHmacSHA256. */
final class Pbkdf2 {
    private Pbkdf2() { }

    static byte[] calcular(String senha, byte[] salt, int iteracoes, int tamanhoEmBits) {
        PBEKeySpec especificacao = new PBEKeySpec(senha.toCharArray(), salt, iteracoes, tamanhoEmBits);
        try {
            SecretKeyFactory fabrica = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            SecretKey chave = fabrica.generateSecret(especificacao);
            return chave.getEncoded();
        } catch (GeneralSecurityException excecao) {
            throw new IllegalStateException("Erro ao calcular hash de senha.", excecao);
        }
    }
}
