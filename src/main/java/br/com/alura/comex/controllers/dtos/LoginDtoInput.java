package br.com.alura.comex.controllers.dtos;

import javax.validation.constraints.NotBlank;

public class LoginDtoInput {
    @NotBlank
    private String email;
    @NotBlank
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
