package br.com.alura.comex.controllers;

import br.com.alura.comex.controllers.dtos.ProdutoDtoInput;
import br.com.alura.comex.controllers.dtos.ProdutoDtoOutput;
import br.com.alura.comex.controllers.mappers.ProdutoMapper;
import br.com.alura.comex.entities.Categoria;
import br.com.alura.comex.entities.Produto;
import br.com.alura.comex.repositories.CategoriaRepository;
import br.com.alura.comex.repositories.ProdutoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
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
    public Page<ProdutoDtoOutput> listarTodos(@PageableDefault(sort = "nome", direction = Sort.Direction.ASC, size = 5) Pageable pageable) {
        Page<Produto> paginaProdutos = produtoRepository.findAll(pageable);
        List<ProdutoDtoOutput> produtos = paginaProdutos.get().map(ProdutoMapper::toProdutoDtoOutput).collect(Collectors.toList());
        return new PageImpl<>(produtos, pageable, produtos.size());
    }

    @PostMapping
    @CacheEvict(value = "relatoriosPedidos", allEntries = true)
    @Transactional
    public ResponseEntity<ProdutoDtoOutput> salvar(@RequestBody @Valid ProdutoDtoInput produtoDtoInput, UriComponentsBuilder uriBuilder) {
        Categoria categoria = categoriaRepository.findById(produtoDtoInput.getIdCategoria()).orElseThrow(() -> new EmptyResultDataAccessException(1));
        Produto produto = ProdutoMapper.fromProdutoDtoInput(produtoDtoInput);
        produto.setCategoria(categoria);
        Produto produtoSalvo = produtoRepository.save(produto);
        URI uri = uriBuilder.path("/api/produtos/{id}").buildAndExpand(produtoSalvo.getId()).toUri();
        return ResponseEntity.created(uri).body(ProdutoMapper.toProdutoDtoOutput(produtoSalvo));
    }
}
