package sv.edu.udb.dwfminiproyecto.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateOrderRequest(
        @NotBlank String customerName,
        @NotNull @Positive BigDecimal total
) {}
