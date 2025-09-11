package sv.edu.udb.dwfminiproyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.udb.dwfminiproyecto.domain.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {}
