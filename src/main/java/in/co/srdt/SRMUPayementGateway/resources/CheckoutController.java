package in.co.srdt.SRMUPayementGateway.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    @GetMapping()
    public String getPaymentPage() {
        return "Checkout";
    }
}
