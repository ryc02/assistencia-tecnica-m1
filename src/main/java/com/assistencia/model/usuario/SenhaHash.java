package com.assistencia.model.usuario;

import com.assistencia.dados.Campos;
import com.assistencia.exception.ValidationException;
import com.assistencia.seguranca.CriptografiaSenha;

/** Senha guardada somente como hash PBKDF2 (nunca em texto puro). */
public final class SenhaHash {
    private final String hash;

    private SenhaHash(String hash) {
        this.hash = hash;
    }

    public static SenhaHash armazenada(Campos dados) {
        return new SenhaHash(dados.texto("senha"));
    }

    public static SenhaHash nova(Campos dados) {
        String senhaDigitada = dados.texto("senha");
        if (senhaDigitada == null) {
            throw new ValidationException("Senha do usuário é obrigatória.");
        }
        return new SenhaHash(CriptografiaSenha.gerarHash(senhaDigitada));
    }

    boolean confere(String senhaDigitada) {
        return CriptografiaSenha.confere(senhaDigitada, hash);
    }

    void exportar(Campos destino) {
        destino.com("senha", hash);
    }
}
