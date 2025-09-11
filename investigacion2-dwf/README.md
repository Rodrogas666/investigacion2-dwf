# dwf-miniproyecto

API REST de ejemplo con **Spring Boot 3.5.5**, **Java 17**, **Maven**, **H2**, y enfoque **Event-Driven** (eventos internos + `@Async`).Incluye Dockerfile y manifiestos de Kubernetes con probes de liveness/readiness.

## Requisitos cubiertos (según la guía de *Investigación Aplicada 2*)

- API REST JSON con CRUD completo para una entidad (`Order`). ✔️
- Persistencia en **H2 (in-memory)**. ✔️
- Enfoque **Event-Driven** usando `ApplicationEventPublisher` + `@EventListener` y tareas **asíncronas** con `@Async`. ✔️
- Empaquetado en **Docker** y despliegue en **Kubernetes** con `Deployment` (réplicas), `Service` y probes. ✔️
- Manejo de errores estandarizado. ✔️

> Consulta la rúbrica/indicaciones para que el demo y la defensa cumplan: vídeos, CRUD, errores, eventos, Docker y K8s. (UDB - *Investigación Aplicada 2*).

## Ejecutar local

```bash
# 1) Compilar y ejecutar
mvn spring-boot:run

# 2) Probar endpoints
curl -s http://localhost:8080/api/v1/orders | jq .
curl -s -X POST http://localhost:8080/api/v1/orders -H "Content-Type: application/json" \  -d '{"customerName":"Carlos","total":120.50}' | jq .

# 3) Consola H2
#  -> http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:appdb)
```

Swagger/OpenAPI UI: `http://localhost:8080/swagger-ui/index.html`

## Docker

```bash
# Construir imagen (desde la raíz del proyecto)
docker build -t dwf-miniproyecto:0.0.1 .

# Ejecutar
docker run --rm -p 8080:8080 -e APP_LOG_LEVEL=DEBUG dwf-miniproyecto:0.0.1
```

## Kubernetes

```bash
# Subir ConfigMap y Secret
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secret.yaml

# Desplegar (ajusta "image:" en deployment.yaml a tu registry)
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml

# Verificar
kubectl get pods,svc
```

Los probes consultan `/actuator/health/liveness` y `/actuator/health/readiness` (actuator habilitado).

## Diseño Event-Driven

- `OrderService` publica eventos (`OrderCreatedEvent`, `OrderUpdatedEvent`, `OrderDeletedEvent`) usando `ApplicationEventPublisher`.
- `OrderEventsListener` escucha estos eventos y ejecuta **tareas en background** con `@Async` (p.ej. envío de notificaciones).  Esto desacopla el CRUD de los side-effects y mejora escalabilidad/respuesta.

> Si deseas **RabbitMQ/Kafka**, puedes añadir productores/consumidores; ya hay dependencia opcional `spring-kafka` (scope `provided`) para evitar requerir un broker en la demo.

## Estructura principal

```
src/main/java/sv/edu/udb/dwfminiproyecto
├── DwfMiniproyectoApplication.java
├── domain/OrderEntity.java
├── repository/OrderRepository.java
├── service/OrderService.java
├── events/{OrderCreatedEvent,OrderUpdatedEvent,OrderDeletedEvent}.java
├── listeners/OrderEventsListener.java
└── web
    ├── dto/{CreateOrderRequest,UpdateOrderRequest,OrderResponse}.java
    ├── ApiExceptionHandler.java
    ├── ApiError.java
    ├── ResourceNotFoundException.java
    └── OrderController.java
```

## Notas

- Java 17 y Spring Boot 3.5.5 (configurado en `pom.xml`).
- H2 en memoria con datos de ejemplo (`data.sql`).
- Logs ajustables con `APP_LOG_LEVEL`.
- Actuator expone health; OpenAPI UI lista los endpoints.

---

© 2025 UDB – Demo académico.
