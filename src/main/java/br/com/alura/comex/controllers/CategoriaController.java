package br.com.alura.comex.controllers;

import br.com.alura.comex.controllers.dtos.AtualizarCategoriaDtoInput;
import br.com.alura.comex.controllers.dtos.CategoriaDtoInput;
import br.com.alura.comex.controllers.dtos.CategoriaDtoOutput;
import br.com.alura.comex.controllers.mappers.CategoriaMapper;
import br.com.alura.comex.entities.Categoria;
import br.com.alura.comex.repositories.CategoriaRepository;
import br.com.alura.comex.repositories.projections.CategoriaProjection;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public List<CategoriaDtoOutput> listarTodas(String nome) {
        List<Categoria> categorias;
        if (StringUtils.hasText(nome)) {
            categorias = categoriaRepository.findByNome(nome);
        } else {
            categorias = categoriaRepository.findAll();
        }
        return categorias.stream().map(CategoriaMapper::toCategoriaDtoOutput).collect(Collectors.toList());
    }

    @PostMapping
    @CacheEvict(value = "relatoriosPedidos", allEntries = true)
    @Transactional
    public ResponseEntity<CategoriaDtoOutput> salvar(@RequestBody @Valid CategoriaDtoInput categoriaDtoInput, UriComponentsBuilder uriBuilder) {
        Categoria categoria = CategoriaMapper.fromCategoriaDtoInput(categoriaDtoInput);
        Categoria categoriaCriada = categoriaRepository.save(categoria);
        URI uri = uriBuilder.path("/api/categorias/{id}").buildAndExpand(categoriaCriada.getId()).toUri();
        return ResponseEntity.created(uri).body(CategoriaMapper.toCategoriaDtoOutput(categoriaCriada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDtoOutput> buscarPorId(@PathVariable Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        CategoriaDtoOutput dto = CategoriaMapper.toCategoriaDtoOutput(categoria);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/pedidos")
    @Cacheable(value = "relatoriosPedidos")
    public List<CategoriaProjection> buscarRelatorio() {
        List<CategoriaProjection> categorias = categoriaRepository.findCategoriaRelatorio();
        return categorias;
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "relatoriosPedidos", allEntries = true)
    @Transactional
    public ResponseEntity<CategoriaDtoOutput> atualizar(@RequestBody AtualizarCategoriaDtoInput dtoInput, @PathVariable Long id) {
        Categoria categoriaAtual = categoriaRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        Categoria categoriaNova = CategoriaMapper.fromAtualizarCategoriaDtoInput(dtoInput);
        BeanUtils.copyProperties(categoriaNova, categoriaAtual, "id", "produtos");
        return ResponseEntity.ok(CategoriaMapper.toCategoriaDtoOutput(categoriaAtual));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "relatoriosPedidos", allEntries = true)
    @Transactional
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
