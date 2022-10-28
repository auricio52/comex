package br.com.alura.comex.controllers.mappers;

import br.com.alura.comex.controllers.dtos.AtualizarCategoriaDtoInput;
import br.com.alura.comex.controllers.dtos.CategoriaDtoInput;
import br.com.alura.comex.controllers.dtos.CategoriaDtoOutput;
import br.com.alura.comex.entities.Categoria;

public class CategoriaMapper {
    public static Categoria fromCategoriaDtoInput(CategoriaDtoInput categoriaDtoInput) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDtoInput.getNome());
        return categoria;
    }

    public static Categoria fromAtualizarCategoriaDtoInput(AtualizarCategoriaDtoInput atualizarCategoriaDtoInput) {
        Categoria categoria = new Categoria();
        categoria.setNome(atualizarCategoriaDtoInput.getNome());
        categoria.setStatus(atualizarCategoriaDtoInput.getStatus());
        return categoria;
    }

    public static CategoriaDtoOutput toCategoriaDtoOutput(Categoria categoria) {
        CategoriaDtoOutput categoriaDtoOutput = new CategoriaDtoOutput();
        categoriaDtoOutput.setId(categoria.getId());
        categoriaDtoOutput.setNome(categoria.getNome());
        categoriaDtoOutput.setStatus(categoria.getStatus());
        return categoriaDtoOutput;
    }
}
