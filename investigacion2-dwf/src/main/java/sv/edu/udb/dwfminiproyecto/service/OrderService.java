package sv.edu.udb.dwfminiproyecto.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.edu.udb.dwfminiproyecto.domain.OrderEntity;
import sv.edu.udb.dwfminiproyecto.events.OrderCreatedEvent;
import sv.edu.udb.dwfminiproyecto.events.OrderDeletedEvent;
import sv.edu.udb.dwfminiproyecto.events.OrderUpdatedEvent;
import sv.edu.udb.dwfminiproyecto.repository.OrderRepository;
import sv.edu.udb.dwfminiproyecto.web.ResourceNotFoundException;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository repository;
    private final ApplicationEventPublisher events;

    public OrderService(OrderRepository repository, ApplicationEventPublisher events) {
        this.repository = repository;
        this.events = events;
    }

    @Transactional(readOnly = true)
    public List<OrderEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public OrderEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order %d no encontrada".formatted(id)));
    }

    public OrderEntity create(OrderEntity entity) {
        OrderEntity saved = repository.save(entity);
        events.publishEvent(new OrderCreatedEvent(saved.getId()));
        return saved;
    }

    public OrderEntity update(Long id, OrderEntity changes) {
        OrderEntity current = findById(id);
        if (changes.getCustomerName() != null) current.setCustomerName(changes.getCustomerName());
        if (changes.getTotal() != null) current.setTotal(changes.getTotal());
        if (changes.getStatus() != null) current.setStatus(changes.getStatus());
        OrderEntity saved = repository.save(current);
        events.publishEvent(new OrderUpdatedEvent(saved.getId()));
        return saved;
    }

    public void delete(Long id) {
        OrderEntity current = findById(id);
        repository.delete(current);
        events.publishEvent(new OrderDeletedEvent(id));
    }
}
