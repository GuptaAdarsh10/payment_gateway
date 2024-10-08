package in.co.srdt.SRMUPayementGateway.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AcquirerData {

    @Column(name = "bank_transactions_id")
    private String bankTransactionId;

    public String getBankTransactionId() {
        return bankTransactionId;
    }

    public void setBankTransactionId(String bankTransactionId) {
        this.bankTransactionId = bankTransactionId;
    }

    public AcquirerData() {
    }
}
