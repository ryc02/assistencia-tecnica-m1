package com.assistencia.builder;

import com.assistencia.exception.ValidationException;
import com.assistencia.model.Cliente;
import com.assistencia.model.Equipamento;

import java.time.LocalDateTime;

/**
 * [Requisito 8: Padrão Builder]
 * EquipamentoBuilder: Constrói objetos Equipamento validando voltagem e cliente.
 */
public class EquipamentoBuilder {

    private Long id;
    private Cliente cliente;
    private String tipo;
    private String marca;
    private String modelo;
    private String numeroSerie;
    private String cor;
    private String voltagem;
    private String descricao;
    private LocalDateTime dataCadastro;

    public EquipamentoBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public EquipamentoBuilder cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public EquipamentoBuilder tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public EquipamentoBuilder marca(String marca) {
        this.marca = marca;
        return this;
    }

    public EquipamentoBuilder modelo(String modelo) {
        this.modelo = modelo;
        return this;
    }

    public EquipamentoBuilder numeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
        return this;
    }

    public EquipamentoBuilder cor(String cor) {
        this.cor = cor;
        return this;
    }

    public EquipamentoBuilder voltagem(String voltagem) {
        this.voltagem = voltagem;
        return this;
    }

    public EquipamentoBuilder descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public EquipamentoBuilder dataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    public Equipamento build() {
        if (cliente == null) {
            throw new ValidationException("Cliente é obrigatório para cadastrar um equipamento.");
        }
        if (tipo == null || tipo.trim().isEmpty() ||
            marca == null || marca.trim().isEmpty() ||
            modelo == null || modelo.trim().isEmpty()) {
            throw new ValidationException("Tipo, marca e modelo são obrigatórios.");
        }
        if (voltagem == null || voltagem.trim().isEmpty()) {
            throw new ValidationException("Voltagem é obrigatória.");
        }
        String v = voltagem.trim().toUpperCase();
        if (!v.equals("110V") && !v.equals("127V") && !v.equals("220V") && !v.equals("BIVOLT") && !v.equals("NAO_APLICAVEL")) {
            throw new ValidationException("Voltagem inválida. Valores permitidos: 110V, 127V, 220V, BIVOLT ou NAO_APLICAVEL.");
        }

        Equipamento eq = new Equipamento();
        eq.setId(id);
        eq.setCliente(cliente);
        eq.setTipo(tipo.trim());
        eq.setMarca(marca.trim());
        eq.setModelo(modelo.trim());
        eq.setNumeroSerie(numeroSerie != null ? numeroSerie.trim() : null);
        eq.setCor(cor != null ? cor.trim() : null);
        eq.setVoltagem(v);
        eq.setDescricao(descricao != null ? descricao.trim() : null);
        eq.setDataCadastro(dataCadastro != null ? dataCadastro : LocalDateTime.now());

        return eq;
    }
}
