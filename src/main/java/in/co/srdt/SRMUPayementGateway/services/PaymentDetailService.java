package in.co.srdt.SRMUPayementGateway.services;

import in.co.srdt.SRMUPayementGateway.entity.AcquirerData;
import in.co.srdt.SRMUPayementGateway.entity.PaymentDetails;
import in.co.srdt.SRMUPayementGateway.repository.PaymentDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class PaymentDetailService {

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    public PaymentDetails savePaymentDetails(PaymentDetails response) {

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setRazorpayPaymentId(response.getRazorpayPaymentId());
        paymentDetails.setAmount(response.getAmount());
        paymentDetails.setCurrency(response.getCurrency());
        paymentDetails.setStatus(response.getStatus());
        paymentDetails.setOrderId(response.getOrderId());
        paymentDetails.setInvoiceId(response.getInvoiceId());
        paymentDetails.setInternational(response.isInternational());
        paymentDetails.setMethod(response.getMethod());
        paymentDetails.setAmountRefunded(response.getAmountRefunded());
        paymentDetails.setRefundStatus(response.getRefundStatus());
        paymentDetails.setCaptured(response.isCaptured());
        paymentDetails.setDescription(response.getDescription());
        paymentDetails.setCardId(response.getCardId());
        paymentDetails.setBank(response.getBank());
        paymentDetails.setWallet(response.getWallet());
        paymentDetails.setVpa(response.getVpa());
        paymentDetails.setEmail(response.getEmail());
        paymentDetails.setContact(response.getContact());
        paymentDetails.setFee(response.getFee());
        paymentDetails.setTax(response.getTax());
        paymentDetails.setErrorCode(response.getErrorCode());
        paymentDetails.setErrorDescription(response.getErrorDescription());
        paymentDetails.setErrorSource(response.getErrorSource());
        paymentDetails.setErrorStep(response.getErrorStep());
        paymentDetails.setErrorReason(response.getErrorReason());

        if (response.getAcquirerData() != null) {
            AcquirerData acquirerData = new AcquirerData();
            acquirerData.setBankTransactionId(response.getAcquirerData().getBankTransactionId());
            paymentDetails.setAcquirerData(acquirerData);
        }
        //long createdAtTimestamp = response.getCreatedAt(); // should return a long
        //LocalDateTime createdAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(createdAtTimestamp), ZoneOffset.UTC);
        paymentDetails.setCreatedAt(response.getCreatedAt()); // Set LocalDateTime in the entity

        return paymentDetailsRepository.save(paymentDetails);
    }

}
