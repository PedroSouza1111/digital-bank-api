package br.com.fiap.digital_bank_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.digital_bank_api.model.Count;

@RestController
@RequestMapping("/")
public class CountController {
    
    private Logger log = LoggerFactory.getLogger(getClass());
    private List<Count> repository = new ArrayList<>();

    @GetMapping
    public String getProjetoInfo() {
        return """
               Projeto: Digital Bank API
               Integrantes: Pedro H. de Souza, Vinicius de S. Sant Anna""";
    }

    @GetMapping("/count")
    public List<Count> index() {
        return repository;
    }

    @GetMapping("/count/{id}")
    public Count getById(@PathVariable Long id) {
        log.info("Buscando conta " + id);
        return getCountById(id);
    }

    @GetMapping("/count/{cpf}")
    public Count getByCpf(@PathVariable Long cpf) {
        log.info("Buscando conta " + cpf);
        return getCountByCpf(cpf);
    }

    @PostMapping("/count")
    public ResponseEntity<Count> create(@RequestBody Count count) {
        repository.add(count);
        log.info("Cadastrando conta " + count.getNome());
        return ResponseEntity.status(201).body(count);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id){
        log.info("Apagando conta " + id);
        getCountById(id).setAtiva(false);
    }

    //DEPÓSITO
    @PutMapping("{id}")
    public Count update(@PathVariable Long id, Double valor,@RequestBody Count count){
        validarValor(valor);
        log.info("Depositando " + valor + " na conta " + count);

        count.setSaldo(getCountById(id).getSaldo() + valor);

        return count;
    }


    private Count getCountById(Long id) {
        return repository.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta " + id + " não encontrada")
                );
    }

    private Count getCountByCpf(Long cpf) {
        return repository.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta do CPF " + cpf + " não encontrada")
                );
    }

    private void validarValor(Double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Insira um valor válido!");
        }
    }
}
