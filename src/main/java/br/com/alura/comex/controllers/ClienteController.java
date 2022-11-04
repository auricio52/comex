package br.com.alura.comex.controllers;

import br.com.alura.comex.controllers.dtos.ClienteDtoInput;
import br.com.alura.comex.controllers.dtos.ClienteDtoOutput;
import br.com.alura.comex.controllers.mappers.ClienteMapper;
import br.com.alura.comex.entities.Cliente;
import br.com.alura.comex.repositories.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public List<ClienteDtoOutput> listarTodos() {
        List<Cliente> clientes = this.clienteRepository.findAll();
        return clientes.stream().map(ClienteMapper::toClienteDtoOutput).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ClienteDtoOutput> salvar(@RequestBody @Valid ClienteDtoInput clienteDtoInput) {
        Cliente cliente = ClienteMapper.fromClienteDtoInput(clienteDtoInput);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return ResponseEntity.ok(ClienteMapper.toClienteDtoOutput(clienteSalvo));
    }
}
