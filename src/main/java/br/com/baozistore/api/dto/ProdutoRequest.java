package br.com.baozistore.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProdutoRequest(
        @NotBlank(message = "O nome é obrigatório.") String nome,
        @NotNull(message = "O preço é obrigatório.")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.") BigDecimal preco,
        @NotNull(message = "Informe se o produto está em estoque.") Boolean estoque) {}
