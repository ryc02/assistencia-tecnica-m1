package com.assistencia.seguranca;

import java.security.SecureRandom;
import java.util.Base64.Encoder;
import java.util.Base64;

/** Gera e confere hashes de senha no formato $pbkdf2$iteracoes$salt$hash (PBKDF2WithHmacSHA256 + salt). */
public final class CriptografiaSenha {
    private static final String PREFIXO = "$pbkdf2$";
    private static final int TAMANHO_PREFIXO = PREFIXO.length();
    private static final int ITERACOES = 10000;

    private CriptografiaSenha() { }

    public static String gerarHash(String senha) {
        byte[] salt = new byte[16];
        SecureRandom aleatorio = new SecureRandom();
        aleatorio.nextBytes(salt);
        byte[] hash = Pbkdf2.calcular(senha, salt, ITERACOES, 256);
        return PREFIXO + ITERACOES + "$" + codificar(salt) + "$" + codificar(hash);
    }

    public static boolean confere(String senhaDigitada, String armazenada) {
        if (senhaDigitada == null || armazenada == null) {
            return false;
        }
        if (!armazenada.startsWith(PREFIXO)) {
            return false;
        }
        String semPrefixo = armazenada.substring(TAMANHO_PREFIXO);
        return confereSemPrefixo(senhaDigitada, semPrefixo);
    }

    private static boolean confereSemPrefixo(String senhaDigitada, String semPrefixo) {
        try {
            String[] partes = semPrefixo.split("\\$");
            return ComparacaoDeHash.confere(senhaDigitada, partes);
        } catch (RuntimeException excecao) {
            return false;
        }
    }

    private static String codificar(byte[] bytes) {
        Encoder codificador = Base64.getEncoder();
        return codificador.encodeToString(bytes);
    }
}
