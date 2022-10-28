package br.com.alura.comex.controllers.dtos;

import javax.validation.constraints.Size;

public class CategoriaDtoInput {
    @Size(min = 2)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
