package br.com.alura.comex.controllers;

import br.com.alura.comex.controllers.dtos.ProdutoDtoInput;
import br.com.alura.comex.controllers.dtos.ProdutoDtoOutput;
import br.com.alura.comex.controllers.mappers.ProdutoMapper;
import br.com.alura.comex.entities.Categoria;
import br.com.alura.comex.entities.Produto;
import br.com.alura.comex.repositories.CategoriaRepository;
import br.com.alura.comex.repositories.ProdutoRepository;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoController(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public Page<ProdutoDtoOutput> listarTodos(Integer pagina) {
        if (pagina == null) {
            pagina = 0;
        }
        Pageable pageable = PageRequest.of(pagina, 5, Sort.by(Sort.Direction.ASC, "nome"));
        Page<Produto> paginaProdutos = produtoRepository.findAll(pageable);
        List<ProdutoDtoOutput> produtos = paginaProdutos.get().map(ProdutoMapper::toProdutoDtoOutput).collect(Collectors.toList());
        return new PageImpl<>(produtos, pageable, produtos.size());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoDtoOutput> salvar(@RequestBody @Valid ProdutoDtoInput produtoDtoInput, UriComponentsBuilder uriBuilder) {
        Optional<Categoria> optional = categoriaRepository.findById(produtoDtoInput.getIdCategoria());
        Produto produto = ProdutoMapper.fromProdutoDtoInput(produtoDtoInput);

        if (optional.isPresent()) {
            produto.setCategoria(optional.get());
            Produto produtoSalvo = produtoRepository.save(produto);
            URI uri = uriBuilder.path("/api/produtos/{id}").buildAndExpand(produtoSalvo.getId()).toUri();
            return ResponseEntity.created(uri).body(ProdutoMapper.toProdutoDtoOutput(produtoSalvo));
        }

        return ResponseEntity.notFound().build();
    }
}
