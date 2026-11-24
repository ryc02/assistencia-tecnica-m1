package com.assistencia.model.usuario;

import com.assistencia.dados.Campos;
import com.assistencia.model.pessoa.Pessoa;
import com.assistencia.model.valor.Identificador;

/** Usuário interno do sistema (operador). Herda a identidade de Pessoa. */
public final class Usuario extends Pessoa {
    private final Conta conta;

    private Usuario(Identificador identificador, Conta conta) {
        super(identificador);
        this.conta = conta;
    }

    /** Usuário lido do banco de dados (senha já em hash). */
    public static Usuario de(Campos dados) {
        Identificador identificador = Identificador.de(dados);
        return new Usuario(identificador, Conta.armazenada(dados));
    }

    /** Usuário novo: a senha digitada é transformada em hash. */
    public static Usuario novo(Campos dados) {
        Identificador identificador = Identificador.de(dados);
        return new Usuario(identificador, Conta.nova(dados));
    }

    /** Confere a senha e devolve o usuário sem o hash, pronto para a sessão. */
    public Usuario autenticar(String senhaDigitada) {
        conta.exigirSenha(senhaDigitada);
        return de(exportar());
    }

    public boolean ehAdministrador() {
        return conta.ehAdministrador();
    }

    public Campos exportarComSenha() {
        Campos destino = exportar();
        conta.exportarComSenha(destino);
        return destino;
    }

    @Override
    protected void exportarDados(Campos destino) {
        conta.exportar(destino);
    }
}
