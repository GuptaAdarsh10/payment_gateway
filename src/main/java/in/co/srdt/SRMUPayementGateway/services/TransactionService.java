package in.co.srdt.SRMUPayementGateway.services;

import in.co.srdt.SRMUPayementGateway.entity.Orders;
import in.co.srdt.SRMUPayementGateway.entity.Transaction;
import in.co.srdt.SRMUPayementGateway.repository.OrderRepository;
import in.co.srdt.SRMUPayementGateway.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OrderRepository ordersRepository;

    public void saveTransaction(String orderId, String paymentId, String signature, Long amount) {
        Orders order = ordersRepository.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Transaction transaction = new Transaction();
        transaction.setOrders(order);
        transaction.setPaymentId(paymentId);
        transaction.setStatus("success");
        transaction.setAmount(amount);

        transactionRepository.save(transaction);
    }

    public void saveTransaction(String paymentId,Long amount){

        Transaction transaction = new Transaction();
        transaction.setPaymentId(paymentId);
        transaction.setStatus("success");
        transaction.setAmount(amount);
        transactionRepository.save(transaction);
    }
}
