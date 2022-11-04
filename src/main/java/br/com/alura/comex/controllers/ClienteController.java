package br.com.alura.comex.controllers;

import br.com.alura.comex.controllers.dtos.ClienteDtoInput;
import br.com.alura.comex.controllers.dtos.ClienteDtoOutput;
import br.com.alura.comex.controllers.mappers.ClienteMapper;
import br.com.alura.comex.entities.Cliente;
import br.com.alura.comex.repositories.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public Page<ClienteDtoOutput> listarTodos(@PageableDefault(size = 5, direction = Sort.Direction.ASC, sort = "nome") Pageable pageable) {
        Page<Cliente> clientes = this.clienteRepository.findAll(pageable);
        return clientes.map(ClienteMapper::toClienteDtoOutput);
    }

    @PostMapping
    public ResponseEntity<ClienteDtoOutput> salvar(@RequestBody @Valid ClienteDtoInput clienteDtoInput) {
        Cliente cliente = ClienteMapper.fromClienteDtoInput(clienteDtoInput);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return ResponseEntity.ok(ClienteMapper.toClienteDtoOutput(clienteSalvo));
    }
}
