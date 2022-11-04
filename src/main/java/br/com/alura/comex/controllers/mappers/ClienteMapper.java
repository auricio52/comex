package br.com.alura.comex.controllers.mappers;

import br.com.alura.comex.controllers.dtos.ClienteDtoInput;
import br.com.alura.comex.controllers.dtos.ClienteDtoOutput;
import br.com.alura.comex.entities.Cliente;

public class ClienteMapper {
    public static ClienteDtoOutput toClienteDtoOutput(Cliente cliente) {
        String local = cliente.getCidade() + "/" + cliente.getEstado();
        ClienteDtoOutput dto = new ClienteDtoOutput();
        dto.setId(cliente.getId());
        dto.setCpf(cliente.getCpf());
        dto.setNome(cliente.getNome());
        dto.setTelefone(cliente.getTelefone());
        dto.setLocal(local);
        return dto;
    }

    public static Cliente fromClienteDtoInput(ClienteDtoInput clienteDtoInput) {
        Cliente cliente = new Cliente();
        cliente.setBairro(clienteDtoInput.getBairro());
        cliente.setCidade(clienteDtoInput.getCidade());
        cliente.setCpf(clienteDtoInput.getCpf());
        cliente.setComplemento(clienteDtoInput.getComplemento());
        cliente.setNome(clienteDtoInput.getNome());
        cliente.setNumero(clienteDtoInput.getNumero());
        cliente.setEstado(clienteDtoInput.getEstado());
        cliente.setRua(clienteDtoInput.getRua());
        cliente.setTelefone(clienteDtoInput.getTelefone());
        return cliente;
    }
}
