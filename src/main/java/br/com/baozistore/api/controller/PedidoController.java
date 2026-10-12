package br.com.baozistore.api.controller;

import br.com.baozistore.api.dto.PedidoRequest;
import br.com.baozistore.api.dto.PedidoResponse;
import br.com.baozistore.api.model.Cliente;
import br.com.baozistore.api.model.Pedido;
import br.com.baozistore.api.model.Produto;
import br.com.baozistore.api.repository.ClienteRepository;
import br.com.baozistore.api.repository.PedidoRepository;
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
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository pedidoRepository,
                            ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@Valid @RequestBody PedidoRequest request) {
        Cliente cliente = obterCliente(request.clienteId());
        Produto produto = obterProduto(request.produtoId());
        Pedido salvo = pedidoRepository.save(new Pedido(cliente, produto, request.quantidade()));
        return ResponseEntity.created(URI.create("/api/pedidos/" + salvo.getId())).body(resumir(salvo));
    }

    @GetMapping
    public List<PedidoResponse> listar() {
        return pedidoRepository.findAll().stream().map(this::resumir).toList();
    }

    @GetMapping("/{id}")
    public PedidoResponse consultar(@PathVariable Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
        return resumir(pedido);
    }

    @PutMapping("/{id}")
    public PedidoResponse atualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequest request) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
        pedido.setCliente(obterCliente(request.clienteId()));
        pedido.setProduto(obterProduto(request.produtoId()));
        pedido.setQuantidade(request.quantidade());
        return resumir(pedidoRepository.save(pedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
        pedidoRepository.delete(pedido);
        return ResponseEntity.noContent().build();
    }

    private Cliente obterCliente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente informado não encontrado."));
    }

    private Produto obterProduto(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto informado não encontrado."));
    }

    private PedidoResponse resumir(Pedido pedido) {
        return new PedidoResponse(pedido.getId(), pedido.getCliente().getId(),
                pedido.getProduto().getId(), pedido.getQuantidade());
    }
}
