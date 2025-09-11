package sv.edu.udb.dwfminiproyecto.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.dwfminiproyecto.domain.OrderEntity;
import sv.edu.udb.dwfminiproyecto.service.OrderService;
import sv.edu.udb.dwfminiproyecto.web.dto.CreateOrderRequest;
import sv.edu.udb.dwfminiproyecto.web.dto.OrderResponse;
import sv.edu.udb.dwfminiproyecto.web.dto.UpdateOrderRequest;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderResponse> findAll() {
        return service.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public OrderResponse getById(@PathVariable Long id) {
        return toResponse(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {
        OrderEntity entity = new OrderEntity();
        entity.setCustomerName(request.customerName());
        entity.setTotal(request.total());
        var saved = service.create(entity);
        return ResponseEntity
                .created(URI.create("/api/v1/orders/" + saved.getId()))
                .body(toResponse(saved));
    }

    @PutMapping("/{id}")
    public OrderResponse update(@PathVariable Long id, @Valid @RequestBody UpdateOrderRequest request) {
        OrderEntity entity = new OrderEntity();
        entity.setCustomerName(request.customerName());
        entity.setTotal(request.total());
        entity.setStatus(request.status());
        return toResponse(service.update(id, entity));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private OrderResponse toResponse(OrderEntity e) {
        return new OrderResponse(
                e.getId(), e.getCustomerName(), e.getTotal(), e.getStatus(), e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
