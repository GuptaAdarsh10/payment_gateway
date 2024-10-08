package in.co.srdt.SRMUPayementGateway.repository;

import in.co.srdt.SRMUPayementGateway.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
