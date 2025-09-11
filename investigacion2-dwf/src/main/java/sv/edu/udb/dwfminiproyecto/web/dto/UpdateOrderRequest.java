package sv.edu.udb.dwfminiproyecto.web.dto;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record UpdateOrderRequest(
        String customerName,
        @Positive BigDecimal total,
        String status
) {}
