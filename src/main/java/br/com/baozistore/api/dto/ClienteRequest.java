package br.com.baozistore.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ClienteRequest(
        @NotBlank(message = "O nome é obrigatório.") String nome,
        @NotNull(message = "A data de cadastro é obrigatória.") LocalDate clienteDesde) {}
