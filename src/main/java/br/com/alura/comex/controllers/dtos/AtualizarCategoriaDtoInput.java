package br.com.alura.comex.controllers.dtos;

import br.com.alura.comex.entities.enums.StatusCategoria;

import javax.validation.constraints.Size;

public class AtualizarCategoriaDtoInput {
    @Size(min = 2)
    private String nome;
    private StatusCategoria status;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public StatusCategoria getStatus() {
        return status;
    }

    public void setStatus(StatusCategoria status) {
        this.status = status;
    }
}
