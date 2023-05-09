package pizzashop.integrationtesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PaymentService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityIntegration {

    public PaymentService paymentService;


    @BeforeEach
    void setUp() {
        PaymentRepository paymentRepository = new PaymentRepository("data/integrationTestPayments.txt");
        paymentService = new PaymentService(null, paymentRepository);
    }

    @Test
    void getPayments() {
        Assertions.assertEquals(0, paymentService.getPayments().size());
        try {
            paymentService.addPayment(1, PaymentType.Cash, 7.0);
            Assertions.assertEquals(1, paymentService.getPayments().size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addPayment() {
        try {
            paymentService.addPayment(1, PaymentType.Cash, -7.0);
            assert false;
        } catch (Exception exception) {
            assertEquals("invalid value! ", exception.getMessage());
        }
    }
}
