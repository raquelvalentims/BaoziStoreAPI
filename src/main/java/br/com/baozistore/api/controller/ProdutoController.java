package br.com.baozistore.api.controller;

import br.com.baozistore.api.dto.ProdutoRequest;
import br.com.baozistore.api.model.Produto;
import br.com.baozistore.api.repository.ProdutoRepository;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody ProdutoRequest request) {
        Produto produto = new Produto(request.nome().trim(), request.preco(), request.estoque());
        Produto salvo = repository.save(produto);
        return ResponseEntity.created(URI.create("/api/produtos/" + salvo.getId())).body(salvo);
    }

    @GetMapping
    public List<Produto> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Produto consultar(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequest request) {
        Produto produto = consultar(id);
        produto.setNome(request.nome().trim());
        produto.setPreco(request.preco());
        produto.setEstoque(request.estoque());
        return repository.save(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Produto produto = consultar(id);
        repository.delete(produto);
        return ResponseEntity.noContent().build();
    }
}
