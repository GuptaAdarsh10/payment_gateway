package in.co.srdt.SRMUPayementGateway.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class RazorpayConfigController {

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @GetMapping("/razorpay-key")
    public ResponseEntity<String> getRazorpayKey() {
        return ResponseEntity.ok(razorpayKeyId);
    }

    @PostMapping("/razorpay")
    public ResponseEntity<String> handleRazorpayWebhook(@RequestBody Map<String, String> payload) {
        System.out.println("Webhook received: " + payload);

        String paymentId = payload.get("razorpay_payment_id");
        String orderId = payload.get("razorpay_order_id");
        String signature = payload.get("razorpay_signature");

        return ResponseEntity.ok("Webhook received successfully");
    }
}
