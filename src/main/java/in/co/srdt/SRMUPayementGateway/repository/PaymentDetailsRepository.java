package in.co.srdt.SRMUPayementGateway.repository;

import in.co.srdt.SRMUPayementGateway.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails,Long> {
}
