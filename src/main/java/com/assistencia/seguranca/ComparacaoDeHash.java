package com.assistencia.seguranca;

import java.security.MessageDigest;
import java.util.Base64.Decoder;
import java.util.Base64;

/** Recalcula o hash da senha digitada com o mesmo salt e compara em tempo constante. */
final class ComparacaoDeHash {
    private ComparacaoDeHash() { }

    static boolean confere(String senhaDigitada, String[] partes) {
        if (partes.length != 3) {
            return false;
        }
        Decoder decodificador = Base64.getDecoder();
        byte[] salt = decodificador.decode(partes[1]);
        byte[] esperado = decodificador.decode(partes[2]);
        int bits = esperado.length * 8;
        int iteracoes = Integer.parseInt(partes[0]);
        byte[] calculado = Pbkdf2.calcular(senhaDigitada, salt, iteracoes, bits);
        return MessageDigest.isEqual(calculado, esperado);
    }
}
