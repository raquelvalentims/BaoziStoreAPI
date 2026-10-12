package br.com.baozistore.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PedidoRequest(
        @NotNull(message = "O clienteId é obrigatório.") Long clienteId,
        @NotNull(message = "O produtoId é obrigatório.") Long produtoId,
        @NotNull(message = "A quantidade é obrigatória.")
        @Min(value = 1, message = "A quantidade deve ser igual ou maior que 1.") Integer quantidade) {}
