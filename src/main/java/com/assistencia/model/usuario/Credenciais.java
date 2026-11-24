package com.assistencia.model.usuario;

import com.assistencia.dados.*;
import com.assistencia.exception.ValidationException;
import com.assistencia.model.valor.Email;

/** E-mail de acesso e hash da senha. A exportação comum nunca inclui a senha. */
public final class Credenciais implements Exportavel {
    private final Email email;
    private final SenhaHash senha;

    private Credenciais(Email email, SenhaHash senha) {
        this.email = email;
        this.senha = senha;
    }

    static Credenciais armazenadas(Campos dados) {
        Email email = Email.obrigatorio(dados);
        return new Credenciais(email, SenhaHash.armazenada(dados));
    }

    static Credenciais novas(Campos dados) {
        Email email = Email.obrigatorio(dados);
        return new Credenciais(email, SenhaHash.nova(dados));
    }

    void exigirSenha(String senhaDigitada) {
        if (!senha.confere(senhaDigitada)) {
            throw new ValidationException("Credenciais inválidas! Verifique seu e-mail e senha.");
        }
    }

    @Override
    public void exportar(Campos destino) {
        email.exportar(destino);
    }

    void exportarComSenha(Campos destino) {
        exportar(destino);
        senha.exportar(destino);
    }
}
