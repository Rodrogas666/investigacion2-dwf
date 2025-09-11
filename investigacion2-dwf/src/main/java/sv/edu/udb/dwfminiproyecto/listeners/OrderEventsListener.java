package sv.edu.udb.dwfminiproyecto.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sv.edu.udb.dwfminiproyecto.events.OrderCreatedEvent;
import sv.edu.udb.dwfminiproyecto.events.OrderDeletedEvent;
import sv.edu.udb.dwfminiproyecto.events.OrderUpdatedEvent;

@Component
public class OrderEventsListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventsListener.class);

    @Async
    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("[async] Recibido OrderCreatedEvent para id={}", event.orderId());
        // Simular una tarea en background (por ejemplo, envío de correo o integración con otro sistema)
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        log.info("[async] Procesamiento post-creación completado para id={}", event.orderId());
    }

    @Async
    @EventListener
    public void onOrderUpdated(OrderUpdatedEvent event) {
        log.info("[async] Recibido OrderUpdatedEvent para id={}", event.orderId());
    }

    @Async
    @EventListener
    public void onOrderDeleted(OrderDeletedEvent event) {
        log.info("[async] Recibido OrderDeletedEvent para id={}", event.orderId());
    }
}
