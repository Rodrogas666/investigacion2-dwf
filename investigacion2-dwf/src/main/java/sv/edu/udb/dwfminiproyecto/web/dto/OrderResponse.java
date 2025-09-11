package sv.edu.udb.dwfminiproyecto.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String customerName,
        BigDecimal total,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
