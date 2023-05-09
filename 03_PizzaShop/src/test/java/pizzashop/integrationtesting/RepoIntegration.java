package pizzashop.integrationtesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PaymentService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepoIntegration {

    public PaymentService paymentService;

    @Mock
    public Payment payment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        PaymentRepository paymentRepository = new PaymentRepository("data/integrationTestPayments.txt");
        paymentService = new PaymentService(null, paymentRepository);
    }

    @Test
    void getPayments() {
        Assertions.assertEquals(0, paymentService.getPayments().size());

        Mockito.when(payment.getType()).thenReturn(PaymentType.Cash);
        Mockito.when(payment.getTableNumber()).thenReturn(1);
        Mockito.when(payment.getAmount()).thenReturn(7.0);

        try {
            paymentService.addPayment(payment.getTableNumber(),  payment.getType(), payment.getAmount());
            Assertions.assertEquals(1, paymentService.getPayments().size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addPayment() {
        Mockito.when(payment.getType()).thenReturn(PaymentType.Cash);
        Mockito.when(payment.getTableNumber()).thenReturn(1);
        Mockito.when(payment.getAmount()).thenReturn(-7.0);

        try {
            paymentService.addPayment(payment.getTableNumber(), payment.getType(), payment.getAmount());
            assert false;
        } catch (Exception exception) {
            assertEquals("invalid value! ", exception.getMessage());
        }
    }

}
