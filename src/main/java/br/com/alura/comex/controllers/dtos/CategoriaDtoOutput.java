package br.com.alura.comex.controllers.dtos;

import br.com.alura.comex.entities.enums.StatusCategoria;

public class CategoriaDtoOutput {
    private Long id;
    private String nome;
    private StatusCategoria status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    //    private List<Produto> produtos = new ArrayList<>();
}
