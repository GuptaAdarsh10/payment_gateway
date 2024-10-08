package in.co.srdt.SRMUPayementGateway.repository;

import in.co.srdt.SRMUPayementGateway.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
}
