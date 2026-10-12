package br.com.baozistore.api.dto;

public record PedidoResponse(Long id, Long clienteId, Long produtoId, Integer quantidade) {}
