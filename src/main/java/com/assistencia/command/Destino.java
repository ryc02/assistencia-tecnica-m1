package com.assistencia.command;

/** Destinos dos Commands: views JSP e redirecionamentos (padrão Post/Redirect/Get). */
public final class Destino {
    private Destino() { }

    public static String view(String nome) {
        return "/WEB-INF/views/" + nome + ".jsp";
    }

    public static String redirecionar(String acao) {
        return "redirect:/controle?acao=" + acao;
    }

    public static String redirecionar(String acao, Long id) {
        return redirecionar(acao) + "&id=" + id;
    }
}
