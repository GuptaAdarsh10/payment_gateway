package in.co.srdt.SRMUPayementGateway.services;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import in.co.srdt.SRMUPayementGateway.entity.Orders;
import in.co.srdt.SRMUPayementGateway.entity.Transaction;
import in.co.srdt.SRMUPayementGateway.repository.OrderRepository;
import in.co.srdt.SRMUPayementGateway.repository.TransactionRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Base64;



@Service
public class PaymentGatewayService {

    private RazorpayClient razorpayClient;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Autowired
    private TransactionRepository transactionRepository;

    private final String API_URL = "https://api.razorpay.com/v1/payments/";

    @Autowired
    private OrderRepository ordersRepository;

    public PaymentGatewayService(@Value("${razorpay.key.id}") String keyId,
                                 @Value("${razorpay.key.secret}") String keySecret) throws RazorpayException {
        this.razorpayClient = new RazorpayClient(keyId, keySecret);
    }

    public void saveTransaction(String orderId, String paymentId, String status, Long amount) {
        Orders order = ordersRepository.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Transaction transaction = new Transaction();
        transaction.setOrders(order);
        transaction.setPaymentId(paymentId);
        transaction.setStatus(status);
        transaction.setAmount(amount);

        transactionRepository.save(transaction);
    }

    public String createOrder(String amount, String currency) throws RazorpayException {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", Integer.parseInt(amount) * 100);
        orderRequest.put("currency", currency);
        Order order = razorpayClient.orders.create(orderRequest);
        return order.get("id");
    }

    public JSONObject getPaymentById(String paymentId) throws Exception {
        String urlString = API_URL + paymentId;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        String auth = keyId + ":" + keyId;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return new JSONObject(response.toString());
        } finally {
            connection.disconnect();
        }
    }

    public boolean verifyPayment(String orderId,String paymentId) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(keyId,keySecret);

        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", "order_IEIaMR65cu6nz3");
        options.put("razorpay_payment_id", "pay_IH4NVgf4Dreq1l");
        options.put("razorpay_signature", "0d4e745a1838664ad6c9c9902212a32d627d68e917290b0ad5f08ff4561bc50f");

        boolean status =  Utils.verifyPaymentSignature(options, keySecret);
        return status;
    }

}
