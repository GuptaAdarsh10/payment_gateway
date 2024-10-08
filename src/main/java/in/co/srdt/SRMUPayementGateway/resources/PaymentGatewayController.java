package in.co.srdt.SRMUPayementGateway.resources;

import com.razorpay.RazorpayException;
import in.co.srdt.SRMUPayementGateway.entity.AcquirerData;
import in.co.srdt.SRMUPayementGateway.entity.Orders;
import in.co.srdt.SRMUPayementGateway.entity.PaymentDetails;
import in.co.srdt.SRMUPayementGateway.entity.Transaction;
import in.co.srdt.SRMUPayementGateway.repository.OrderRepository;
import in.co.srdt.SRMUPayementGateway.services.PaymentDetailService;
import in.co.srdt.SRMUPayementGateway.services.PaymentGatewayService;
import in.co.srdt.SRMUPayementGateway.services.TransactionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentGatewayController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentGatewayService razorpayService;
    @Autowired
    private PaymentDetailService paymentDetailService;
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/createOrder")
    public void createOrder(@RequestBody Orders orders) throws RazorpayException {
        String orderId = razorpayService.createOrder(orders.getAmount(),orders.getCurrency());
        orders.setOrderId(orderId);
        orderRepository.save(orders);
    }

    @PostMapping("/savePaymentDetails")
    //@PostMapping
    //@RequestMapping("/savePaymentDetails/{paymentId}")
    public void savePaymentResponse(@RequestParam String paymentId) throws Exception {
        try {
            JSONObject paymentJson = razorpayService.getPaymentById(paymentId);

            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setRazorpayPaymentId(paymentJson.optString("id", null));
            paymentDetails.setAmount(paymentJson.optLong("amount", 0L));
            paymentDetails.setCurrency(paymentJson.optString("currency", null));
            paymentDetails.setStatus(paymentJson.optString("status", null));
            paymentDetails.setOrderId(paymentJson.optString("order_id", null));
            paymentDetails.setInvoiceId(paymentJson.optString("invoice_id", null));
            paymentDetails.setInternational(paymentJson.optBoolean("international", false));
            paymentDetails.setMethod(paymentJson.optString("method", null));
            paymentDetails.setAmountRefunded(paymentJson.optLong("amount_refunded", 0L));
            paymentDetails.setRefundStatus(paymentJson.optString("refund_status", null));
            paymentDetails.setCaptured(paymentJson.optBoolean("captured", false));
            paymentDetails.setDescription(paymentJson.optString("description", null));
            paymentDetails.setCardId(paymentJson.optString("card_id", null));
            paymentDetails.setBank(paymentJson.optString("bank", null));
            paymentDetails.setWallet(paymentJson.optString("wallet", null));
            paymentDetails.setVpa(paymentJson.optString("vpa", null));
            paymentDetails.setEmail(paymentJson.optString("email", null));
            paymentDetails.setContact(paymentJson.optString("contact", null));
            paymentDetails.setFee(paymentJson.optLong("fee", 0L));
            paymentDetails.setTax(paymentJson.optLong("tax", 0L));
            paymentDetails.setErrorCode(paymentJson.optString("error_code", null));
            paymentDetails.setErrorDescription(paymentJson.optString("error_description", null));
            paymentDetails.setErrorSource(paymentJson.optString("error_source", null));
            paymentDetails.setErrorStep(paymentJson.optString("error_step", null));
            paymentDetails.setErrorReason(paymentJson.optString("error_reason", null));
            paymentDetails.setBankTransactionId(paymentJson.optString("bank_transaction_id", null));
            paymentDetails.setCreatedAt(paymentJson.optLong("created_at", 0L));


         /*   JSONObject acquirerData = paymentJson.optJSONObject("acquirer_data");
            if (acquirerData != null) {
                paymentDetails.setBankTransactionId(acquirerData.optString("bank_transaction_id", null));
            }
*/
            paymentDetailService.savePaymentDetails(paymentDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/handlePayment")
    public ResponseEntity<String> handlePayment(@RequestBody Map<String, Object> paymentDetails) {
        try {
            String paymentId = (String) paymentDetails.get("paymentId");
            //String orderId = (String) paymentDetails.get("orderId");
            //String signature = (String) paymentDetails.get("signature");
            Long amount = Long.parseLong((String) paymentDetails.get("amount"));

            transactionService.saveTransaction(paymentId, amount);
            //transactionService.saveTransaction(orderId, paymentId, signature, amount);
            return ResponseEntity.ok("Payment details saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving payment details");
        }
    }
}
