package br.com.alura.comex.controllers.mappers;

import br.com.alura.comex.controllers.dtos.ProdutoDtoInput;
import br.com.alura.comex.controllers.dtos.ProdutoDtoOutput;
import br.com.alura.comex.entities.Produto;

public class ProdutoMapper {
    public static Produto fromProdutoDtoInput(ProdutoDtoInput produtoDtoInput) {
        Produto produto = new Produto();
        produto.setNome(produtoDtoInput.getNome());
        produto.setDescricao(produtoDtoInput.getDescricao());
        produto.setPrecoUnitario(produtoDtoInput.getPreco());
        produto.setQuantidadeEstoque(produtoDtoInput.getQuantidadeEstoque());
        return produto;
    }

    public static ProdutoDtoOutput toProdutoDtoOutput(Produto produto) {
        ProdutoDtoOutput produtoDtoOutput = new ProdutoDtoOutput();
        produtoDtoOutput.setId(produto.getId());
        produtoDtoOutput.setNome(produto.getNome());
        produtoDtoOutput.setDescricao(produto.getDescricao());
        produtoDtoOutput.setPreco(produto.getPrecoUnitario());
        produtoDtoOutput.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        produtoDtoOutput.setIdCategoria(produto.getCategoria().getId());
        produtoDtoOutput.setNomeCategoria(produto.getCategoria().getNome());
        return produtoDtoOutput;
    }
}
